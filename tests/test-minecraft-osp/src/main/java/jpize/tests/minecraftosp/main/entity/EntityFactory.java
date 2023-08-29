package jpize.tests.minecraftosp.main.entity;

import jpize.tests.minecraftosp.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
