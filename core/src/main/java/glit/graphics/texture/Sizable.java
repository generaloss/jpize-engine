package glit.graphics.texture;

public abstract class Sizable{
    
    protected int width;
    protected int height;
    
    public Sizable(int width, int height){
        setSize(width, height);
    }
    
    public Sizable(Sizable sizable){
        setSize(sizable);
    }
    
    
    protected void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    protected void setSize(Sizable sizable){
        this.width = sizable.width;
        this.height = sizable.height;
    }
    
    
    public float aspect(){
        return (float) width / height;
    }
    
    public boolean match(Sizable sizable){
        return sizable.width == width && sizable.height == height;
    }
    
    public boolean match(int width, int height){
        return this.width == width && this.height == height;
    }
    
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
}
