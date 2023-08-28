package pize.graphics.util.batch.scissor;

import pize.gl.Gl;

import java.awt.*;

public class ScissorNode{
    
    private final int index, scissorOfIndex;
    private final Rectangle rectangle;
    
    public ScissorNode(int index, Rectangle rectangle, int scissorOfIndex){
        this.index = index;
        this.rectangle = rectangle;
        this.scissorOfIndex = scissorOfIndex;
    }
    
    public ScissorNode(int index, int x, int y, int width, int height, int scissorOfIndex){
        this(index, new Rectangle(x, y, width, height), scissorOfIndex);
    }
    
    
    public void activate(){
        Gl.scissor(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
    
    public int getIndex(){
        return index;
    }
    
    public Rectangle getRectangle(){
        return rectangle;
    }
    
    public int getScissorOfIndex(){
        return scissorOfIndex;
    }
    
    
    public int getX(){
        return rectangle.x;
    }
    
    public int getY(){
        return rectangle.y;
    }
    
    public int getX2(){
        return rectangle.x + rectangle.width;
    }
    
    public int getY2(){
        return rectangle.y + rectangle.height;
    }
    
    public int getWidth(){
        return rectangle.width;
    }
    
    public int getHeight(){
        return rectangle.height;
    }
    
}
