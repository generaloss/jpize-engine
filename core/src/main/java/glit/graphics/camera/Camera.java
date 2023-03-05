package glit.graphics.camera;

import glit.context.Resizable;
import glit.math.vecmath.matrix.Matrix4f;

public interface Camera extends Resizable{

    void update();

    Matrix4f getView();
    Matrix4f getProjection();

    float getWidth();
    float getHeight();

}
