package megalul.projectvostok.control;

import pize.math.util.EulerAngle;
import pize.math.vecmath.tuple.Tuple3f;
import megalul.projectvostok.entity.Player;

public class FirstPersonPlayerCameraTarget implements CameraTarget{
    
    private final Player targetPlayer;
    
    public FirstPersonPlayerCameraTarget(Player targetPlayer){
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
