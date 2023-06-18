package pize.util.io;

import pize.Pize;
import pize.app.Disposable;
import pize.io.keyboard.CharCallback;
import pize.io.keyboard.KeyCallback;
import pize.io.glfw.Key;
import pize.io.glfw.KeyAction;
import pize.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TextProcessor implements Disposable, CharCallback, KeyCallback{
    
    private boolean active, newLineOnEnter;
    private final List<String> lines;
    private int currentLineLength;
    
    private int cursorX, cursorY;
    private boolean cursorInEnd;
    private int tabSpaces = 4;
    
    public TextProcessor(boolean newLineOnEnter){
        active = true;
        this.newLineOnEnter = newLineOnEnter;
        lines = new ArrayList<>();
        lines.add("");
        
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
    }
    
    @Override
    public void invoke(int keyCode, KeyAction action){
        if(!active || action == KeyAction.RELEASE)
            return;
        
        moveCursor(keyCode);
        
        if(keyCode == Key.TAB.GLFW)
            insertLine(" ".repeat(tabSpaces));
        if(newLineOnEnter && keyCode == Key.ENTER.GLFW)
            newLineAndWrap();
        
        else if(keyCode == Key.BACKSPACE.GLFW && !(cursorX == 0 && cursorY == 0)){
            if(cursorX == 0)
                removeLineAndWrap();
            else
                removeChar();
        }
    }
    
    
    private void removeChar(){
        String line = lines.get(cursorY);
        lines.set(cursorY, line.substring(0, cursorX - 1) + line.substring(cursorX));
        
        cursorX--;
        currentLineLength--;
    }
    
    public void insertChar(char character){
        String currentLine = lines.get(cursorY);
        lines.set(cursorY, currentLine.substring(0, cursorX) + character + currentLine.substring(cursorX));
        
        cursorX++;
        currentLineLength++;
    }
    
    public void insertLine(String line){
        String currentLine = lines.get(cursorY);
        currentLine = currentLine.substring(0, cursorX) + line + currentLine.substring(cursorX);
        lines.set(cursorY, currentLine);
        
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
    
    
    public void newLineAndWrap(){
        final String prevLine = lines.get(cursorY);
        lines.set(cursorY, prevLine.substring(0, cursorX));
        
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
        lines.set(cursorY, lines.get(cursorY) + removedLine);
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
    }
    
    public void moveCursorHome(){
        cursorX = 0;
    }
    
    public void moveCursorDown(){
        if(cursorY < lines.size() - 1){
            cursorY++;
            updateCurrentLineLength();
            norCursorX();
        }
    }
    
    public void moveCursorUp(){
        if(cursorY > 0){
            cursorY--;
            updateCurrentLineLength();
            norCursorX();
        }
    }
    
    public void moveCursorLeft(){
        if(cursorX > 0)
            cursorX--;
        else if(cursorY > 0){
            cursorY--;
            updateCurrentLineLength();
            moveCursorEnd();
        }
    }
    
    public void moveCursorRight(){
        if(cursorX < currentLineLength)
            cursorX++;
        else if(cursorY < lines.size() - 1){
            cursorY++;
            updateCurrentLineLength();
            moveCursorHome();
        }
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
    }
    
    @Override
    public String toString(){
        final StringJoiner joiner = new StringJoiner("\n");
        for(String line: lines)
            joiner.add(line);
        
        return joiner.toString();
    }
    
    @Override
    public void dispose(){
        lines.clear();
        Pize.keyboard().removeCharCallback(this);
        Pize.keyboard().removeKeyCallback(this);
    }
    
}
