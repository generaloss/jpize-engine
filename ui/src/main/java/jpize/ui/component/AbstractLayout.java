package jpize.ui.component;

public abstract class AbstractLayout extends UIComponent{

    public AbstractLayout(){
        super.background().color().setA(0);
    }

    public float getPositionForComponent(UIComponent component, boolean forY){
        return forY ? component.cache.y : component.cache.x;
    }

    public float getSizeForWrapContent(UIComponent component, boolean forY){
        return forY ? cache.containerHeight : cache.containerWidth;
    }

    public float getRemainingAreaForComponent(UIComponent component, boolean forY){
        return forY ? cache.containerHeight : cache.containerWidth;
    }

}
