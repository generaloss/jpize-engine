package jpize.ui.palette;

import jpize.graphics.font.BitmapFont;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Button extends Rect{

    private final TextView textView;

    public Button(Constraint width, Constraint height, String text, BitmapFont font){
        super(width, height);

        this.textView = new TextView(text, font);
        textView.padding().set(Constr.zero);
        textView.color().set(0.1, 0.1, 0.1, 1);
        super.add(textView);
    }

    public Button(Constraint size, String text, BitmapFont font){
        this(size, size, text, font);
    }

    @Override
    public void render(){
        super.render();
    }

    public TextView textview(){
        return textView;
    }

}
