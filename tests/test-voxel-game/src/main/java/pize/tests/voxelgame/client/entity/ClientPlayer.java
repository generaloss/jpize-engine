package pize.tests.voxelgame.client.entity;

import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.BoundingBox;

public class ClientPlayer extends Entity{
    
    private final Vec3f oldPosition;
    
    public ClientPlayer(){
        super(new BoundingBox(0, 0, 0, 1, 2, 1));
        
        oldPosition = new Vec3f();
    }
    
    public boolean checkPosition(){
        if(!oldPosition.equals(getPosition())){
            oldPosition.set(getPosition());
            return true;
        }
        return false;
    }
    
}
