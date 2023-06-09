package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketOfflinePlayerState extends IPacket{ // Server->Client
    
    public static final int PACKET_ID = 13;
    
    public PacketOfflinePlayerState(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    
    public PacketOfflinePlayerState(Vec3f position){
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
