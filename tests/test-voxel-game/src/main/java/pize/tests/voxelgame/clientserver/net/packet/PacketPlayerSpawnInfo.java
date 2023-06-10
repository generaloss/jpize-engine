package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketPlayerSpawnInfo extends IPacket{
    
    public static final int PACKET_ID = 13;
    
    public PacketPlayerSpawnInfo(){
        super(PACKET_ID);
    }
    
    
    public String worldName;
    public Vec3f position;
    
    public PacketPlayerSpawnInfo(String worldName, Vec3f position){
        this();
        
        this.worldName = worldName;
        this.position = position;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(worldName);
        stream.writeVec3f(position);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        worldName = stream.readUTF();
        position = stream.readVec3f();
    }
    
}
