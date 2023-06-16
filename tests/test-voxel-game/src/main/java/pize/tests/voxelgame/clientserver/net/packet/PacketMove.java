package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketMove extends IPacket{
    
    public static final int PACKET_ID = 9;
    
    public PacketMove(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    
    public PacketMove(Vec3f position){
        this();
        this.position = position;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeVec3f(position);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        position = stream.readVec3f();
    }
    
}
