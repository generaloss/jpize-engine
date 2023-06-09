package pize.tests.voxelgame.clientserver.entity;

import pize.math.util.EulerAngle;
import pize.physic.BoundingBox;
import pize.physic.BoxBody;
import pize.physic.Velocity3D;

public abstract class Entity extends BoxBody{
    
    protected final Velocity3D velocity;
    protected final EulerAngle direction;
    
    public Entity(BoundingBox boundingBox){
        super(boundingBox);
        
        velocity = new Velocity3D();
        direction = new EulerAngle();
    }
    
    public void update(){
    
    }
    
    
    public Velocity3D getVelocity(){
        return velocity;
    }
    
    public EulerAngle getDirection(){
        return direction;
    }
    
}
