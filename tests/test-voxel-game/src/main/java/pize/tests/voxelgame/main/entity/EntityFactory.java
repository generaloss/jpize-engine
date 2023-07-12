package pize.tests.voxelgame.main.entity;

import pize.tests.voxelgame.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
