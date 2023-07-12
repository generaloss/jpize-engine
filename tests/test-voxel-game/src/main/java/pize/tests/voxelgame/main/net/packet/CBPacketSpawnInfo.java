package pize.tests.voxelgame.main.net.packet;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketSpawnInfo extends IPacket<PacketHandler>{
    
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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
    }
    
}
