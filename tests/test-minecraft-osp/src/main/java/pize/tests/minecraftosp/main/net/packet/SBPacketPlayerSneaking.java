package pize.tests.minecraftosp.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.main.entity.Player;
import pize.tests.minecraftosp.server.net.PlayerGameConnection;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleSneaking(this);
    }
    
}