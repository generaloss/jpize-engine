package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketSpawnInfo extends IPacket<ClientConnection>{
    
    public static final byte PACKET_ID = 13;
    
    public CBPacketSpawnInfo(){
        super(PACKET_ID);
    }
    
    
    public String levelName;
    public Vec3f position;
    
    public CBPacketSpawnInfo(String levelName, Vec3f position){
        this();
        
        this.levelName = levelName;
        this.position = position;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.spawnInfo(this);
    }
    
}
