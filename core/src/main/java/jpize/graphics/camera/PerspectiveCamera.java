package jpize.graphics.camera;

import jpize.Jpize;
import jpize.util.Resizable;
import jpize.math.util.Frustum;
import jpize.math.vecmath.matrix.Matrix4f;

public class PerspectiveCamera extends Camera3D implements Resizable{

    private float fieldOfView, near, far;
    private final Matrix4f projection, view, imaginaryView, rollMatrix;
    private final Frustum frustum;
    private boolean imaginaryX, imaginaryY, imaginaryZ, hasImaginaryAxis;

    public PerspectiveCamera(double near, double far, double fieldOfView){
        this(Jpize.getWidth(), Jpize.getHeight(), near, far, fieldOfView);
    }

    public PerspectiveCamera(int width, int height, double near, double far, double fieldOfView){
        super(width, height);
        
        this.near = (float) near;
        this.far = (float) far;
        this.fieldOfView = (float) fieldOfView;
        this.view = new Matrix4f();
        this.projection = new Matrix4f();
        this.imaginaryView = new Matrix4f();
        this.rollMatrix = new Matrix4f();
        this.frustum = new Frustum();

        updateProjectionMatrix();
        calculateView();
    }

    @Override
    public void update(){
        calculateView();
    }

    private void updateProjectionMatrix(){
        this.projection.toPerspective(width, height, near, far, fieldOfView);
    }

    private void calculateView(){
        // Roll camera rotation
        rollMatrix.toRotatedZ(rotation.roll);

        // View matrix
        imaginaryView.toLookAt(position, rotation.getDirection());
        imaginaryView.set(rollMatrix.copy().mul(imaginaryView));

        // View matrix
        if(hasImaginaryAxis){
            view.toLookAt(
                imaginaryX ? 0 : position.x,
                imaginaryY ? 0 : position.y,
                imaginaryZ ? 0 : position.z,
                rotation.getDirection()
            );
            view.set(rollMatrix.mul(view));
        }else
            view.set(imaginaryView);

        // Frustum
        frustum.setFrustum(imaginaryView, projection);
    }

    @Override
    public void resize(int width, int height){
        if(match(width, height))
            return;
    
        setSize(width, height);
        updateProjectionMatrix();
    }

    public void setImaginaryOrigins(boolean x, boolean y, boolean z){
        this.imaginaryX = x;
        this.imaginaryY = y;
        this.imaginaryZ = z;
        this.hasImaginaryAxis = imaginaryX || imaginaryY || imaginaryZ;
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
        updateProjectionMatrix();
    }

    public float getNear(){
        return near;
    }

    public void setNear(float near){
        if(this.near == near)
            return;

        this.near = near;
        updateProjectionMatrix();
    }

    public float getFar(){
        return far;
    }

    public void setFar(float far){
        if(this.far == far)
            return;

        this.far = far;
        updateProjectionMatrix();
    }

    @Override
    public Matrix4f getView(){
        return view;
    }
    
    public Matrix4f getImaginaryView(){
        return imaginaryView;
    }

    @Override
    public Matrix4f getProjection(){
        return projection;
    }
    
}
