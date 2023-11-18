package jpize.gui.components;

public class RegionMesh{

    private final float[] mesh;

    public RegionMesh(float beginX, float beginY, float separatorX1, float separatorY1, float separatorX2, float separatorY2, float endX, float endY){
        mesh = new float[]{
            beginX,
            separatorX1,
            separatorX2,
            endX,

            beginY,
            separatorY1,
            separatorY2,
            endY
        };
    }

    public float[] getMesh(){
        return mesh;
    }

}
