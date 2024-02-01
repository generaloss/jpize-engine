package jpize.ui.palette;

import jpize.graphics.font.Font;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Button extends Rect{

    private final TextView textview;

    public Button(Constraint width, Constraint height, String text, Font font, Constraint text_size){
        super(width, height);
        super.style.background().color().set(0.38, 0, 0.9);
        super.style.setCornerRadius(Constr.relh(0.15));
        super.input.setClickable(true);

        this.textview = new TextView(text, font, text_size);
        this.textview.padding().set(Constr.zero);
        this.textview.color().set(0.85);
        super.add(textview);
    }

    public Button(Constraint width, Constraint height, String text, Font font){
        this(width, height, text, font, Constr.relh(0.8));
    }

    public Button(Constraint size, String text, Font font, Constraint text_size){
        this(size, size, text, font, text_size);
    }

    public Button(Constraint size, String text, Font font){
        this(size, text, font, Constr.match_parent);
    }

    public TextView textview(){
        return textview;
    }

}
