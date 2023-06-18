package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.physic.Motion3D;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.io.IOException;

public class SBPacketMove extends IPacket<PlayerConnectionAdapter>{
    
    public static final int PACKET_ID = 9;
    
    public SBPacketMove(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    public EulerAngles rotation;
    public Motion3D motion;
    
    public SBPacketMove(LocalPlayer localPlayer){
        this();
        position = localPlayer.getPosition();
        rotation = localPlayer.getRotation();
        motion = localPlayer.getMotion();
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeTuple3d(motion);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        position = (Vec3f) stream.readTuple3f();
        rotation = stream.readEulerAngles();
        motion = new Motion3D(stream.readTuple3d());
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleMove(this);
    }
    
}
