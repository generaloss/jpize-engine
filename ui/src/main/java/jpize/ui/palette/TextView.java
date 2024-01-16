package jpize.ui.palette;

import jpize.graphics.font.BitmapFont;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;

public class TextView extends UIComponent{

    private String text;
    private BitmapFont font;
    private final Color color;
    private Constraint text_size;
    private float text_scale;

    public TextView(String text, BitmapFont font, Constraint text_size){
        style.background().color().setA(0);
        input.setClickable(false);
        this.color = new Color();
        this.text = text;
        this.font = font;
        this.text_size = text_size;
    }

    public TextView(String text, BitmapFont font){
        this(text, font, Constr.match_parent);
    }

    @Override
    public void update(){
        final float cache_text_size = cache.constrToPx(text_size, true, true);
        final float advance = font.options().getAdvance();
        text_scale = cache_text_size / advance;

        font.setScale(text_scale);
        size.set(Constr.px( font.getTextWidth(text) ), Constr.px( font.options().getAdvanceScaled() ));
        cache.calculate();
    }

    @Override
    public void render(){
        super.render();

        super.renderBackground();
        font.options().color.set(color);

        font.drawText(renderer.batch(), text, cache.x, cache.y);
    }

    public BitmapFont getFont(){
        return font;
    }

    public void setFont(BitmapFont font){
        this.font = font;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
        size.setX(Constr.px(font.getTextWidth(text)));
    }

    public Color color(){
        return color;
    }

    public void setTextSize(Constraint text_size){
        this.text_size = text_size;
    }

}