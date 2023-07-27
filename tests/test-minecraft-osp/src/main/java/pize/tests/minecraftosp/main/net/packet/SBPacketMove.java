package pize.tests.minecraftosp.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.client.entity.LocalPlayer;
import pize.tests.minecraftosp.server.net.PlayerGameConnection;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.physic.Velocity3f;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleMove(this);
    }
    
}
