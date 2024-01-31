package jpize.ui.palette;

import jpize.Jpize;
import jpize.graphics.font.Font;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.TextProcessor;
import jpize.sdl.event.callback.keyboard.KeyCallback;
import jpize.sdl.input.Btn;
import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;

public class TextField extends UIComponent{

    private String text;
    private Font font;
    private final Color color;

    private final TextProcessor processor;
    private final Rect cursor;
    private final KeyCallback keyCallback;

    public TextField(Constraint width, Constraint height, Font font, String text){
        super.size.set(width, height);
        super.style.background().color().set(0.85);

        this.font = font;
        this.color = new Color(0.1);

        super.input.setClickable(true);

        this.processor = new TextProcessor(false, false);
        setText(text);

        this.cursor = new Rect(Constr.relh(0.07), Constr.match_parent);
        this.cursor.style().background().color().set(0.4, 0.1, 0.9);
        this.cursor.setHidden(true);
        super.add(cursor);

        this.keyCallback = (key, action, mods) -> {
            if(action != KeyAction.DOWN || !mods.hasCtrl())
                return;
            if(key == Key.C)
                Jpize.setClipboardText(processor.getString());
            else if(key == Key.V)
                processor.insertText(Jpize.getClipboardText().replace("\n", ""));
        };
    }

    public TextField(Constraint width, Constraint height, Font font){
        this(width, height, font, "");
    }

    @Override
    public void update(){
        cache.calculate();

        // scale, disable wrapping
        font.setScale(cache.height / (font.info.getHeight()));
        font.options.wrapThreesholdWidth = -1;

        // set text
        if(processor.isEnabled()){
            text = processor.getString();
            cursor.setHidden(processor.getCursorBlinkingSeconds() % 1 > 0.5);
        }

        // cursor x
        if(input.isFocused() && Btn.LEFT.isPressed()){
            final float touchX = Jpize.getX() - super.cache.x;

            int cursorX = 0;
            for(GlyphSprite glyph: font.iterable(text)){
                if(glyph.getX() + glyph.getWidth() / 2 > touchX)
                    break;
                cursorX++;
            }
            processor.setCursorX(cursorX);
        }

        cursor.padding().left = Constr.px(font.getTextWidth(text.substring(0, processor.getCursorX())));
    }

    @Override
    public void render(){
        final TextureBatch batch = super.context.renderer().batch();

        font.options.color.set(color);
        font.drawText(batch, text, cache.x, cache.y + font.options.getAdvanceScaled());
    }

    @Override
    public void onFocus(){
        processor.enable();
        processor.resetCursorBlinking();
        cursor.setHidden(false);
        Jpize.context().callbacks().addKeyCallback(keyCallback);
    }

    @Override
    public void onUnfocus(){
        processor.disable();
        cursor.setHidden(true);
        Jpize.context().callbacks().removeKeyCallback(keyCallback);
    }


    public Font getFont(){
        return font;
    }

    public void setFont(Font font){
        this.font = font;
    }


    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
        processor.clear();
        processor.insertText(text.replace("\n", ""));
    }

    public Color color(){
        return color;
    }

}
