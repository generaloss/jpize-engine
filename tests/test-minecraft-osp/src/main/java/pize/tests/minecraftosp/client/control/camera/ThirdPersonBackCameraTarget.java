package pize.tests.minecraftosp.client.control.camera;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.entity.LocalPlayer;

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
    public Vec3f getPosition(){
        final float dist = 5;
        position.set(targetPlayer.getLerpPosition())
            .add(0, targetPlayer.getEyeHeight(), 0)
            .add(targetPlayer.getRotation().getDirection().mul(-dist));
        return position;
    }
    
    @Override
    public EulerAngles getRotation(){
        direction.set(targetPlayer.getRotation());
        return direction;
    }
    
}
