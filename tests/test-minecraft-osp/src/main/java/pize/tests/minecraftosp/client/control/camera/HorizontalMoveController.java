package pize.tests.minecraftosp.client.control.camera;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.options.KeyMapping;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.client.control.PlayerController;
import pize.tests.minecraftosp.client.entity.LocalPlayer;

public class HorizontalMoveController{
    
    private final PlayerController playerController;
    private final Vec3f motion;

    public HorizontalMoveController(PlayerController playerController){
        this.playerController = playerController;
        motion = new Vec3f();
    }
    
    public void update(){
        motion.zero();
        
        final Minecraft session = playerController.getSession();
        final Options options = session.getOptions();
        
        final LocalPlayer player = session.getGame().getPlayer();
        final Vec3f dir = player.getRotation().getDirectionHorizontal();
        
        if(options.getKey(KeyMapping.FORWARD).isPressed())
            motion.add(dir);
        if(options.getKey(KeyMapping.BACK).isPressed())
            motion.sub(dir);
        if(options.getKey(KeyMapping.LEFT).isPressed())
            motion.sub(dir.z, 0, -dir.x);
        if(options.getKey(KeyMapping.RIGHT).isPressed())
            motion.add(dir.z, 0, -dir.x);
        
        motion.nor();
    }
    
    public Vec3f getMotion(){
        return motion;
    }
    
}
