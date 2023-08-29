package jpize.tests.minecraftosp.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftosp.main.entity.Player;
import jpize.tests.minecraftosp.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class SBPacketPlayerSneaking extends IPacket<PlayerGameConnection>{
    
    public static final int PACKET_ID = 16;
    
    public SBPacketPlayerSneaking(){
        super(PACKET_ID);
    }
    
    
    public UUID playerUUID;
    public boolean sneaking;
    
    public SBPacketPlayerSneaking(Player player){
        this();
        this.playerUUID = player.getUUID();
        this.sneaking = player.isSneaking();
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleSneaking(this);
    }
    
}