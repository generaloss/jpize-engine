package pize.tests.voxelgame.client.control.camera;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.entity.LocalPlayer;

public class FirstPersonPlayerCameraTarget implements CameraTarget{
    
    private final LocalPlayer targetPlayer;
    private final Vec3f position;
    private final EulerAngles rotation;
    
    public FirstPersonPlayerCameraTarget(LocalPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
        position = new Vec3f();
        rotation = new EulerAngles();
    }
    
    @Override
    public Vec3f getPosition(){
        position.set(targetPlayer.getLerpPosition()).add(0, targetPlayer.getEyeHeight(), 0);
        return position;
    }
    
    @Override
    public EulerAngles getRotation(){
        rotation.set(targetPlayer.getRotation());
        return rotation;
    }
    
}
