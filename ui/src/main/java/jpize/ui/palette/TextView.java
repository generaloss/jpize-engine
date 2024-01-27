package jpize.ui.palette;

import jpize.graphics.font.Font;
import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;
import jpize.util.math.vecmath.vector.Vec2f;

public class TextView extends UIComponent{

    private String text;
    private Font font;
    private Constraint text_size;
    private final Color color;
    private boolean wrap;

    private float _text_scale;
    private Vec2f _bounds;

    public TextView(String text, Font font, Constraint textSize){
        this.text = text;
        this.font = font;
        this.text_size = textSize;
        this.color = new Color(0.1);

        super.style.background().color().setA(0);
    }

    public TextView(String text, Font font){
        this(text, font, Constr.match_parent);
    }

    @Override
    public void update(){
        // init
        final float cache_text_size = cache.constrToPx(text_size, true, true);
        final float advance = font.options.getAdvance();
        _text_scale = cache_text_size / advance;

        // font options
        font.setScale(_text_scale);
        font.options.invLineWrap = true;
        if(wrap && cache.hasParent)
            font.options.textAreaWidth = Math.max(0, cache.parentWidth() - cache.parent.cache().marginRight);
        else
            font.options.textAreaWidth = -1;

        // bounds
        _bounds = font.getMaxBounds(text);

        // size
        size.set(Constr.px(_bounds.x), Constr.px(_bounds.y));
        cache.calculate();
    }

    @Override
    public void render(){
        font.options.color.set(color);
        font.drawText(context.renderer().batch(), text, cache.x, cache.y + cache.height);
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