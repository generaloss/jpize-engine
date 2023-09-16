package jpize.tests.minecraftose.client.control;

import jpize.graphics.camera.controller.Rotation3DController;
import jpize.glfw.key.Key;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftose.Minecraft;
import jpize.tests.minecraftose.client.control.camera.GameCamera;
import jpize.tests.minecraftose.client.control.camera.HorizontalMoveController;
import jpize.tests.minecraftose.client.control.camera.PerspectiveType;
import jpize.tests.minecraftose.client.options.KeyMapping;
import jpize.tests.minecraftose.client.options.Options;
import jpize.tests.minecraftose.client.entity.LocalPlayer;
import jpize.tests.minecraftose.main.net.packet.SBPacketPlayerSneaking;
import jpize.util.time.Stopwatch;

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
