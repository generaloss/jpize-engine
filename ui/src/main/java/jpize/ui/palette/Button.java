package jpize.ui.palette;

import jpize.graphics.font.Font;
import jpize.ui.component.UIPressCallback;
import jpize.ui.component.UIReleaseCallback;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Button extends Rect{

    private final TextView textview;
    private UIPressCallback pressStyleCallback;
    private UIReleaseCallback releaseStyleCallback;

    public Button(Constraint width, Constraint height, String text, Font font, Constraint text_size){
        super(width, height);
        super.background().color().set(0.35, 0.1, 0.9);
        super.setCornerRadius(Constr.relh(0.15));
        super.setClickable(true);

        this.textview = new TextView(text, font, text_size);
        this.textview.padding().set(Constr.zero);
        this.textview.color().set(0.85);
        super.add(textview);

        setButtonPressStyle((view, btn) -> super.background().color().set(0.45, 0.2, 1));
        setButtonReleaseStyle((view, btn) -> super.background().color().set(0.35, 0.1, 0.9));
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


    public UIPressCallback buttonPressStyle(){
        return pressStyleCallback;
    }

    public UIReleaseCallback buttonReleaseStyle(){
        return releaseStyleCallback;
    }

    public void setButtonPressStyle(UIPressCallback pressStyleCallback){
        if(this.pressStyleCallback != null)
            super.removePressCallback(this.pressStyleCallback);

        this.pressStyleCallback = pressStyleCallback;
        if(this.pressStyleCallback != null)
            super.addPressCallback(this.pressStyleCallback);
    }

    public void setButtonReleaseStyle(UIReleaseCallback releaseStyleCallback){
        if(this.releaseStyleCallback != null)
            super.removeReleaseCallback(this.releaseStyleCallback);

        this.releaseStyleCallback = releaseStyleCallback;
        if(this.releaseStyleCallback != null)
            super.addReleaseCallback(this.releaseStyleCallback);
    }

}
