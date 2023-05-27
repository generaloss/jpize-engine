package pize.gui;

import pize.Pize;
import pize.gui.constraint.*;

import java.util.*;

import static pize.gui.Align.*;
import static pize.gui.LayoutType.CONSTRAINT;

public abstract class UIComponent<C> implements Cloneable{

    private Constraint constraintX, constraintY, constraintWidth, constraintHeight;
    protected float x, y, width, height;
    private boolean show, hover, preGrab, grab;
    private Align align;

    private LayoutType layoutType;
    private Align alignComponents;

    private float shiftX, shiftY;
    private UIComponent<C> parent;
    private final Map<String, UIComponent<C>> childList;
    private List<UIComponent<C>> sortedChildList;
    private int indexAsChild;

    public UIComponent(Constraint constraintX, Constraint constraintY, Constraint constraintWidth, Constraint constraintHeight){
        this.constraintX = constraintX;
        this.constraintY = constraintY;
        this.constraintWidth = constraintWidth;
        this.constraintHeight = constraintHeight;

        childList = new HashMap<>();
        sortedChildList = new ArrayList<>();

        setLayoutType(CONSTRAINT);
        alignItems(LEFT_DOWN);
        show(true);
    }

    public UIComponent(){
        this(Constraint.zero(), Constraint.zero(), Constraint.matchParent(), Constraint.matchParent());
    }

    public UIComponent(UIComponent<C> component){
        this(component.constraintX, component.constraintY, component.constraintWidth, component.constraintHeight);
    }


    public void render(C canvas){
        correctConstraints();
        calculateSize();
        correctSize();
        calculatePos();
        correctPos();

        // render element
        if(show)
            render(canvas, x, y, width, height);
        else
            return;
        
        // touch
        hover = checkIsHover();
        
        grab = preGrab;
        
        if(this.isTouchDown())
            preGrab = true;
        else if(Pize.isTouchReleased())
            preGrab = false;
        
        
        // render children
        if(sortedChildList.size() == 0)
            return;

        float shiftX = 0;
        float shiftY = 0;

        for(UIComponent<C> child: sortedChildList){
            child.shiftX = shiftX;
            child.shiftY = shiftY;

            child.render(canvas);

            switch(layoutType){
                case HORIZONTAL ->
                    shiftX +=
                        alignSignX(alignComponents) * (
                            sortedChildList.size() != 1 && child == sortedChildList.get(0) && isCenteredX()
                            ? (child.width  + sortedChildList.get(1).width ) / 2
                            : child.width
                        ) + child.getConstraintX();

                case VERTICAL   ->
                    shiftY +=
                        alignSignY(alignComponents) * (
                            sortedChildList.size() != 1 && child == sortedChildList.get(0) && isCenteredY()
                            ? (child.height + sortedChildList.get(1).height) / 2
                            : child.height
                        ) + child.getConstraintY();
            }
        }
    }
    
    
    protected void correctConstraints(){ }
    
    protected void correctSize(){ }
    
    protected void correctPos(){ }

    protected abstract void render(C canvas, float x, float y, float width, float height);
    
    
    public boolean isTouchDown(){
        return Pize.isTouchDown() && isHover();
    }
    
    public boolean isTouched(){
        return Pize.isTouched() && isHover();
    }
    
    public boolean isTouchReleased(){
        return Pize.isTouchReleased() && grab;
    }
    
    
    public boolean isShow(){
        return show;
    }

    public void show(boolean show){
        this.show = show;
    }


    public Constraint getXConstraint(){
        return constraintX;
    }

    public Constraint getYConstraint(){
        return constraintY;
    }

    public Constraint getWidthConstraint(){
        return constraintWidth;
    }

    public Constraint getHeightConstraint(){
        return constraintHeight;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }


    public void setX(Constraint constraint){
        constraintX = constraint;
    }

    public void setY(Constraint constraint){
        constraintY = constraint;
    }

    public void setPosition(Constraint x, Constraint y){
        constraintX = x;
        constraintY = y;
    }

    public void setPosition(Constraint xy){
        constraintX = xy;
        constraintY = xy;
    }


    public void setWidth(Constraint constraint){
        constraintWidth = constraint;
        calculateSize();
    }

    public void setHeight(Constraint constraint){
        constraintHeight = constraint;
        calculateSize();
    }

    public void setSize(Constraint width, Constraint height){
        constraintWidth = width;
        constraintHeight = height;
        calculateSize();
    }

    public void setSize(Constraint widthHeight){
        constraintWidth = widthHeight;
        constraintHeight = widthHeight;
        calculateSize();
    }

    public void setSize(UIComponent<?> component){
        constraintWidth = component.constraintWidth;
        constraintHeight = component.constraintHeight;
        calculateSize();
    }

    public float aspect(){
        return width / height;
    }


    public void put(String id, UIComponent<C> child){
        child.indexAsChild = childList.size();
        child.setParent(this);

        childList.put(id, child);

        sortedChildList = childList.values().stream()
            .sorted(Comparator.comparingInt(component -> component.indexAsChild))
            .toList();
    }

    protected void setParent(UIComponent<C> component){
        parent = component;
    }

    protected void setAsParentFor(UIComponent<C>... components){
        for(UIComponent<C> component: components)
            component.setParent(this);
    }

    public <T extends UIComponent<C>> T get(String id){
        return (T) childList.get(id);
    }

    public <T extends UIComponent<C>> T getByOrder(int index){
        return (T) sortedChildList.get(index);
    }

    public void setLayoutType(LayoutType layoutType){
        this.layoutType = layoutType;
    }

    public void alignItems(Align alignComponents){
        this.alignComponents = alignComponents;
    }

