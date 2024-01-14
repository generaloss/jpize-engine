package jpize.graphics.camera;

import jpize.Jpize;
import jpize.app.Resizable;
import jpize.util.math.util.Frustum;
import jpize.util.math.vecmath.matrix.Matrix4f;

public class PerspectiveCamera extends Camera3D implements Resizable{

    private float fovY, near, far;
    private final Matrix4f projection, view, combined, imaginaryView, rollMatrix;
    private final Frustum frustum;
    private boolean imaginaryX, imaginaryY, imaginaryZ, hasImaginaryAxis;

    public PerspectiveCamera(double near, double far, double fovY){
        this(Jpize.getWidth(), Jpize.getHeight(), near, far, fovY);
    }

    public PerspectiveCamera(int width, int height, double near, double far, double fovY){
        super(width, height);
        
        this.near = (float) near;
        this.far = (float) far;
        this.fovY = (float) fovY;

        this.imaginaryView = new Matrix4f();
        this.rollMatrix = new Matrix4f();
        this.frustum = new Frustum();
        this.view = new Matrix4f();
        this.projection = new Matrix4f();
        this.combined = new Matrix4f();

        updateProjectionMatrix();
        updateViewMatrix();
    }

    @Override
    public void update(){
        updateViewMatrix();
    }

    private void updateProjectionMatrix(){
        projection.setPerspective(width, height, near, far, fovY);
    }

    private void updateViewMatrix(){
        // Roll camera rotation
        rollMatrix.setRotationZ(rotation.roll);

        // View matrix
        imaginaryView.setLookAt(position, rotation.getDir());
        imaginaryView.set(rollMatrix.copy().mul(imaginaryView));

        // View matrix
        if(hasImaginaryAxis){
            view.setLookAt(
                imaginaryX ? 0 : position.x,
                imaginaryY ? 0 : position.y,
                imaginaryZ ? 0 : position.z,
                rotation.getDir()
            );
            view.set(rollMatrix.mul(view));
        }else
            view.set(imaginaryView);

        // Frustum
        frustum.setFrustum(imaginaryView, projection);
        // Update combined
        updateCombinedMatrix();
    }

    private void updateCombinedMatrix(){
        combined.set(projection).mul(view);
    }

    @Override
    public void resize(int width, int height){
        if(matchSize(width, height))
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
        return fovY;
    }

    public void setFov(float fieldOfView){
        if(this.fovY == fieldOfView)
            return;

        this.fovY = fieldOfView;
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

    @Override
    public Matrix4f getCombined(){
        return combined;
    }
    
}
