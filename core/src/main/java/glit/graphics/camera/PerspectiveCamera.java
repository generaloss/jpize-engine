package glit.graphics.camera;

import glit.Glit;
import glit.math.util.Frustum;
import glit.math.vecmath.matrix.Matrix4f;

public class PerspectiveCamera extends Camera3D{

    private int width, height;
    private float fieldOfView, near, far;

    private final Frustum frustum;
    private final Matrix4f projection, view, imaginaryView;
    private boolean dirtyProjection, imaginaryX, imaginaryY, imaginaryZ;


    public PerspectiveCamera(double near, double far, double fieldOfView){
        this(Glit.getWidth(), Glit.getHeight(), near, far, fieldOfView);
    }

    public PerspectiveCamera(int width, int height, double near, double far, double fieldOfView){
        this.width = width;
        this.height = height;
        this.near = (float) near;
        this.far = (float) far;
        this.fieldOfView = (float) fieldOfView;

        view = new Matrix4f().toLookAt(position, rotation.direction());
        projection = new Matrix4f().toPerspective(width, height, this.near, this.far, this.fieldOfView);
        imaginaryView = new Matrix4f();

        frustum = new Frustum(view, projection);
    }


    @Override
    public void update(){
        if(dirtyProjection){
            projection.toPerspective(width, height, near, far, fieldOfView);
            dirtyProjection = false;
        }

        view.toLookAt(
            imaginaryX ? 0 : position.x,
            imaginaryY ? 0 : position.y,
            imaginaryZ ? 0 : position.z,
            rotation.direction()
        );

        frustum.setFrustum(
            !(imaginaryX || imaginaryY || imaginaryZ) ? view : imaginaryView.toLookAt(position, rotation.direction()),
            projection
        );
    }

    @Override
    public void resize(int width, int height){
        if(this.width != width || this.height != height){
            this.width = width;
            this.height = height;

            dirtyProjection = true;
        }
    }

    public void setImaginaryOrigins(boolean x, boolean y, boolean z){
        imaginaryX = x;
        imaginaryY = y;
        imaginaryZ = z;
    }

    public Frustum getFrustum(){
        return frustum;
    }

    public float getFov(){
        return fieldOfView;
    }

    public void setFov(float fieldOfView){
        if(this.fieldOfView == fieldOfView)
            return;

        this.fieldOfView = fieldOfView;
        dirtyProjection = true;
    }

    public float getNear(){
        return near;
    }

    public void setNear(float near){
        if(this.near == near)
            return;

        this.near = near;
        dirtyProjection = true;
    }

    public float getFar(){
        return far;
    }

    public void setFar(float far){
        if(this.far == far)
            return;

        this.far = far;
        dirtyProjection = true;
    }

    @Override
    public Matrix4f getView(){
        return view;
    }

    @Override
    public Matrix4f getProjection(){
        return projection;
    }

    @Override
    public float getWidth(){
        return width;
    }

    @Override
    public float getHeight(){
        return height;
    }

}
