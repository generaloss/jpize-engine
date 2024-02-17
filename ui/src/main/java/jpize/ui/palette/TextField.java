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
import jpize.sdl.input.KeyMods;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextField extends UIComponent{

    private Font font;
    private String text;
    private final Color color;
    private String hint;
    private final Color hint_color;
    private final Rect cursor;

    private final TextProcessor _processor;
    private final KeyCallback _keyCallback;
    private String _oldText;

    private final List<TextFieldInputCallback> inputCallbacks;
    private final List<TextFieldEnterCallback> enterCallbacks;

    public TextField(Constraint width, Constraint height, Font font, String text){
        super.size.set(width, height);
        super.background().color().set(0.85);
        super.setCornerRadius(Constr.relh(0.1));
        super.setClickable(true);
        super.margin.set(Constr.relh(0.05));
        super.addFocusCallback(this::focusCallback);

        this._keyCallback = this::keyCallback;

        this.font = font;
        this.color = new Color(0.1);
        this.hint_color = new Color(0.55);
        this.inputCallbacks = new CopyOnWriteArrayList<>();
        this.enterCallbacks = new CopyOnWriteArrayList<>();
        this._processor = new TextProcessor(false, false);

        this.cursor = new Rect(Constr.relh(0.07), Constr.match_parent);
        this.cursor.background().color().set(0.38, 0, 0.9);
        this.cursor.setHidden(true);
        super.add(cursor);

        setText(text);
    }

    public TextField(Constraint width, Constraint height, Font font){
        this(width, height, font, "");
    }


    @Override
    public void update(){
        cache.calculate();

        // scale, disable wrapping
        font.setScale(cache.containerHeight / (font.info.getHeight()));
        font.options.wrapThreesholdWidth = -1;

        // set text
        if(_processor.isEnabled()){
            cursor.setHidden(_processor.getCursorBlinkingSeconds() % 1 > 0.5);
            text = _processor.getString();

            if(!text.equals(_oldText)){
                invokeInputCallbacks();
                _oldText = text;
            }
        }

        // cursor x
        if(super.isFocused() && Btn.LEFT.isPressed()){
            final float touchX = Jpize.getX() - super.cache.x - super.cache.marginLeft;

            int cursorX = 0;
            for(GlyphSprite glyph: font.iterable(text)){
                if(glyph.getX() + glyph.getWidth() / 2 > touchX)
                    break;
                cursorX++;
            }
            _processor.setCursorX(cursorX);
        }

        cursor.padding().left = Constr.px(font.getTextWidth(text.substring(0, _processor.getCursorX())));
    }

    @Override
    public void render(){
        final boolean drawHint = (text == null || text.isEmpty());
        if(drawHint && (hint == null || hint.isBlank()))
            return;
        final TextureBatch batch = super.context.renderer().batch();

        font.options.color.set(drawHint ? hint_color : color);

        final float x = cache.x + cache.marginLeft;
        final float y = cache.y + cache.marginBottom + font.options.getAdvanceScaled();
        if(drawHint)
            font.drawText(batch, hint, x, y);
        else
            font.drawText(batch, text, x, y);
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
        this.text = text.replace("\n", "");
        _oldText = this.text;
        _processor.clear();
        _processor.insertText(this.text);
        invokeInputCallbacks();
    }


    public String getHint(){
        return hint;
    }

    public void setHint(String hint){
        this.hint = hint;
    }


    public Color color(){
        return color;
    }


    public void addInputCallback(TextFieldInputCallback callback){
        inputCallbacks.add(callback);
    }

    public void removeInputCallback(TextFieldInputCallback callback){
        inputCallbacks.remove(callback);
    }

    private void invokeInputCallbacks(){
        for(TextFieldInputCallback callback: inputCallbacks)
            callback.invoke(this, text);
    }


    public void addEnterCallback(TextFieldEnterCallback callback){
        enterCallbacks.add(callback);
    }

    public void removeEnterCallback(TextFieldEnterCallback callback){
        enterCallbacks.remove(callback);
    }

    private void invokeEnterCallbacks(){
        for(TextFieldEnterCallback callback: enterCallbacks)
            callback.invoke(this, text);
    }


    private void keyCallback(Key key, KeyAction action, KeyMods mods){
        if(action != KeyAction.DOWN)
            return;
        if(key == Key.ENTER)
            invokeEnterCallbacks();

        if(!mods.hasCtrl())
            return;
        if(key == Key.C)
            Jpize.setClipboardText(_processor.getString());
        else if(key == Key.V){
            _processor.insertText(Jpize.getClipboardText().replace("\n", ""));
            invokeInputCallbacks();
        }
    }

    private void focusCallback(UIComponent view, boolean focus){
        if(focus){
            _processor.enable();
            _processor.resetCursorBlinking();
            cursor.setHidden(false);
            Jpize.context().callbacks().addKeyCallback(_keyCallback);
        }else{
            _processor.disable();
            cursor.setHidden(true);
            Jpize.context().callbacks().removeKeyCallback(_keyCallback);
        }
    }

}
