package jpize.tests.minecraftose.client.entity;

import jpize.Jpize;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftose.client.entity.model.PlayerModel;
import jpize.tests.minecraftose.main.entity.Player;
import jpize.tests.minecraftose.main.level.Level;
import jpize.tests.minecraftose.client.level.ClientLevel;

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
        Jpize.execSync(()->{
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
        final float lastTickTime = (System.currentTimeMillis() - lastTime) / 1000F / Jpize.getFixedUpdateDt();
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
