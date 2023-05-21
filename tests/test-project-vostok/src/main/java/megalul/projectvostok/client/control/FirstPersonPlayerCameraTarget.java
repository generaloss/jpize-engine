package megalul.projectvostok.client.control;

import pize.math.util.EulerAngle;
import pize.math.vecmath.tuple.Tuple3f;
import megalul.projectvostok.client.entity.ClientPlayer;

public class FirstPersonPlayerCameraTarget implements CameraTarget{
    
    private final ClientPlayer targetPlayer;
    
    public FirstPersonPlayerCameraTarget(ClientPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
    }
    
    @Override
    public Tuple3f getPosition(){
        return targetPlayer.getPosition();
    }
    
    @Override
    public EulerAngle getDirection(){
        return targetPlayer.getDirection();
    }
    
}
