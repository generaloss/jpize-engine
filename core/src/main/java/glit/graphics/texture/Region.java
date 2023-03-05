package glit.graphics.texture;

public class Region{

    protected double u1, v1, u2, v2;


    public Region(double u1, double v1, double u2, double v2){
        set(u1, v1, u2, v2);
    }

    public Region(){
        this(0, 0, 1, 1);
    }


    public void set(double u1, double v1, double u2, double v2){
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
    }

    public void set(Region region){
        u1 = region.u1;
        v1 = region.v1;
        u2 = region.u2;
        v2 = region.v2;
    }


    public float aspect(){
        return (float) ((u2 - u1) / (v2 - v1));
    }


    public float getWidthOn(Texture texture){
        return (float) (u2 - u1) * texture.getWidth();
    }

    public float getHeightOn(Texture texture){
        return (float) (v2 - v1) * texture.getHeight();
    }


    public double u1(){
        return u1;
    }

    public double v1(){
        return v1;
    }


    public double u2(){
        return u2;
    }

    public double v2(){
        return v2;
    }


    public float u1f(){
        return (float) u1;
    }

    public float v1f(){
        return (float) v1;
    }


    public float u2f(){
        return (float) u2;
    }

    public float v2f(){
        return (float) v2;
    }


    public static Region calcRegionInRegion(Region region, double u1, double v1, double u2, double v2){
        double regionWidth = region.u2() - region.u1();
        double regionHeight = region.v2() - region.v1();

        return new Region(
            region.u1() + u1 * regionWidth,
            region.v1() + v1 * regionHeight,
            region.u1() + u2 * regionWidth,
            region.v1() + v2 * regionHeight
        );
    }

    public static Region calcRegionInRegion(Region region, Region regionRegion){
        return calcRegionInRegion(region, regionRegion.u1, regionRegion.v1, regionRegion.u2, regionRegion.v2);
    }

}
