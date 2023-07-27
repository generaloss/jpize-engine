package pize.tests.voxelgame.client.entity;

import pize.Pize;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.entity.model.PlayerModel;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.main.entity.Player;
import pize.tests.voxelgame.main.level.Level;

public class AbstractClientPlayer extends Player{
    
    private PlayerModel model;
    
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
        
        // Player model
        if(model == null){
            Pize.execSync(()->{
                System.out.println(1);
                model = new PlayerModel(this);
            });
        }
        
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
