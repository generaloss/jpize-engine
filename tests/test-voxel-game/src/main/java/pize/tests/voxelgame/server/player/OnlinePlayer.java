package pize.tests.voxelgame.server.player;

import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.tests.voxelgame.server.Server;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.packet.IPacket;

public class OnlinePlayer{
    
    private final PlayerProfile profile;
    private final TcpChannel channel;
    private final Vec3f position;
    private String worldName;
    private int renderDistance;
    
    public OnlinePlayer(Server serverOF, PlayerProfile profile, TcpChannel channel){
        this.profile = profile;
        this.channel = channel;
        
        position = new Vec3f();
        renderDistance = serverOF.getConfiguration().getMaxRenderDistance();//: 0 //! 0
    }
    
    
    public void sendPacket(IPacket packet){
        packet.write(channel);
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
    
    public String getName(){
        return profile.getName();
    }
    
    public TcpChannel getChannel(){
        return channel;
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
