package megalul.projectvostok.client.entity;

import megalul.projectvostok.clientserver.entity.Entity;
import pize.physic.BoundingBox;

public class ClientPlayer extends Entity{
    
    public ClientPlayer(){
        super(new BoundingBox(0, 0, 0, 1, 2, 1));
    }
    
}
