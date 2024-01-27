package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.util.batch.TextureBatch;
import jpize.app.JpizeApplication;
import jpize.util.math.Maths;
import jpize.util.math.vecmath.vector.Vec2i;
import jpize.sdl.input.Key;
import jpize.io.TextProcessor;

import java.util.List;
import java.util.StringJoiner;

public class TextEditor extends JpizeApplication{

    public static final int beginX = 100;

    private TextureBatch batch;
    private Font font;
    private TextProcessor text;

    private Vec2i dragCursorPos;
    private String selectedText;

    @Override
    public void init(){
        batch = new TextureBatch();

        text = new TextProcessor(true, true);

        font = FontLoader.getDefault();
        font.setScale(1F);

        dragCursorPos = new Vec2i();
        selectedText = "";
    }

    @Override
    public void update(){
        final boolean hasCtrl = Jpize.input().keyMods().hasCtrl();

        if(hasCtrl && Key.Y.isDown())
            text.removeLine();

        if(hasCtrl && Key.V.isDown())
            text.insertText(Jpize.getClipboardText());

        if(hasCtrl && Key.C.isDown())
            Jpize.setClipboardText(selectedText);

        if(Jpize.isTouched()){
            final int x = Jpize.getX() - beginX;
            final int y = Maths.floor((Jpize.getY() / font.options.getAdvanceScaled()));

            final int cursorY = text.getLines().size() - 1 - y;
            text.setCursorY(cursorY);
            final String curLine = text.getCurrentLine();

            int cursorX = 0;
            for(GlyphSprite glyph: font.iterable(curLine)){
                if(glyph.getX() + glyph.getWidth() / 2 > x)
                    break;

                cursorX++;
            }

            text.setCursorX(cursorX);

            if(Jpize.isTouchDown())
                dragCursorPos.set(cursorX, cursorY);

            selectedText = text.getString(dragCursorPos.x, dragCursorPos.y, cursorX, cursorY);
        }
    }

    @Override
    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();

        final String string = text.getString(true);
        final float advance = font.options.getAdvanceScaled();

        // Iterate lines
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        final List<String> lines = text.getLines();
        for(int i = 0; i < lines.size(); i++){
            // Add line number
            lineNumbersJoiner.add(String.valueOf(lines.size() - i));

            // Draw line background
            final float lineWidth = font.getTextWidth(lines.get(i));
            batch.drawRect(0.1, 0.15, 0.2, 1,  beginX, 10 + (lines.size() - 1 - i) * advance,  lineWidth, advance);
            batch.drawRect(0.3, 0.45, 0.5, 1,  0 , 10 + (lines.size() - 1 - i) * advance,  beginX, advance);
        }
        // Draw line numbers
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);

        // Draw text
        font.drawText(batch, string, beginX, 10);

        // Draw cursor
        if(text.isCursorRender()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = (text.getCursorY() + 1) * advance - advance * text.getLines().size();
            final float cursorX = font.getTextWidth(currentLine.substring(0, text.getCursorX()));
            batch.drawRect(1, 1, 1, 1,  beginX + cursorX, 10 - cursorY, 2, advance);
        }

        font.drawText(batch, selectedText, Jpize.getWidth() - font.getTextWidth(selectedText), Jpize.getHeight() - font.getTextHeight(selectedText));

        batch.end();

        if(string.equals("exit()"))
            Jpize.exit();
    }

    @Override
    public void dispose(){
        text.dispose();
        batch.dispose();
        font.dispose();
    }

}