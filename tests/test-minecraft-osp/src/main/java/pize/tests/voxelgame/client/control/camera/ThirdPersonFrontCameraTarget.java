package pize.tests.voxelgame.client.control.camera;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.entity.LocalPlayer;

public class ThirdPersonFrontCameraTarget implements CameraTarget{
    
    private final LocalPlayer targetPlayer;
    private final Vec3f position;
    private final EulerAngles rotation;
    
    public ThirdPersonFrontCameraTarget(LocalPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
        position = new Vec3f();
        rotation = new EulerAngles();
    }
    
    @Override
    public Vec3f getPosition(){
        final float dist = 5;
        
        position.set(targetPlayer.getLerpPosition()).add(0, targetPlayer.getEyeHeight(), 0).add(targetPlayer.getRotation().getDirection().mul(dist));
        return position;
    }
    
    @Override
    public EulerAngles getRotation(){
        rotation.set(new EulerAngles().setDirection(targetPlayer.getRotation().getDirection().mul(-1)));
        return rotation;
    }
    
}

