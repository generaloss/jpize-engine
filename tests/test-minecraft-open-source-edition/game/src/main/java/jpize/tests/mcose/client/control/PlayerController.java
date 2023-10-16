package jpize.tests.mcose.client.control;

import jpize.glfw.key.Key;
import jpize.graphics.camera.controller.Rotation3DController;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.client.control.camera.GameCamera;
import jpize.tests.mcose.client.control.camera.HorizontalMoveController;
import jpize.tests.mcose.client.control.camera.PerspectiveType;
import jpize.tests.mcose.client.entity.LocalPlayer;
import jpize.tests.mcose.client.level.ClientLevel;
import jpize.tests.mcose.client.options.KeyMapping;
import jpize.tests.mcose.client.options.Options;
import jpize.tests.mcose.main.Tickable;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketPlayerSneaking;
import jpize.util.time.Stopwatch;

public class PlayerController implements Tickable{

    private final LocalPlayer player;

    private final Rotation3DController rotationController;
    private final HorizontalMoveController horizontalMoveController;
    private final Stopwatch prevJumpTime;
    
    public PlayerController(LocalPlayer player){
        this.player = player;

        this.rotationController = new Rotation3DController();
        this.horizontalMoveController = new HorizontalMoveController(this);
        this.prevJumpTime = new Stopwatch();
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }

    public void update(){
        if(player == null)
            return;
        final ClientLevel level = player.getLevel();
        if(level == null)
            return;

        final Minecraft session = level.getGame().getSession();
        final Options options = session.getOptions();

        // Rotation
        rotationController.update();
        player.getRotation().set(rotationController.getRotation());

        // Horizontal motion
        horizontalMoveController.update();

        if(options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isPressed() ||
            options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isDown())
            player.setSprinting(true);
        else if(options.getKey(KeyMapping.FORWARD).isReleased())
            player.setSprinting(false);

        if(options.getKey(KeyMapping.SNEAK).isDown()){
            player.setSneaking(true);
            session.getGame().getConnectionHandler().sendPacket(new SBPacketPlayerSneaking(player));
        }else if(options.getKey(KeyMapping.SNEAK).isReleased()){
            player.setSneaking(false);
            session.getGame().getConnectionHandler().sendPacket(new SBPacketPlayerSneaking(player));
        }

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

    public void tick(){

    }
    
    
    public Rotation3DController getRotationController(){
        return rotationController;
    }

    public HorizontalMoveController getHorizontalMoveController(){
        return horizontalMoveController;
    }

}
