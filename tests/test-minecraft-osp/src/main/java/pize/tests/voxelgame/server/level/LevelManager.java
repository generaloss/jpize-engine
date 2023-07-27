package pize.tests.voxelgame.server.level;

import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LevelManager{
    
    public final Server server;
    public final Map<String, ServerLevel> loadedLevels;
    
    public LevelManager(Server server){
        this.server = server;
        
        loadedLevels = new HashMap<>();
    }
    
    public Server getServer(){
        return server;
    }
    
    
    public Collection<ServerLevel> getLoadedLevels(){
        return loadedLevels.values();
    }
    
    public ServerLevel getLevel(String worldName){
        return loadedLevels.get(worldName);
    }
    
    public ServerLevel getDefaultLevel(){
        return getLevel(server.getConfiguration().getDefaultLevelName());
    }
    
    public void loadLevel(String levelName){
        if(levelName == null || isLevelExists(levelName) || isLevelLoaded(levelName))
            return;
        
        // final ServerLevel level = new ServerLevel(server);
        // level.getConfiguration().load(levelName, seed, generator);
        // loadedLevels.put(levelName, level);
        // level.getChunkManager().start();
        
        System.out.println("[Server]: Loaded level '" + levelName + "'");
    }
    
    public void createLevel(String levelName, int seed, ChunkGenerator generator){
        if(levelName == null || !isLevelExists(levelName) || isLevelLoaded(levelName))
            return;
        
        final ServerLevel level = new ServerLevel(server);
        level.getConfiguration().load(levelName, seed, generator);
        loadedLevels.put(levelName, level);
        level.getChunkManager().start();
        
        System.out.println("[Server]: Created level '" + levelName + "'");
    }
    
    public boolean isLevelLoaded(String levelName){
        return loadedLevels.containsKey(levelName);
    }
    
    public boolean isLevelExists(String levelName){
        return true; //: SHO? SHIZA.
    }
    
}