    public void alignSelf(Align align){
        this.align = align;
    }
    
    
    public boolean isGrab(){
        return grab;
    }
    
    public boolean isHover(){
        return hover;
    }
    
    private boolean checkIsHover(){
        float mouseX = Pize.getX();
        float mouseY = Pize.getY();
        
        if(!Pize.window().isFocused() || mouseX < 0 || mouseX >= Pize.getWidth() || mouseY < 0 || mouseY >= Pize.getHeight())
            return false;
        
        boolean hover = !( mouseX < x || mouseY < y || mouseX > x + width || mouseY > y + height);
        hover &= isChildHover(childList.values());
        
        if(parent != null)
            for(int i = parent.sortedChildList.size() - 1; i >= 0; i--){
                UIComponent<C> child = parent.sortedChildList.get(i);
                if(child == this)
                    break;
                
                if(!child.show)
                    continue;
                
                hover &= (mouseX < child.x || mouseY < child.y || mouseX > child.x + child.width || mouseY > child.y + child.height);
            }
        
        return hover;
    }
    
    private boolean isChildHover(Collection<UIComponent<C>> childList){
        float mouseX = Pize.getX();
        float mouseY = Pize.getY();
        
        boolean hover = true;
        
        for(UIComponent<C> child: childList){
            hover &= !child.show || mouseX < child.x || mouseY < child.y || mouseX > child.x + child.width || mouseY > child.y + child.height;
            if(hover)
                hover = isChildHover(child.childList.values());
        }
        
        return hover;
    }

    
    private void calculateSize(){
        if(constraintWidth.getType() == ConstraintType.ASPECT)
            height = calcConstraintY(constraintHeight);
        
        width = calcConstraintX(constraintWidth);
        
        if(constraintWidth.getType() != ConstraintType.ASPECT)
            height = calcConstraintY(constraintHeight);
    }
    
    private void calculatePos(){
        x = getParentX() + getConstraintX();
        y = getParentY() + getConstraintY();
        if(align == null){
            x += shiftX + getParentAlignOffsetX();
            y += shiftY + getParentAlignOffsetY();
        }else{
            x += getAlignOffsetX(align);
            y += getAlignOffsetY(align);
        }
    }
    

    protected float calcConstraintX(Constraint x){
        return switch(x.getType()){
            case PIXEL -> x.getValue();
            case ASPECT -> x.getValue() * height;
            case RELATIVE -> getRelativeWidth((RelativeConstraint) x);
        };
    }

    protected float calcConstraintY(Constraint y){
        return switch(y.getType()){
            case PIXEL -> y.getValue();
            case ASPECT -> y.getValue() * width;
            case RELATIVE -> getRelativeHeight((RelativeConstraint) y);
        };
    }

    private float getConstraintX(){
        int sign =
            align == null ?
                parent != null ?
                    alignSignX(parent.alignComponents)
                    : 1
                : alignSignX(align);

        return calcConstraintX(constraintX) * sign;
    }

    private float getConstraintY(){
        int sign =
            align == null ?
                parent != null ?
                    parent.alignSignY(parent.alignComponents)
                    : 1
                : alignSignY(align);

        return calcConstraintY(constraintY) * sign;
    }

    private float getRelativeWidth(RelativeConstraint constraint){
        return constraint.getValue() * switch(constraint.getRelativeTo()){
            case AUTO, WIDTH -> getParentWidth();
            case HEIGHT -> getParentHeight();
        };
    }

    private float getRelativeHeight(RelativeConstraint constraint){
        return constraint.getValue() * switch(constraint.getRelativeTo()){
            case AUTO, HEIGHT -> getParentHeight();
            case WIDTH -> getParentWidth();
        };
    }

    private float getParentX(){
        if(parent == null)
            return 0;
        return parent.getX();
    }

    private float getParentY(){
        if(parent == null)
            return 0;
        return parent.getY();
    }

    private float getParentWidth(){
        if(parent == null)
            return Pize.getWidth();
        return parent.getWidth();
    }

    private float getParentHeight(){
        if(parent == null)
            return Pize.getHeight();
        return parent.getHeight();
    }

    private float getParentAlignOffsetX(){
        if(parent != null)
            return getAlignOffsetX(parent.alignComponents);
        return 0;
    }

    private float getParentAlignOffsetY(){
        if(parent != null)
            return getAlignOffsetY(parent.alignComponents);
        return 0;
    }

    private float getAlignOffsetX(Align align){
        if(align == CENTER || align == DOWN || align == UP)
            return (getParentWidth() - width) / 2;
        else if(align == RIGHT || align == RIGHT_DOWN || align == RIGHT_UP)
            return getParentWidth() - width;
        else
            return 0;
    }

    private float getAlignOffsetY(Align align){
        if(align == CENTER || align == LEFT || align == RIGHT)
            return (getParentHeight() - height) / 2;
        else if(align == UP || align == LEFT_UP || align == RIGHT_UP)
            return getParentHeight() - height;
        else
            return 0;
    }

    private int alignSignX(Align align){
        return align == RIGHT
            || align == RIGHT_DOWN
            || align == RIGHT_UP
            ? -1 : 1;
    }

    private int alignSignY(Align align){
        return align == UP
            || align == LEFT_UP
            || align == RIGHT_UP
            ? -1 : 1;
    }

    private boolean isCenteredX(){
        return switch(alignComponents){
            case UP, DOWN, CENTER -> true;
            default -> false;
        };
    }

    private boolean isCenteredY(){
        return switch(alignComponents){
            case LEFT, RIGHT, CENTER -> true;
            default -> false;
        };
    }

    
    @Override
    public UIComponent<C> clone(){
        try{
            return (UIComponent) super.clone();
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

}
