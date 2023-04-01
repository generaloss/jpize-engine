package glit.tests.particle;

public abstract class Noise{
    
    protected float scale;
    
    public Noise(){
        scale = 1;
    }
    
    
    public void setScale(float scale){
        this.scale = scale;
    }
    
    public float getScale(){
        return scale;
    }
    
    
    abstract void setSeed(int seed);
    
    abstract float get(float x, float y);
    
    abstract float get(float x, float y, float z);
    
}
