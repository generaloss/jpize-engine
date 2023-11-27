package jpize.ui.component;

public abstract class LayoutComponent extends UIComponent{

    public abstract float calcPosition(UIComponent component, boolean forY);

    public abstract float calcWrapContent(UIComponent component, boolean forY, boolean forSize);

    public void resize(){ }

}
