package jpize.util.io;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.util.Disposable;
import jpize.glfw.input.GlfwMod;
import jpize.glfw.input.GlfwMods;
import jpize.glfw.keyboard.callback.GlfwCharCallback;
import jpize.glfw.keyboard.callback.GlfwKeyCallback;
import jpize.glfw.key.Key;
import jpize.glfw.input.GlfwAction;
import jpize.util.StringUtils;
import jpize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TextProcessor implements Disposable, GlfwCharCallback, GlfwKeyCallback{
    
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

        Jpize.keyboard().addCharCallback(this);
        Jpize.keyboard().addKeyCallback(this);
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
    public void invoke(Key key, int scancode, GlfwAction action, GlfwMods mods){
        if(!active || action == GlfwAction.RELEASE)
            return;

        // Moving cursor
        moveCursor(key);

        // Tab
        if(mods.has(GlfwMod.SHIFT) && key == Key.TAB){
            removeTab();
            resetCursorBlinking();
        }else if(key == Key.TAB){
            insertTab();
            resetCursorBlinking();
        }

        // New line
        if(newLineOnEnter && key == Key.ENTER){
            newLineAndWrap();
            resetCursorBlinking();
        }

        // Backspace
        else if(key == Key.BACKSPACE && !(cursorX == 0 && cursorY == 0)){
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
    
    
    private void moveCursor(Key key){
        if(key == Key.END  ) moveCursorEnd();
        if(key == Key.HOME ) moveCursorHome();
        if(key == Key.UP   ) moveCursorUp();
        if(key == Key.DOWN ) moveCursorDown();
        if(key == Key.LEFT ) moveCursorLeft();
        if(key == Key.RIGHT) moveCursorRight();
        
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

    public boolean isCursorRender(){
        return (getCursorBlinkingSeconds() < 1 || Maths.frac(getCursorBlinkingSeconds()) >= 0.5) && isActive();
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

    public String getString(){
        return getString(false);
    }
    
    @Override
    public void dispose(){
        lines.clear();
        Jpize.keyboard().removeCharCallback(this);
        Jpize.keyboard().removeKeyCallback(this);
    }
    
}
