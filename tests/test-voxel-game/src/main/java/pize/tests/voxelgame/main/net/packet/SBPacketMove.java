package pize.tests.voxelgame.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.physic.Velocity3f;
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
    public Velocity3f velocity;
    
    public SBPacketMove(LocalPlayer localPlayer){
        this();
        position = localPlayer.getPosition();
        rotation = localPlayer.getRotation();
        velocity = localPlayer.getVelocity();
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleMove(this);
    }
    
}
