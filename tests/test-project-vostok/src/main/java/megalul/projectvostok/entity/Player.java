package megalul.projectvostok.entity;

import pize.physic.BoundingBox;
import megalul.projectvostok.world.World;

public class Player extends Entity{
    
    public Player(World worldOf){
        super(worldOf, new BoundingBox(0, 0, 0, 1, 2, 1));
    }
    
}
