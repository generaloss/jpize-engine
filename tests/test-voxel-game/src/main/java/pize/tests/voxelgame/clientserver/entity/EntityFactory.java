package pize.tests.voxelgame.clientserver.entity;

import pize.tests.voxelgame.clientserver.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
