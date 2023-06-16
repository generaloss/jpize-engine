package pize.tests.voxelgame.client.control;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;

public class PlayerController{
    
    private final Main sessionOF;
    
    private LocalPlayer player;
    private final CameraRotationController rotationController;
    
    public PlayerController(Main sessionOF){
        this.sessionOF = sessionOF;
        rotationController = new CameraRotationController();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public void update(){
        if(player == null)
            return;
        
        rotationController.update();
        player.getRotation().set(rotationController.getRotation());
        
        final Options options = sessionOF.getOptions();
        float forward = 0;
        float strafe = 0;
        
        if(options.getKey(KeyMapping.FORWARD).isPressed())
            forward++;
        if(options.getKey(KeyMapping.BACK).isPressed())
            forward--;
        if(options.getKey(KeyMapping.RIGHT).isPressed())
            strafe--;
        if(options.getKey(KeyMapping.LEFT).isPressed())
            strafe++;
        
        player.setMoving(forward, strafe);
        
        if(options.getKey(KeyMapping.JUMP).isPressed())
            player.jump();
        if(options.getKey(KeyMapping.SPRINT).isDown())
            player.sprint();
        if(options.getKey(KeyMapping.SNEAK).isDown())
            player.setSneaking(true);
        else if(options.getKey(KeyMapping.SNEAK).isReleased())
            player.setSneaking(false);
    }
    
    
    public void setTargetPlayer(LocalPlayer player){
        this.player = player;
    }
    
    
    public CameraRotationController getRotationController(){
        return rotationController;
    }
    
}
