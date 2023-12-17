package jpize.ui.component;

public abstract class AbstractLayout extends UIComponent{

    public AbstractLayout(){
        input.setClickable(false);
    }

    public abstract float calcPosition(UIComponent component, boolean forY);

    public abstract float calcWrapContent(UIComponent component, boolean forY, boolean forSize);

    public void resize(){ }

}
