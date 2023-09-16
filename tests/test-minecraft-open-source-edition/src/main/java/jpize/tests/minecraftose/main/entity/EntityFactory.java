package jpize.tests.minecraftose.main.entity;

import jpize.tests.minecraftose.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
