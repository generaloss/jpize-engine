package glit.graphics.camera;

import glit.Pize;
import glit.context.Resizable;
import glit.math.Maths;
import glit.math.vecmath.matrix.Matrix4f;

public class CenteredOrthographicCamera extends Camera2D implements Resizable{
    
    private float scale, rotation;
    private final Matrix4f projection, view, scalingMatrix, translationMatrix, rotationMatrix;
    private boolean dirtyProjection, imaginaryX, imaginaryY;

    public CenteredOrthographicCamera(int width, int height){
        super(width, height);

        scale = 1;
        view = new Matrix4f();

        projection = new Matrix4f().toOrthographic(-Maths.round(this.width / 2F), -Maths.round(this.height / 2F), this.width, this.height);

        scalingMatrix = new Matrix4f();
        translationMatrix = new Matrix4f();
        rotationMatrix = new Matrix4f();
    }

    public CenteredOrthographicCamera(){
        this(Pize.getWidth(), Pize.getHeight());
    }


    @Override
    public void update(){
        if(dirtyProjection){
            projection.identity().toOrthographic(-Maths.round(this.width / 2F), -Maths.round(this.height / 2F), this.width, this.height);
            dirtyProjection = false;
        }

        scalingMatrix.toScaled(scale, scale, 1);
        translationMatrix.toTranslated(imaginaryX ? 0 : -position.x, imaginaryY ? 0 : -position.y, 0);
        rotationMatrix.toRotatedZ(rotation);

        view.set(scalingMatrix.mul(translationMatrix).mul(rotationMatrix));
    }

    @Override
    public void resize(int width, int height){
        if(super.match(width, height))
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

    public float getRot(){
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
