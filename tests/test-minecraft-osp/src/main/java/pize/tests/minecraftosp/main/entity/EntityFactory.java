package pize.tests.minecraftosp.main.entity;

import pize.tests.minecraftosp.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
