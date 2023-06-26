package pize.tests.voxelgame.client.control;

import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.base.net.packet.SBPacketPlayerSneaking;

import static pize.tests.voxelgame.client.control.PerspectiveType.*;

public class PlayerController{
    
    private final VoxelGame session;
    
    private LocalPlayer player;
    private final CameraRotationController rotationController;
    
    public PlayerController(VoxelGame session){
        this.session = session;
        rotationController = new CameraRotationController();
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    public void update(){
        if(player == null)
            return;
        final Options options = session.getOptions();
        
        // Rotation
        rotationController.update();
        player.getRotation().set(rotationController.getRotation());
        
        // Horizontal motion
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
        
        // Jump, Sprint, Sneak
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
        
        // Toggle perspective
        final GameCamera camera = session.getGame().getCamera();
        
        if(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE).isDown()){
            switch(camera.getPerspective()){
                
                case FIRST_PERSON -> camera.setPerspective(THIRD_PERSON_BACK);
                case THIRD_PERSON_BACK -> camera.setPerspective(THIRD_PERSON_FRONT);
                case THIRD_PERSON_FRONT -> camera.setPerspective(FIRST_PERSON);
            }
        }
    }
    
    
    public void setTargetPlayer(LocalPlayer player){
        this.player = player;
    }
    
    
    public CameraRotationController getRotationController(){
        return rotationController;
    }
    
}
