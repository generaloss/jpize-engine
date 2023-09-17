package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.tests.minecraftose.server.player.ServerPlayer;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketSpawnPlayer extends IPacket<ClientConnection>{
    
    public static final byte PACKET_ID = 15;
    
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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        playerName = stream.readUTF();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.spawnPlayer(this);
    }
    
}
