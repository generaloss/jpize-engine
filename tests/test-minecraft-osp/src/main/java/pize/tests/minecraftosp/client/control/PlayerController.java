package pize.tests.minecraftosp.client.control;

import pize.graphics.camera.controller.Rotation3DController;
import pize.io.key.Key;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.control.camera.HorizontalMoveController;
import pize.tests.minecraftosp.client.control.camera.PerspectiveType;
import pize.tests.minecraftosp.client.options.KeyMapping;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.client.entity.LocalPlayer;
import pize.tests.minecraftosp.main.net.packet.SBPacketPlayerSneaking;
import pize.util.time.Stopwatch;

public class PlayerController{
    
    private final Minecraft session;
    
    private LocalPlayer player;
    private final Rotation3DController rotationController;
    private final HorizontalMoveController horizontalMoveController;
    private final Stopwatch prevJumpTime;
    
    public PlayerController(Minecraft session){
        this.session = session;

        this.rotationController = new Rotation3DController();
        this.horizontalMoveController = new HorizontalMoveController(this);
        this.prevJumpTime = new Stopwatch();
    }
    
    public Minecraft getSession(){
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
        horizontalMoveController.update();
        final Vec3f motion = horizontalMoveController.getMotion();
        player.moveControl(motion);
        
        // Jump, Sprint, Sneak
        if(options.getKey(KeyMapping.JUMP).isDown()){
            player.setJumping(true);

            // Activate Flying
            if(player.isFlyEnabled()){
                if(prevJumpTime.getMillis() < 350)
                    player.setFlying(!player.isFlying());
                prevJumpTime.stop().reset().start();
            }
        }else if(options.getKey(KeyMapping.JUMP).isReleased())
            player.setJumping(false);
        
        if(options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isPressed() ||
            options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isDown())
            player.setSprinting(true);
        else if(options.getKey(KeyMapping.FORWARD).isReleased())
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
                
                case FIRST_PERSON -> camera.setPerspective(PerspectiveType.THIRD_PERSON_BACK);
                case THIRD_PERSON_BACK -> camera.setPerspective(PerspectiveType.THIRD_PERSON_FRONT);
                case THIRD_PERSON_FRONT -> camera.setPerspective(PerspectiveType.FIRST_PERSON);
            }
        }

        // Boost
        if(Key.Y.isDown())
            player.getVelocity().mul(2, 1.4, 2);
        if(Key.H.isDown())
            player.getVelocity().add(camera.getRotation().getDirection().mul(2));
    }
    
    
    public void setTargetPlayer(LocalPlayer player){
        this.player = player;
    }
    
    
    public Rotation3DController getRotationController(){
        return rotationController;
    }
    
}
