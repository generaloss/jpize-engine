package jpize.graphics.camera;

import jpize.Jpize;
import jpize.util.Resizable;
import jpize.math.vecmath.matrix.Matrix4f;

public class OrthographicCamera extends Camera2D implements Resizable{

    private float scale, rotation;
    private final Matrix4f projection, view, scalingMatrix, translationMatrix, rotationMatrix;
    private boolean dirtyProjection, imaginaryX, imaginaryY;

    public OrthographicCamera(int width, int height){
        super(width, height);

        scale = 1;
        view = new Matrix4f();

        projection = new Matrix4f().toOrthographic(0, 0, width, height);

        scalingMatrix = new Matrix4f();
        translationMatrix = new Matrix4f();
        rotationMatrix = new Matrix4f();
    }

    public OrthographicCamera(){
        this(Jpize.getWidth(), Jpize.getHeight());
    }


    @Override
    public void update(){
        if(dirtyProjection){
            projection.identity().toOrthographic(0, 0, width, height);
            dirtyProjection = false;
        }

        translationMatrix.toTranslated(
            imaginaryX ? 0 : -position.x,
            imaginaryY ? 0 : -position.y,
            0
        );
        scalingMatrix.toScaled(scale, scale, 1);
        rotationMatrix.toRotatedZ(rotation);
        view.set(scalingMatrix.mul(translationMatrix).mul(rotationMatrix));
    }

    @Override
    public void resize(int width, int height){
        if(matchSize(width, height))
            return;
    
        setSize(width, height);
        dirtyProjection = true;
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
    
}
