package pize.tests.voxelgame.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.player.ServerPlayer;

import java.io.IOException;
import java.util.UUID;

public class CBPacketSpawnPlayer extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 15;
    
    public CBPacketSpawnPlayer(){
        super(PACKET_ID);
    }
    
    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;
    public String playerName;
    
    public CBPacketSpawnPlayer(ServerPlayer player){
        this();
        this.uuid = player.getUUID();
        this.position = player.getPosition();
        this.rotation = player.getRotation();
        this.playerName = player.getName();
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        playerName = stream.readUTF();
    }
    
}
