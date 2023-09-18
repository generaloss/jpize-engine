package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.tests.minecraftose.main.entity.Player;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketPlayerSneaking extends IPacket<ClientConnection>{
    
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.playerSneaking(this);
    }
    
}