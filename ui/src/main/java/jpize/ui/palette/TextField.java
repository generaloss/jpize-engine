package jpize.ui.palette;

import jpize.graphics.font.Font;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.TextProcessor;
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

    public TextField(Constraint width, Constraint height, Font font, String text){
        super.size.set(width, height);
        this.font = font;
        this.color = new Color(0);

        super.input.setClickable(true);

        this.processor = new TextProcessor(false, false);
        setText(text);

        this.cursor = new Rect(Constr.px(4), Constr.match_parent);
        this.cursor.style().background().color().set(0.5);
        this.cursor.setHidden(true);
        super.add(cursor);
    }

    public TextField(Constraint width, Constraint height, Font font){
        this(width, height, font, "");
    }

    @Override
    public void update(){
        cache.calculate();

        // scale
        font.setScale(cache.height / (font.info.getHeight()));

        // set text
        if(processor.isEnabled()){
            text = processor.getString();
            cursor.setHidden(processor.getCursorBlinkingSeconds() % 1 > 0.5);
        }

        // cursor x
        cursor.padding().left = Constr.px(font.getTextWidth(text));
    }

    @Override
    public void render(){
        final TextureBatch batch = super.context.renderer().batch();

        // System.out.println(cache.height + " " + font.options.getLineHeightScaled());

        font.options.color.set(color);
        font.drawText(batch, text, cache.x, cache.y + font.options.getAdvanceScaled());
    }

    @Override
    public void onSelect(){
        processor.enable();
        processor.resetCursorBlinking();
        cursor.setHidden(false);
    }

    @Override
    public void onDeselect(){
        processor.disable();
        cursor.setHidden(true);
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
        processor.insertText(text);
    }


    public Color color(){
        return color;
    }

}
