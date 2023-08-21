package pize.util.io;

import pize.Pize;
import pize.app.Disposable;
import pize.io.keyboard.CharCallback;
import pize.io.keyboard.KeyCallback;
import pize.io.key.Key;
import pize.io.key.KeyState;
import pize.util.StringUtils;
import pize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TextProcessor implements Disposable, CharCallback, KeyCallback{
    
    private boolean active, newLineOnEnter;
    private final List<String> lines;
    private int currentLineLength;
    
    private int cursorX, cursorY;
    private boolean cursorInEnd;
    private final Stopwatch cursorStopwatch;
    private int tabSpaces = 4;
    
    public TextProcessor(boolean newLineOnEnter){
        this.active = true;
        this.newLineOnEnter = newLineOnEnter;
        this.lines = new ArrayList<>();
        this.lines.add("");
        this.cursorStopwatch = new Stopwatch().start();

        Pize.keyboard().addCharCallback(this);
        Pize.keyboard().addKeyCallback(this);
    }
    
    public TextProcessor(){
        this(true);
    }
    
    
    @Override
    public void invoke(char character){
        if(!active)
            return;
        
        insertChar(character);
        resetCursorBlinking();
    }
    
    @Override
    public void invoke(int keyCode, KeyState action){
        if(!active || action == KeyState.RELEASE)
            return;

        moveCursor(keyCode);
        if(Key.LEFT_SHIFT.isPressed() && keyCode == Key.TAB.GLFW){
            removeTab();
            resetCursorBlinking();
        }else if(keyCode == Key.TAB.GLFW){
            insertTab();
            resetCursorBlinking();
        }

        if(newLineOnEnter && keyCode == Key.ENTER.GLFW){
            newLineAndWrap();
            resetCursorBlinking();
        }
        
        else if(keyCode == Key.BACKSPACE.GLFW && !(cursorX == 0 && cursorY == 0)){
            if(cursorX == 0)
                removeLineAndWrap();
            else
                removeChar();

            resetCursorBlinking();
        }
    }
    
    
    private void removeChar(){
        String line = lines.get(cursorY);
        setLine(line.substring(0, cursorX - 1) + line.substring(cursorX));
        
        cursorX--;
        currentLineLength--;
    }
    
    public void insertChar(char character){
        String currentLine = lines.get(cursorY);
        setLine(currentLine.substring(0, cursorX) + character + currentLine.substring(cursorX));
        
        cursorX++;
        currentLineLength++;
    }

    public void setLine(String line){
        lines.set(cursorY, line);
    }

    public String getLine(){
        return lines.get(cursorY);
    }
    
    public void insertLine(String line){
        line = line.replaceAll("\n", "");
        
        String currentLine = getLine();
        currentLine = currentLine.substring(0, cursorX) + line + currentLine.substring(cursorX);
        setLine(currentLine);
        
        cursorX += line.length();
        currentLineLength += line.length();
    }
    
    public void insertText(String text){
        int linesNum = StringUtils.count(text, "\n") + 1;
        String[] lines = text.split("\n");
        
        for(int i = 0; i < linesNum; i++){
            if(i != 0)
                newLineAndWrap();
            
            if(i < lines.length)
                insertLine(lines[i]);
        }
    }

    public void insertTab(){
        insertLine(" ".repeat(tabSpaces));
    }

    public void removeTab(){
        String line = getLine();
        if(line.isEmpty())
            return;

        for(int i = 0; i < tabSpaces; i++){
            if(line.charAt(0) == ' ')
                line = line.substring(1);
            else
                return;
        }
        setLine(line);
        cursorX = Math.max(0, cursorX - tabSpaces);
        updateCurrentLineLength();
    }
    
    
    public void newLineAndWrap(){
        final String prevLine = getLine();
        setLine(prevLine.substring(0, cursorX));
        
        lines.add(cursorY + 1, prevLine.substring(cursorX));
        cursorY++;
        updateCurrentLineLength();
        moveCursorHome();
    }
    
    public void removeLineAndWrap(){
        final String removedLine = lines.get(cursorY);
        lines.remove(cursorY);
        cursorY--;
        
        updateCurrentLineLength();
        moveCursorEnd();
        setLine(lines.get(cursorY) + removedLine);
        updateCurrentLineLength();
    }
    
    public void removeLine(){
        if(lines.size() == 1)
            lines.set(0, "");
        else{
            lines.remove(cursorY);
            
            if(cursorY >= lines.size())
                cursorY--;
        }
        
        updateCurrentLineLength();
        moveCursorEnd();
    }
    
    
    private void updateCurrentLineLength(){
        currentLineLength = lines.get(cursorY).length();
    }
    
    
    private void moveCursor(int keyCode){
        if(keyCode == Key.END.GLFW  ) moveCursorEnd();
        if(keyCode == Key.HOME.GLFW ) moveCursorHome();
        if(keyCode == Key.UP.GLFW   ) moveCursorUp();
        if(keyCode == Key.DOWN.GLFW ) moveCursorDown();
        if(keyCode == Key.LEFT.GLFW ) moveCursorLeft();
        if(keyCode == Key.RIGHT.GLFW) moveCursorRight();
        
        cursorInEnd = (
            cursorX == currentLineLength &&
            cursorX > 0 &&
            !lines.get(cursorY).substring(0, cursorX).isBlank()
        );
    }
    
    public void moveCursorEnd(){
        cursorX = currentLineLength;
        resetCursorBlinking();
    }
    
    public void moveCursorHome(){
        cursorX = 0;
        resetCursorBlinking();
    }
    
    public void moveCursorDown(){
        if(cursorY < lines.size() - 1){
            cursorY++;
            updateCurrentLineLength();
            norCursorX();
        }
        resetCursorBlinking();
    }
    
    public void moveCursorUp(){
        if(cursorY > 0){
            cursorY--;
            updateCurrentLineLength();
            norCursorX();
        }
        resetCursorBlinking();
    }
    
    public void moveCursorLeft(){
        if(cursorX > 0)
            cursorX--;
        else if(cursorY > 0){
            cursorY--;
            updateCurrentLineLength();
            moveCursorEnd();
        }
        resetCursorBlinking();
    }
    
    public void moveCursorRight(){
        if(cursorX < currentLineLength)
            cursorX++;
        else if(cursorY < lines.size() - 1){
            cursorY++;
            updateCurrentLineLength();
            moveCursorHome();
        }
        resetCursorBlinking();
    }

    public void resetCursorBlinking(){
        cursorStopwatch.reset();
    }
    
    private void norCursorX(){
        if(cursorX > currentLineLength || cursorInEnd)
            cursorX = currentLineLength;
    }
    
    
    public List<String> getLines(){
        return lines;
    }
    
    public String getCurrentLine(){
        if(cursorY >= lines.size())
            return null;
        
        return lines.get(cursorY);
    }
    
    
    public int getCursorX(){
        return cursorX;
    }
    
    public int getCursorY(){
        return cursorY;
    }
    
    
    public int getTabSpaces(){
        return tabSpaces;
    }
    
    public void setTabSpaces(int tabSpaces){
        this.tabSpaces = tabSpaces;
    }
    
    
    public boolean isNewLineOnEnter(){
        return newLineOnEnter;
    }
    
    public void setNewLineOnEnter(boolean newLineOnEnter){
        this.newLineOnEnter = newLineOnEnter;
    }
    
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean active){
        this.active = active;
    }
    
    
    public void clear(){
        moveCursorHome();
        cursorY = 0;
        
        lines.clear();
        lines.add("");
        
        updateCurrentLineLength();
    }

    public double getCursorBlinkingSeconds(){
        return cursorStopwatch.getSeconds();
    }
    
    public String getString(boolean inv){
        final StringJoiner joiner = new StringJoiner("\n");
        if(!inv){
            for(String line: lines)
                joiner.add(line);
        }else{
            for(int i = lines.size() - 1; i >= 0; i--)
                joiner.add(lines.get(i));
        }
        
        return joiner.toString();
    }
    
    @Override
    public void dispose(){
        lines.clear();
        Pize.keyboard().removeCharCallback(this);
        Pize.keyboard().removeKeyCallback(this);
    }
    
}
