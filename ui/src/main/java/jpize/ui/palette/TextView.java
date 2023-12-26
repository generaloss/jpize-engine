package jpize.ui.palette;

import jpize.graphics.font.BitmapFont;
import jpize.graphics.util.color.Color;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;

public class TextView extends UIComponent{

    private String text;
    private BitmapFont font;
    private final Color color;

    public TextView(String text, BitmapFont font){
        style.background().color().setA(0);
        input.setClickable(false);
        size.set(Constr.px(font.getTextWidth(text)), Constr.px(font.options().getAdvanceScaled()));
        this.text = text;
        this.font = font;
        this.color = new Color();
    }

    @Override
    public void render(){
        super.render();
        cache.calculate();
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

}