package megalul.projectvostok.client.world;

import megalul.projectvostok.Main;
import megalul.projectvostok.clientserver.entity.Entity;
import megalul.projectvostok.clientserver.world.World;

import java.util.Collection;

public class ClientWorld implements World{
    
    private final Main sessionOF;
    private final ClientChunkManager clientChunkManager;
    
    public ClientWorld(Main sessionOF){
        this.sessionOF = sessionOF;
        clientChunkManager = new ClientChunkManager(this);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        return 0;
    }
    
    @Override
    public byte getBlockID(int x, int y, int z){
        return 0;
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block){
    
    }
    
    @Override
    public int getLight(int x, int y, int z){
        return 0;
    }
    
    @Override
    public Collection<Entity> getEntities(){
        return null;
    }
    
    public ClientChunkManager getChunkManager(){
        return clientChunkManager;
    }
    
}
