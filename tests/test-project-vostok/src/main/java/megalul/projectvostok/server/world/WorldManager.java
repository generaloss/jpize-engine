package megalul.projectvostok.server.world;

import megalul.projectvostok.server.Server;
import megalul.projectvostok.server.chunk.gen.DefaultGenerator;

import java.util.HashMap;
import java.util.Map;

public class WorldManager{
    
    public final Server serverOF;
    public final Map<String, ServerWorld> loadedWorlds;
    
    public WorldManager(Server serverOF){
        this.serverOF = serverOF;
        
        loadedWorlds = new HashMap<>();
    }
    
    
    public boolean isWorldLoaded(String worldName){
        return loadedWorlds.containsKey(worldName);
    }
    
    public boolean isWorldExists(String worldName){ //: SHO
        return false;
    }
    
    public void loadWorld(String worldName){
        if(worldName == null || loadedWorlds.containsKey(worldName) || isWorldExists(worldName))
            return;
        
        ServerWorld world = new ServerWorld(serverOF);
        world.getConfiguration().load(worldName, DefaultGenerator.getInstance(), true);
        
        loadedWorlds.put(worldName, world);
        
        System.out.println("[SERVER]: Loaded world '" + worldName + "'");
    }
    
    public ServerWorld getWorld(String worldName){
        return loadedWorlds.get(worldName);
    }
    
}
