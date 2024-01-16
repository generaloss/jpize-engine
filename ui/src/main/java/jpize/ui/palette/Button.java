package jpize.ui.palette;

import jpize.graphics.font.BitmapFont;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Button extends Rect{

    private final TextView textview;

    public Button(Constraint width, Constraint height, String text, BitmapFont font, Constraint text_size){
        super(width, height);

        this.textview = new TextView(text, font, text_size);
        this.textview.setID("text");
        this.textview.padding().set(Constr.zero);
        this.textview.color().set(0.1, 0.1, 0.1, 1);
        super.add(textview);
    }

    public Button(Constraint width, Constraint height, String text, BitmapFont font){
        this(width, height, text, font, Constr.match_parent);
    }

    public Button(Constraint size, String text, BitmapFont font, Constraint text_size){
        this(size, size, text, font, text_size);
    }

    public Button(Constraint size, String text, BitmapFont font){
        this(size, text, font, Constr.match_parent);
    }

    @Override
    public void render(){
        super.render();
    }

    public TextView textview(){
        return textview;
    }

}
