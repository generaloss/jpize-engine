package pize.tests.voxelgame.client.control;

import pize.math.util.EulerAngles;
import pize.math.vecmath.tuple.Tuple3f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.entity.LocalPlayer;

public class ThirdPersonBackCameraTarget implements CameraTarget{
    
    private final LocalPlayer targetPlayer;
    private final Vec3f position;
    private final EulerAngles direction;
    
    public ThirdPersonBackCameraTarget(LocalPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
        position = new Vec3f();
        direction = new EulerAngles();
    }
    
    @Override
    public Tuple3f getPosition(){
        final float dist = 5;
        
        position.set(targetPlayer.getPosition()).add(0, targetPlayer.getEyeHeight(), 0).add(targetPlayer.getRotation().direction().mul(-dist));
        return position;
    }
    
    @Override
    public EulerAngles getRotation(){
        direction.set(targetPlayer.getRotation());
        direction.pitch += Math.max(0, Math.min(10, targetPlayer.getMotion().y));
        return direction;
    }
    
}
