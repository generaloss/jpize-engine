package megalul.projectvostok.entity;

import pize.math.util.EulerAngle;
import pize.physic.BoundingBox;
import pize.physic.BoxBody;
import pize.physic.Velocity3D;
import megalul.projectvostok.world.World;

public abstract class Entity extends BoxBody{
    
    protected World worldOF;
    
    protected final Velocity3D velocity;
    protected final EulerAngle direction;
    
    public Entity(World worldOF, BoundingBox boundingBox){
        super(boundingBox);
        this.worldOF = worldOF;
        
        velocity = new Velocity3D();
        direction = new EulerAngle();
    }
    
    public World getWorldOf(){
        return worldOF;
    }
    
    public void update(){
    
    }
    
    
    public Velocity3D getVelocity(){
        return velocity;
    }
    
    public EulerAngle getDirection(){
        return direction;
    }
    
    public World getWorld(){
        return worldOF;
    }
    
}
