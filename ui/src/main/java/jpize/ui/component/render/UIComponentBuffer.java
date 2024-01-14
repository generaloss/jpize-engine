package jpize.ui.component.render;

import jpize.Jpize;
import jpize.ui.component.UIComponent;
import jpize.app.Resizable;

import java.util.Arrays;

public class UIComponentBuffer implements Resizable{

    private UIComponent[] buffer;
    private int width;
    private int height;

    public UIComponentBuffer(int width, int height){
        resize(width, height);
    }

    public UIComponentBuffer(){
        this(Jpize.getWidth(), Jpize.getHeight());
    }

    @Override
    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        this.buffer = new UIComponent[width * height];
    }


    private int getIndex(int x, int y){
        return x * height + y;
    }

    private boolean outOfBounds(int x, int y){
        return !(x > -1 && y > -1 && x < width && y < height);
    }


    public UIComponent get(int x, int y){
        if(outOfBounds(x, y))
            return null;
        final int index = getIndex(x, y);
        return buffer[index];
    }

    public void set(int x, int y, UIComponent component){
        if(outOfBounds(x, y))
            return;
        final int index = getIndex(x, y);
        buffer[index] = component;
    }

    public void fill(int x1, int y1, int width, int height, UIComponent component){
        final int x2 = x1 + width;
        final int y2 = y1 + height;

        for(int x = x1; x < x2; x++)
            for(int y = y1; y < y2; y++)
                set(x, y, component);
    }

    public void fill(float x1, float y1, float width, float height, UIComponent component){
        fill((int) x1, (int) y1, (int) width, (int) height, component);
    }


    public void clear(){
        Arrays.fill(buffer, null);
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}
