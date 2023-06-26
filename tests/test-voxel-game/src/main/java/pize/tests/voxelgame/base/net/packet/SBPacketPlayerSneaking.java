package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.base.entity.Player;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.io.IOException;
import java.util.UUID;

public class SBPacketPlayerSneaking extends IPacket<PlayerConnectionAdapter>{
    
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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleSneaking(this);
    }
    
}