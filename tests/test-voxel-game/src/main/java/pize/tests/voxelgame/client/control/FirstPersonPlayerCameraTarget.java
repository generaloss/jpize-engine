package pize.tests.voxelgame.client.control;

import pize.math.util.EulerAngle;
import pize.math.vecmath.tuple.Tuple3f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.entity.LocalPlayer;

public class FirstPersonPlayerCameraTarget implements CameraTarget{
    
    private final LocalPlayer targetPlayer;
    private final Vec3f position;
    private final EulerAngle rotation;
    
    public FirstPersonPlayerCameraTarget(LocalPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
        position = new Vec3f();
        rotation = new EulerAngle();
    }
    
    @Override
    public Tuple3f getPosition(){
        position.set(targetPlayer.getPosition()).add(0, targetPlayer.getEyes(), 0);
        return position;
    }
    
    @Override
    public EulerAngle getRotation(){
        rotation.set(targetPlayer.getRotation());
        return rotation;
    }
    
}
