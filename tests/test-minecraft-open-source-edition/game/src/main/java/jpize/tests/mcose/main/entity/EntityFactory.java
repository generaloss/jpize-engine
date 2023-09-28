package jpize.tests.mcose.main.entity;

import jpize.tests.mcose.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
