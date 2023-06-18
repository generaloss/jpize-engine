package pize.tests.voxelgame.client.control;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketPlayerSneaking;

public class PlayerController{
    
    private final Main session;
    
    private LocalPlayer player;
    private final CameraRotationController rotationController;
    
    public PlayerController(Main session){
        this.session = session;
        rotationController = new CameraRotationController();
    }
    
    public Main getSession(){
        return session;
    }
    
    public void update(){
        if(player == null)
            return;
        
        rotationController.update();
        player.getRotation().set(rotationController.getRotation());
        
        final Options options = session.getOptions();
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
        
        player.moveControl(forward, strafe);
        
        if(options.getKey(KeyMapping.JUMP).isPressed())
            player.jump();
        
        if(options.getKey(KeyMapping.SPRINT).isDown())
            player.setSprinting(true);
        else if(options.getKey(KeyMapping.SPRINT).isReleased())
            player.setSprinting(false);
        
        if(options.getKey(KeyMapping.SNEAK).isDown()){
            player.setSneaking(true);
            session.getGame().sendPacket(new SBPacketPlayerSneaking(player));
        }else if(options.getKey(KeyMapping.SNEAK).isReleased()){
            player.setSneaking(false);
            session.getGame().sendPacket(new SBPacketPlayerSneaking(player));
        }
    }
    
    
    public void setTargetPlayer(LocalPlayer player){
        this.player = player;
    }
    
    
    public CameraRotationController getRotationController(){
        return rotationController;
    }
    
}
