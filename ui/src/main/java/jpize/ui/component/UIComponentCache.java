package jpize.ui.component;

import jpize.Jpize;
import jpize.util.math.Maths;
import jpize.ui.constraint.Constraint;
import jpize.ui.constraint.ConstraintFlag;
import jpize.ui.constraint.ConstraintNum;

public class UIComponentCache{

    private final UIComponent component;
    public boolean precalculatedHeightFlag, autoConstrFlag;

    public UIComponent parent;
    public boolean hasParent;

    public float x, y, width, height;

    public float widthMin, heightMin, widthMax, heightMax;
    public boolean hasWidthMax, hasHeightMax;

    public float contentWidth, contentHeight;

    public float marginTop, marginLeft, marginBottom, marginRight;
    public float paddingTop, paddingLeft, paddingBottom, paddingRight;
    public boolean hasPaddingTop, hasPaddingLeft, hasPaddingBottom, hasPaddingRight;
    public float paddingX, paddingY;

    public float cornerRadius, borderSize;
    public boolean pressed;

    public UIComponentCache(UIComponent component){
        this.component = component;
    }

    public void calculate(){
        updateParent();
        calcMargin();
        calcSize();
        calcPadding();
        calcPosition();
        calcStyle();
    }

    private void updateParent(){
        parent = component.parent;
        hasParent = parent != null;
    }

    private void calcSize(){
        // minSize
        widthMin = constrToPx(component.minSize().x, false, true);
        heightMin = constrToPx(component.minSize().y, true, true);

        // maxSize
        widthMax = constrToPx(component.maxSize().x, false, true);
        hasWidthMax = !autoConstrFlag;
        heightMax = constrToPx(component.maxSize().y, true, true);
        hasHeightMax = !autoConstrFlag;

        // size
        precalculatedHeightFlag = false;

        width = constrToPx(component.size().x, false, true);
        if(!precalculatedHeightFlag)
            height = constrToPx(component.size().y, true, true);

        // clamp
        if(hasWidthMax) width = Maths.clamp(width, widthMin, widthMax);
        else width = Math.max(width, widthMin);

        if(hasHeightMax) height = Maths.clamp(height, heightMin, heightMax);
        else height = Math.max(height, heightMin);

        // content size
        contentWidth = width - marginLeft - marginRight;
        contentHeight = height - marginTop - marginBottom;
    }

    private void calcMargin(){
        marginTop = Math.max(0, constrToPx(component.margin.top, true, false));
        marginLeft = Math.max(0, constrToPx(component.margin.left, false, false));
        marginBottom = Math.max(0, constrToPx(component.margin.bottom, true, false));
        marginRight = Math.max(0, constrToPx(component.margin.right, false, false));
    }

    private void calcPadding(){
        // top
        paddingTop = Math.max(0, constrToPx(component.padding.top, true, false));
        hasPaddingTop = !autoConstrFlag;
        // left
        paddingLeft = Math.max(0, constrToPx(component.padding.left, false, false));
        hasPaddingLeft = !autoConstrFlag;
        // bottom
        paddingBottom = Math.max(0, constrToPx(component.padding.bottom, true, false));
        hasPaddingBottom = !autoConstrFlag;
        // right
        paddingRight = Math.max(0, constrToPx(component.padding.right, false, false));
        hasPaddingRight = !autoConstrFlag;
    }

