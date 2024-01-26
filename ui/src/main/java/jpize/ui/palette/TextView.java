package jpize.ui.palette;

import jpize.graphics.font.Font;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;
import jpize.util.math.vecmath.vector.Vec2f;

public class TextView extends UIComponent{

    private String text;
    private Font font;
    private final Color color;
    private Constraint text_size;
    private float text_scale;
    private boolean wrap;
    private Vec2f bounds;

    public TextView(String text, Font font, Constraint text_size){
        super.style.background().color().setA(0);
        this.color = new Color();
        this.text = text;
        this.font = font;
        this.text_size = text_size;
    }

    public TextView(String text, Font font){
        this(text, font, Constr.match_parent);
    }

    @Override
    public void update(){
        // init
        final float cache_text_size = cache.constrToPx(text_size, true, true);
        final float advance = font.options.getAdvance();
        text_scale = cache_text_size / advance;

        // font options
        font.setScale(text_scale);
        font.options.invLineWrap = true;
        if(wrap && cache.hasParent)
            font.options.textAreaWidth = Math.max(0, cache.parentWidth() - cache.parent.cache().marginRight);
        else
            font.options.textAreaWidth = -1;

        // bounds
        bounds = font.getBounds(text);

        // size
        size.set(Constr.px(bounds.x), Constr.px(bounds.y));
        cache.calculate();
    }

    @Override
    public void render(){
        super.render();

        font.options.color.set(color);

        font.drawText(context.renderer().batch(), text, cache.x, cache.y + bounds.y - font.options.getDescentScaled());
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
        size.setX(Constr.px(font.getTextWidth(text)));
    }

    public Color color(){
        return color;
    }

    public void setTextSize(Constraint text_size){
        this.text_size = text_size;
    }

}