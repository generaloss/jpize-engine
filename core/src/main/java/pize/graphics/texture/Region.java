package pize.graphics.texture;

public class Region{

    protected float u1, v1, u2, v2;
    private float width, height;
    
    
    public Region(Region region){
        set(region);
    }
    
    public Region(float u1, float v1, float u2, float v2){
        set(u1, v1, u2, v2);
    }
    
    public Region(double u1, double v1, double u2, double v2){
        set(u1, v1, u2, v2);
    }
    
    public Region(Texture texture, double x, double y, double width, double height){
        set(texture, x, y, width, height);
    }

    public Region(){
        this(0, 0, 1, 1);
    }


    public void set(float u1, float v1, float u2, float v2){
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
        
        this.width = u2 - u1;
        this.height = v2 - v1;
    }
    
    public void set(Region region){
        set(region.u1, region.v1, region.u2, region.v2);
    }
    
    public void set(double u1, double v1, double u2, double v2){
        set((float) u1, (float) v1, (float) u2, (float) v2);
    }

    public void set(Texture texture, double x, double y, double width, double height){
        set(
            x / texture.getWidth(),
            y / texture.getHeight(),
            (x + width) / texture.getWidth(),
            (y + height) / texture.getHeight()
        );
    }


    public float u1(){
        return u1;
    }

    public float v1(){
        return v1;
    }

    public float u2(){
        return u2;
    }

    public float v2(){
        return v2;
    }

    public float getWidth(){
        return width;
    }
    
    public float getHeight(){
        return height;
    }
    
    
    public float aspect(){
        return width / height;
    }
    
    public float getWidthPx(Texture texture){
        return width * texture.getWidth();
    }
    
    public float getHeightPx(Texture texture){
        return height * texture.getHeight();
    }


    public static Region calcRegionInRegion(Region region, double u1, double v1, double u2, double v2){
        final float regionWidth = region.getWidth();
        final float regionHeight = region.getHeight();

        return new Region(
            region.u1 + u1 * regionWidth,
            region.v1 + v1 * regionHeight,
            region.u1 + u2 * regionWidth,
            region.v1 + v2 * regionHeight
        );
    }

    public static Region calcRegionInRegion(Region region, Region regionOfRegion){
        return calcRegionInRegion(region, regionOfRegion.u1, regionOfRegion.v1, regionOfRegion.u2, regionOfRegion.v2);
    }
    
    
    public Region copy(){
        return new Region(this);
    }


    @Override
    public String toString(){
        return "[" + u1 + ", " + v1 + "]; [" + u2 + ", " + v2 + "]";
    }

}
