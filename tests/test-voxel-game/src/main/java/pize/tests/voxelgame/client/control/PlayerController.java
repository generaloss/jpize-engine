package pize.tests.voxelgame.client.control;

import pize.Pize;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.entity.ClientPlayer;
import pize.tests.voxelgame.client.options.KeyMapping;

public class PlayerController{
    
    private final Main sessionOF;
    
    private ClientPlayer targetPlayer;
    private final CameraRotationController rotationController;
    private final Vec3f up = new Vec3f(0, 1, 0);
    
    public PlayerController(Main sessionOF){
        this.sessionOF = sessionOF;
        rotationController = new CameraRotationController();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public void update(){
        if(targetPlayer == null)
            return;
        
        rotationController.update();
        targetPlayer.getDirection().set(rotationController.getRotation());
        
        float speed = Pize.getDeltaTime() * 7;
        if(isPressed(KeyMapping.SPRINT))
            speed *= 13;
    
        Vec3f dir = rotationController.getRotation().direction();
        Vec3f acceleration = dir.clone();
        acceleration.y = 0;
        acceleration.nor().mul(speed);
    
        if(isPressed(KeyMapping.FORWARD))
            targetPlayer.getPosition().add(acceleration);
        if(isPressed(KeyMapping.BACK))
            targetPlayer.getPosition().sub(acceleration);
    
        Vec3f dirXZ = dir.clone();
        dirXZ.y = 0;
        Vec3f sideMove = Vec3f.crs(up, dirXZ.nor()).mul(speed);
        if(isPressed(KeyMapping.RIGHT))
            targetPlayer.getPosition().add(sideMove);
        if(isPressed(KeyMapping.LEFT))
            targetPlayer.getPosition().sub(sideMove);
        if(isPressed(KeyMapping.JUMP))
            targetPlayer.getPosition().y += speed;
        if(isPressed(KeyMapping.SNEAK))
            targetPlayer.getPosition().y -= speed;
    }
    
    private boolean isPressed(KeyMapping key){
        return sessionOF.getOptions().getKey(key).isPressed();
    }
    
    
    public void setTargetPlayer(ClientPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
    }
    
    
    public CameraRotationController getRotationController(){
        return rotationController;
    }
    
}
