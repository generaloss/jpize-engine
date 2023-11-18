package jpize.util.io;

import jpize.Jpize;
import jpize.graphics.font.FontCharset;
import jpize.math.Maths;
import jpize.sdl.event.CharCallback;
import jpize.sdl.event.KeyCallback;
import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.sdl.input.KeyMods;
import jpize.util.Disposable;
import jpize.util.StringUtils;
import jpize.util.time.Stopwatch;

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

    private final List<Character> stopCursorChars;
    
    public TextProcessor(boolean newLineOnEnter){
        this.active = true;
        this.newLineOnEnter = newLineOnEnter;
        this.lines = new ArrayList<>();
        this.lines.add("");
        this.cursorStopwatch = new Stopwatch().start();

        this.stopCursorChars = new ArrayList<>();
        for(char c: FontCharset.SPECIAL_SYMBOLS.toArray())
            stopCursorChars.add(c);

        Jpize.context().getCallbacks().addCharCallback(this);
        Jpize.context().getCallbacks().addKeyCallback(this);
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
    public void invoke(Key key, KeyAction action, KeyMods mods){
        if(!active || action == KeyAction.UP)
            return;

        final boolean hasCtrl = mods.hasCtrl();

        // Moving cursor
        moveCursor(key, hasCtrl);

        // Tab
        if(mods.hasShift() && key == Key.TAB){
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
            else{
                do{
                    removeChar();
                }while(hasCtrl && cursorX > 0 && !stopCursorChars.contains(getCurrentLine().charAt(cursorX - 1)));
            }

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
    
    public void insertLine(String line){
        line = line.replaceAll("\n", "");
        
        String currentLine = getCurrentLine();
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
        String line = getCurrentLine();
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
        final String prevLine = getCurrentLine();
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
    

    public void setCursorPos(int x, int y){
        setCursorY(y);
        setCursorX(x);
    }

    public void setCursorX(int x){
        cursorX = Maths.clamp(x, 0, getCurrentLine().length());
        resetCursorBlinking();
    }

    public void setCursorY(int y){
        cursorY = Maths.clamp(y, 0, lines.size() - 1);
        updateCurrentLineLength();
        resetCursorBlinking();
    }

    private void moveCursor(Key key, boolean hasCtrl){
        if(key == Key.END  ) moveCursorEnd();
        if(key == Key.HOME ) moveCursorHome();
        if(key == Key.UP   ) moveCursorUp();
        if(key == Key.DOWN ) moveCursorDown();
        if(key == Key.LEFT ) moveCursorLeft(hasCtrl);
        if(key == Key.RIGHT) moveCursorRight(hasCtrl);
        
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
    
    public void moveCursorLeft(boolean hasCtrl){
        if(cursorX > 0){
            final String curLine = getCurrentLine();
            do{
                cursorX--;
            }while(hasCtrl && cursorX > 0 && !stopCursorChars.contains(curLine.charAt(cursorX - 1)));
        }else if(cursorY > 0){
            cursorY--;
            updateCurrentLineLength();
            moveCursorEnd();
        }
        resetCursorBlinking();
    }
    
    public void moveCursorRight(boolean hasCtrl){
        if(cursorX < currentLineLength){
            final String curLine = getCurrentLine();
            do{
                cursorX++;
            }while(hasCtrl && cursorX < curLine.length() && !stopCursorChars.contains(curLine.charAt(cursorX)));
        }else if(cursorY < lines.size() - 1){
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
        return getLine(cursorY);
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

    public String getLine(int cursorY){
        if(cursorY >= lines.size() || cursorY < 0)
            return null;

        return lines.get(cursorY);
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

    public String getString(int x1, int y1, int x2, int y2){
        final StringBuilder builder = new StringBuilder();

        x1 = Math.max(x1, 0);
        x2 = Math.max(x2, 0);

        final int startY = Math.min(y1, y2);
        final int endY = Math.max(y1, y2);

        if(startY == endY){
            final String line = getLine(startY);

            final int startX = Maths.clamp(Math.min(x1, x2), 0, line.length());
            final int endX = Maths.clamp(Math.max(x1, x2), 0, line.length());

            return line.substring(startX, endX);
        }

        for(int y = startY; y <= endY; y++){
            final String line = getLine(y);
            if(line == null)
                break;

            if(y == startY){
                builder.append(line.substring(Math.min(x1, line.length())));
            }else if(y == endY){
                builder.append(line, 0, Math.min(x2, line.length()));
                break;
            }else{
                builder.append(line);
            }

            builder.append("\n");
        }

        return builder.toString();
    }


    public List<Character> getStopCursorChars(){
        return stopCursorChars;
    }

    
    @Override
    public void dispose(){
        lines.clear();
        Jpize.context().getCallbacks().removeCharCallback(this);
        Jpize.context().getCallbacks().removeKeyCallback(this);
    }
    
}
