package pize.tests.voxelgame.client.entity.model;

import pize.math.util.EulerAngle;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.control.GameCamera;

public class Pose{
    
    private final Vec3f position;
    private final EulerAngle rotation;
    
    private final Matrix4f translateMatrix, rotationMatrix, modelMatrix;
    
    public Pose(){
        position = new Vec3f();
        rotation = new EulerAngle();
        
        translateMatrix = new Matrix4f();
        rotationMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }
    
    public void updateMatrices(GameCamera camera, Pose initial){
        translateMatrix.toTranslated(position);
        rotationMatrix.set(rotation.toMatrix());
        
        modelMatrix
            .set(new Matrix4f().toTranslated(-camera.getX(), 0, -camera.getZ()))
            .mul(initial.translateMatrix).mul(initial.rotationMatrix)
            .mul(translateMatrix).mul(rotationMatrix);
    }
    
    public void updateMatrices(GameCamera camera, Pose initial, Pose parent){
        translateMatrix.toTranslated(position);
        rotationMatrix.set(rotation.toMatrix());
        
        modelMatrix
            .set(new Matrix4f().toTranslated(-camera.getX(), 0, -camera.getZ()))
                .mul(parent.translateMatrix).mul(parent.rotationMatrix)
            .mul(initial.translateMatrix).mul(initial.rotationMatrix)
            .mul(translateMatrix).mul(rotationMatrix);
    }
    
    
    public void set(Vec3f position, EulerAngle rotation){
        this.position.set(position);
        this.rotation.set(rotation);
    }
    
    public void set(Pose pose){
        set(pose.position, pose.rotation);
    }
    
    
    public Matrix4f getModelMatrix(){
        return modelMatrix;
    }
    
    public Vec3f getPosition(){
        return position;
    }
    
    public EulerAngle getRotation(){
        return rotation;
    }
    
}
