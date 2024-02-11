package jpize.ui.component;

import jpize.util.color.Color;
import jpize.ui.constraint.Constraint;

public class UIStyle{

    private final UIBackground background;
    private final Color border_color;
    private Constraint border_size;
    private Constraint corner_radius;

    public UIStyle(){
        this.background = new UIBackground();
        this.border_color = new Color();
    }


    public UIBackground background(){
        return background;
    }


    public Constraint getCornerRadius(){
        return corner_radius;
    }

    public void setCornerRadius(Constraint corner_radius){
        this.corner_radius = corner_radius;
    }


    public Constraint getBorderSize(){
        return border_size;
    }

    public void setBorderSize(Constraint border_size){
        this.border_size = border_size;
    }


    public Color borderColor(){
        return border_color;
    }

}
