package jpize.graphics.camera;

import jpize.app.Resizable;
import jpize.graphics.util.Sizable;
import jpize.util.math.matrix.Matrix4f;

public abstract class Camera extends Sizable implements Resizable{
    
    public Camera(int width, int height){
        super(width, height);
    }
    
    public abstract void update();

    public abstract Matrix4f getView();
    public abstract Matrix4f getProjection();
    public abstract Matrix4f getCombined();

}
