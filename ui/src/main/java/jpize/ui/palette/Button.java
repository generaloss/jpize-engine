package jpize.ui.palette;

import jpize.graphics.font.BitmapFont;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Button extends Rect{

    private final TextView textview;

    public Button(Constraint width, Constraint height, String text, BitmapFont font){
        super(width, height);

        this.textview = new TextView(text, font);
        textview.padding().set(Constr.zero);
        textview.color().set(0.1, 0.1, 0.1, 1);
        super.add(textview);
    }

    public Button(Constraint size, String text, BitmapFont font){
        this(size, size, text, font);
    }

    @Override
    public void render(){
        super.render();
    }

    public TextView textview(){
        return textview;
    }

}
