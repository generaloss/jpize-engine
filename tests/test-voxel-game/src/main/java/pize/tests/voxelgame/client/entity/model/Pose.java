package pize.tests.voxelgame.client.entity.model;

import pize.math.util.EulerAngles;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.control.camera.GameCamera;

public class Pose{
    
    private final Vec3f position, scale;
    private final EulerAngles rotation;
    
    private final Matrix4f translateMatrix, scaleMatrix, poseModelMatrix, modelMatrix;
    
    public Pose(){
        position = new Vec3f();
        scale = new Vec3f(1, 1, 1);
        rotation = new EulerAngles();
        
        translateMatrix = new Matrix4f();
        scaleMatrix = new Matrix4f();
        poseModelMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }
    
    public void updateMatrices(GameCamera camera, Pose initial){
        translateMatrix.toTranslated(position);
        scaleMatrix.toScaled(scale);
        
        poseModelMatrix
            .identity()
        
            .mul(initial.poseModelMatrix)
            .mul(translateMatrix).mul(scaleMatrix).mul(rotation.toMatrix())
        ;
        
        modelMatrix
            .set(new Matrix4f().toTranslated(-camera.getX(), 0, -camera.getZ()))
            .mul(poseModelMatrix);
    }
    
    public void updateMatrices(GameCamera camera, Pose initial, Pose parent){
        translateMatrix.toTranslated(position);
        scaleMatrix.toScaled(scale);
        
        poseModelMatrix
            .identity()
            
            .mul(parent.poseModelMatrix)
            .mul(initial.poseModelMatrix)
            .mul(translateMatrix).mul(scaleMatrix).mul(rotation.toMatrix())
        ;
        
        modelMatrix
            .set(new Matrix4f().toTranslated(-camera.getX(), 0, -camera.getZ()))
            .mul(poseModelMatrix);
    }
    
    
    public void set(Vec3f position, EulerAngles rotation){
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
    
    public Vec3f getScale(){
        return scale;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
}
