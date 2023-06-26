package pize.tests.voxelgame.base.entity;

import pize.tests.voxelgame.base.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
