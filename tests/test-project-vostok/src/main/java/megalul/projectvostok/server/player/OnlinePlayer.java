package megalul.projectvostok.server.player;

import megalul.projectvostok.clientserver.net.PlayerProfile;
import megalul.projectvostok.server.Server;
import pize.math.vecmath.vector.Vec3f;
import pize.net.security.KeyAES;

public class OnlinePlayer{
    
    private final PlayerProfile profile;
    private KeyAES clientKey;
    private final Vec3f position;
    private String worldName;
    private int renderDistance;
    
    public OnlinePlayer(Server serverOF, PlayerProfile profile){
        this.profile = profile;
        
        position = new Vec3f();
        renderDistance = serverOF.getConfiguration().getMaxRenderDistance();
    }
    
    
    public void setClientKey(KeyAES clientKey){
        this.clientKey = clientKey;
    }
    
    public void setWorldIn(String worldName){
        this.worldName = worldName;
    }
    
    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
    }
    
    
    public PlayerProfile getProfile(){
        return profile;
    }
    
    public KeyAES getClientKey(){
        return clientKey;
    }
    
    public Vec3f getPosition(){
        return position;
    }
    
    public String getWorldName(){
        return worldName;
    }
    
    public int getRenderDistance(){
        return renderDistance;
    }
    
}
