package pize.tests.minecraftosp.client.entity;

import pize.Pize;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.entity.model.PlayerModel;
import pize.tests.minecraftosp.main.entity.Player;
import pize.tests.minecraftosp.main.level.Level;
import pize.tests.minecraftosp.client.level.ClientLevel;

public class AbstractClientPlayer extends Player{
    
    private volatile PlayerModel model;
    
    private long lastTime;
    private final Vec3f lastPosition, lerpPosition;
    private final EulerAngles lastRotation, lerpRotation;
    
    public AbstractClientPlayer(Level level, String name){
        super(level, name);
        
        // Interpolation
        lastTime = System.currentTimeMillis();
        lastPosition = new Vec3f();
        lerpPosition = new Vec3f();
        lastRotation = new EulerAngles();
        lerpRotation = new EulerAngles();

        // Player model
        Pize.execSync(()->{
            model = new PlayerModel(this);
        });
    }


    @Override
    public ClientLevel getLevel(){
        return (ClientLevel) super.getLevel();
    }

    @Override
    public void tick(){
        // Interpolation
        lastTime = System.currentTimeMillis();
        lastPosition.set(getPosition());
        lastRotation.set(getRotation());
        
        // Player tick
        super.tick();
    }
    
    public PlayerModel getModel(){
        return model;
    }
    
    
    public void updateInterpolation(){
        final float lastTickTime = (System.currentTimeMillis() - lastTime) / 1000F / Pize.getUpdateDt();
        lerpPosition.lerp(lastPosition, getPosition(), lastTickTime);
        lerpRotation.lerp(lastRotation, getRotation(), lastTickTime);
    }
    
    public Vec3f getLerpPosition(){
        return lerpPosition;
    }
    
    public EulerAngles getLerpRotation(){
        return lerpRotation;
    }
    
    public boolean isPositionChanged(){
        return !lastPosition.equals(getPosition());
    }
    
    public boolean isRotationChanged(){
        return !lastRotation.equals(getRotation());
    }
    
}
