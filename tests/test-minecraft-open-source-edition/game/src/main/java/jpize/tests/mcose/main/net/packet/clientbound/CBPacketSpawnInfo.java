package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketSpawnInfo extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 13;
    
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnInfo(this);
    }
    
}
