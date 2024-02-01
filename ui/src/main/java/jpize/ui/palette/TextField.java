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
import jpize.ui.palette.callback.TextFieldEnterCallback;
import jpize.ui.palette.callback.TextFieldInputCallback;
import jpize.util.color.Color;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextField extends UIComponent{

    private String text;
    private Font font;
    private final Color color;

    private final Rect cursor;

    private final TextProcessor _processor;
    private final KeyCallback _keyCallback;
    private String _oldText;

    private final List<TextFieldInputCallback> inputCallbacks;
    private final List<TextFieldEnterCallback> enterCallbacks;

    public TextField(Constraint width, Constraint height, Font font, String text){
        super.size.set(width, height);
        super.style.background().color().set(0.85);
        super.input.setClickable(true);

        this.font = font;
        this.color = new Color(0.1);
        this.inputCallbacks = new CopyOnWriteArrayList<>();
        this.enterCallbacks = new CopyOnWriteArrayList<>();

        this._processor = new TextProcessor(false, false);
        setText(text);

        this.cursor = new Rect(Constr.relh(0.07), Constr.match_parent);
        this.cursor.style().background().color().set(0.38, 0, 0.9);
        this.cursor.setHidden(true);
        super.add(cursor);

        this._keyCallback = (key, action, mods) -> {
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
        };
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
        if(input.isFocused() && Btn.LEFT.isPressed()){
            final float touchX = Jpize.getX() - super.cache.x;

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
        final TextureBatch batch = super.context.renderer().batch();
        font.options.color.set(color);
        font.drawText(batch, text, cache.x + cache.marginLeft, cache.y + cache.marginBottom + font.options.getAdvanceScaled());
    }

    @Override
    public void onFocus(){
        _processor.enable();
        _processor.resetCursorBlinking();
        cursor.setHidden(false);
        Jpize.context().callbacks().addKeyCallback(_keyCallback);
    }

    @Override
    public void onUnfocus(){
        _processor.disable();
        cursor.setHidden(true);
        Jpize.context().callbacks().removeKeyCallback(_keyCallback);
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

}
