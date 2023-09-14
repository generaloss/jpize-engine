package jpize.tests.minecraftosp.main.net.packet;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftosp.client.entity.LocalPlayer;
import jpize.tests.minecraftosp.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import jpize.physic.utils.Velocity3f;

import java.io.IOException;

public class SBPacketMove extends IPacket<PlayerGameConnection>{
    
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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleMove(this);
    }
    
}
