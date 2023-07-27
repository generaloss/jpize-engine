package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.main.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class CBPacketPlayerSneaking extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 17;
    
    public CBPacketPlayerSneaking(){
        super(PACKET_ID);
    }
    
    
    public UUID playerUUID;
    public boolean sneaking;
    
    public CBPacketPlayerSneaking(Player player){
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
    
}