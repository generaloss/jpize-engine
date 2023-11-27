package jpize.ui.component;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.ui.constraint.Constraint;
import jpize.ui.constraint.ConstraintFlag;
import jpize.ui.constraint.ConstraintNum;

public class UIComponentCache{

    private final UIComponent component;
    private boolean precalculatedHeightFlag, autoConstrFlag;

    public float x, y, width, height;

    public float widthMin, heightMin, widthMax, heightMax;
    public boolean hasWidthMax, hasHeightMax;

    public float paddingTop, paddingLeft, paddingBottom, paddingRight;
    public boolean hasPaddingTop, hasPaddingLeft, hasPaddingBottom, hasPaddingRight;

    public UIComponentCache(UIComponent component){
        this.component = component;
    }

    public void calculate(){
        // size
        calcSize();
        // padding
        calcPadding();
        // position
        calcPosition();
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
    }

    private void calcPadding(){
        // top
        paddingTop = Math.max(0, constrToPx(component.padding().top, true, false));
        hasPaddingTop = !autoConstrFlag;
        // left
        paddingLeft = Math.max(0, constrToPx(component.padding().left, false, false));
        hasPaddingLeft = !autoConstrFlag;
        // bottom
        paddingBottom = Math.max(0, constrToPx(component.padding().bottom, true, false));
        hasPaddingBottom = !autoConstrFlag;
        // right
        paddingRight = Math.max(0, constrToPx(component.padding().right, false, false));
        hasPaddingRight = !autoConstrFlag;
    }

    private void calcPosition(){
        final UIComponent parent = component.parent();

        if(hasPaddingLeft && hasPaddingRight) x = (paddingLeft + Jpize.getWidth() - paddingRight - width) / 2;
        else if(hasPaddingLeft) x = paddingLeft;
        else if(hasPaddingRight) x = Jpize.getWidth() - paddingRight - width;
        else x = 0;

        if(hasPaddingTop && hasPaddingBottom) y = (paddingBottom + Jpize.getHeight() - paddingTop - height) / 2;
        else if(hasPaddingTop) y = Jpize.getHeight() - paddingTop - height;
        else if(hasPaddingBottom) y = paddingBottom;
        else y = 0;

        if(parent instanceof LayoutComponent layout){
            x = layout.calcPosition(component, false);
            y = layout.calcPosition(component, true);
        }
    }


    private float constrToPx(Constraint c, boolean forY, boolean forSize){
        autoConstrFlag = false;

        if(c instanceof ConstraintFlag flag)
            return constrFlagToPx(flag, forY, forSize);
        if(c instanceof ConstraintNum num)
            return constrNumToPx(num, forY, forSize);
        return 0;
    }

    private float constrFlagToPx(ConstraintFlag flag, boolean forY, boolean forSize){
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
                final UIComponent parent = component.parent();
                if(parent == null) yield 0;
                if(parent instanceof LayoutComponent layout) yield layout.calcWrapContent(component, forY, forSize);
                yield 0;
            }
            case "match_parent" -> forY ? parentHeight() : parentWidth();
        };
    }

    private float constrNumToPx(ConstraintNum num, boolean forY, boolean forSize){
        return switch(num.type()){
            case PX -> num.value();
            case ASPECT -> {
                if(forSize && !forY){
                    height = constrToPx(component.size().y, true, true);
                    precalculatedHeightFlag = true;
                }
                yield num.value() * (forY ? width : height);
            }
            case REL_W -> num.value() * parentWidth();
            case REL_H -> num.value() * parentHeight();
        };
    }


    private float parentWidth(){
        final UIComponent parent = component.parent();
        if(parent == null) return Jpize.getWidth();
        return parent.cache().width;
    }

    private float parentHeight(){
        final UIComponent parent = component.parent();
        if(parent == null) return Jpize.getHeight();
        return parent.cache().height;
    }

    private float parentMinWidth(){
        final UIComponent parent = component.parent();
        if(parent == null) return 0;
        return parent.cache().widthMin;
    }

    private float parentMinHeight(){
        final UIComponent parent = component.parent();
        if(parent == null) return 0;
        return parent.cache().heightMin;
    }

    private float parentMaxWidth(){
        final UIComponent parent = component.parent();
        if(parent == null) return 0;
        return parent.cache().widthMax;
    }

    private float parentMaxHeight(){
        final UIComponent parent = component.parent();
        if(parent == null) return 0;
        return parent.cache().heightMax;
    }


}
