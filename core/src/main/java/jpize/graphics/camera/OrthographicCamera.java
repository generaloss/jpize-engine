package jpize.graphics.camera;

import jpize.Jpize;
import jpize.util.Resizable;
import jpize.math.vecmath.matrix.Matrix4f;

public class OrthographicCamera extends Camera2D implements Resizable{

    private float scale, rotation;
    private final Matrix4f projection, view, combined, scalingMatrix, translationMatrix, rotationMatrix;
    private boolean imaginaryX, imaginaryY;

    public OrthographicCamera(int width, int height){
        super(width, height);

        scale = 1F;

        scalingMatrix = new Matrix4f();
        translationMatrix = new Matrix4f();
        rotationMatrix = new Matrix4f();
        view = new Matrix4f();
        projection = new Matrix4f();
        combined = new Matrix4f();

        updateProjectionMatrix();
        updateViewMatrix();
    }

    public OrthographicCamera(){
        this(Jpize.getWidth(), Jpize.getHeight());
    }


    @Override
    public void update(){
        updateViewMatrix();
    }

    private void updateProjectionMatrix(){
        projection.identity().setOrthographic(0, 0, width, height);
    }

    private void updateViewMatrix(){
        translationMatrix.setTranslate(
            imaginaryX ? 0 : -position.x,
            imaginaryY ? 0 : -position.y
        );
        scalingMatrix.setScale(scale);
        rotationMatrix.setRotationZ(rotation);
        view.set(scalingMatrix.mul(translationMatrix).mul(rotationMatrix));
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

    public void setImaginaryOrigins(boolean x, boolean y){
        imaginaryX = x;
        imaginaryY = y;
    }

    public float getScale(){
        return scale;
    }

    public void scale(float scale){
        this.scale *= scale;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public float getRotation(){
        return rotation;
    }

    public void rotate(float deg){
        rotation += deg;
    }

    public void setRot(float deg){
        rotation = deg;
    }

    @Override
    public Matrix4f getProjection(){
        return projection;
    }

    @Override
    public Matrix4f getView(){
        return view;
    }

    @Override
    public Matrix4f getCombined(){
        return combined;
    }
    
}
