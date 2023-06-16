package pize.graphics.camera;

import pize.app.Resizable;
import pize.graphics.texture.Sizable;
import pize.math.vecmath.matrix.Matrix4f;

public abstract class Camera extends Sizable implements Resizable{
    
    public Camera(int width, int height){
        super(width, height);
    }
    
    public abstract void update();

    public abstract Matrix4f getView();
    public abstract Matrix4f getProjection();

}
