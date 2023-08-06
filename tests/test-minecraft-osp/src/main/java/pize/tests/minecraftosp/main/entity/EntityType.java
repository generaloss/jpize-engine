package pize.tests.minecraftosp.main.entity;

import pize.physic.BoundingBox3f;
import pize.tests.minecraftosp.main.level.Level;

import java.util.HashMap;
import java.util.Map;

public class EntityType<T extends Entity>{
    
    public static final Map<Integer, EntityType<?>> entityMap = new HashMap<>();
    
    public static EntityType<?> fromEntityID(int entityID){
        return entityMap.get(entityID);
    }
    
    public static final EntityType<Player> PLAYER = new EntityType<>(level->new Player(level, ""), 1, new BoundingBox3f(-0.3, 0, -0.3, 0.3, 1.8, 0.3), false);
    
    
    private final EntityFactory<T> factory;
    private final int entityID;
    private final BoundingBox3f boundingBox;
    private final boolean summonable;
    
    public EntityType(EntityFactory<T> factory, int entityID, BoundingBox3f boundingBox, boolean summonable){
        this.factory = factory;
        this.entityID = entityID;
        this.boundingBox = boundingBox;
        this.summonable = summonable;
        
        entityMap.put(entityID, this);
    }
    
    public int getID(){
        return entityID;
    }
    
    public BoundingBox3f getBoundingBox(){
        return boundingBox;
    }
    
    public boolean isSummonable(){
        return summonable;
    }
    
    
    public T createEntity(Level level){
        if(!summonable)
            return null;
        
        return factory.create(level);
    }
    
}