    private void calcPosition(){
        float parentX = 0;
        float parentY = 0;
        float parentW = Jpize.getWidth();
        float parentH = Jpize.getHeight();

        if(hasParent){
            parentX = parent.cache.x;
            parentY = parent.cache.y;
            parentW = parent.cache.width;
            parentH = parent.cache.height;
        }

        if(hasPaddingLeft && hasPaddingRight) paddingX = (paddingLeft + parentW - paddingRight - width) / 2;
        else if(hasPaddingLeft) paddingX = paddingLeft + parentMarginLeft();
        else if(hasPaddingRight) paddingX = parentW - paddingRight - parentMarginRight() - width;
        else paddingX = 0;

        if(hasPaddingTop && hasPaddingBottom) paddingY = (paddingBottom + parentH - paddingTop - height) / 2;
        else if(hasPaddingTop) paddingY = parentH - paddingTop - parentMarginTop() - height;
        else if(hasPaddingBottom) paddingY = paddingBottom + parentMarginBottom();
        else paddingY = 0;

        x = parentX + paddingX;
        y = parentY + paddingY;

        if(parent instanceof AbstractLayout layout){
            x = layout.calcPosition(component, false);
            y = layout.calcPosition(component, true);
        }

        // correct
        x = Maths.round(x);
        y = Maths.round(y);
        width = Maths.round(width);
        height = Maths.round(height);
    }

    private void calcStyle(){
        cornerRadius = constrToPx(component.style.getCornerRadius(), component, false, true);
        borderSize = constrToPx(component.style.getBorderSize(), component, false, true);
    }


    public float constrToPx(Constraint c, boolean forY, boolean forSize){
        return constrToPx(c, parent, forY, forSize);
    }

    public float constrToPx(Constraint c, UIComponent relative, boolean forY, boolean forSize){
        autoConstrFlag = false;

        if(c instanceof ConstraintFlag flag)
            return constrFlagToPx(flag, relative, forY, forSize);
        if(c instanceof ConstraintNum num)
            return constrNumToPx(num, relative, forY, forSize);
        return 0;
    }

    private float constrFlagToPx(ConstraintFlag flag, UIComponent relative, boolean forY, boolean forSize){
        return switch(flag.toString()){
            default -> 0;
            case "auto" -> {
                autoConstrFlag = true;
                if(forSize){
                    if(forY)
                        yield parentMinHeight();
                    else
                        yield parentMinWidth();
                }
                yield 0;
            }
            case "wrap_content" -> {
                if(!hasParent) yield 0;
                if(parent instanceof AbstractLayout layout) yield layout.calcWrapContent(component, forY, forSize);
                yield 0;
            }
            case "match_parent" -> (forY ? parentHeight() : parentWidth());
        };
    }

    private float constrNumToPx(ConstraintNum num, UIComponent relative, boolean forY, boolean forSize){
        return switch(num.type()){
            case PX -> num.value();
            case ASPECT -> {
                if(forSize && !forY){
                    height = constrToPx(component.size().y, relative, true, true);
                    precalculatedHeightFlag = true;
                }
                yield num.value() * (forY ? width : height);
            }
            case REL_W -> num.value() * width(relative);
            case REL_H -> num.value() * height(relative);
        };
    }


    public float width(UIComponent component){
        if(component == null) return Jpize.getWidth();
        return component.cache().width;
    }

    public float parentWidth(){
        return width(parent);
    }

    public float height(UIComponent component){
        if(component == null) return Jpize.getHeight();
        return component.cache().height;
    }

    public float parentHeight(){
        return height(parent);
    }


    public float parentMinWidth(){
        if(parent == null) return 0;
        return parent.cache().widthMin;
    }

    public float parentMinHeight(){
        if(parent == null) return 0;
        return parent.cache().heightMin;
    }

    public float parentMaxWidth(){
        if(parent == null) return 0;
        return parent.cache().widthMax;
    }

    public float parentMaxHeight(){
        if(parent == null) return 0;
        return parent.cache().heightMax;
    }


    public float parentMarginTop(){
        if(parent == null) return 0;
        return parent.cache().marginTop;
    }

    public float parentMarginLeft(){
        if(parent == null) return 0;
        return parent.cache().marginLeft;
    }

    public float parentMarginBottom(){
        if(parent == null) return 0;
        return parent.cache().marginBottom;
    }

    public float parentMarginRight(){
        if(parent == null) return 0;
        return parent.cache().marginRight;
    }


    public void press(){
        pressed = true;
    }

    public boolean release(){
        boolean pressed_last = pressed;
        pressed = false;
        return pressed_last;
    }

}
