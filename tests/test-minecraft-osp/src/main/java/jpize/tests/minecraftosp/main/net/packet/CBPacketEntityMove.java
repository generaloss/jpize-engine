package jpize.tests.minecraftosp.main.net.packet;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftosp.server.player.ServerPlayer;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import jpize.physic.Velocity3f;

import java.io.IOException;
import java.util.UUID;

public class CBPacketEntityMove extends IPacket<PacketHandler>{

    public static final int PACKET_ID = 9;

    public CBPacketEntityMove(){
        super(PACKET_ID);
    }


    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;
    public Velocity3f velocity;

    public CBPacketEntityMove(ServerPlayer serverPlayer){
        this();
        uuid = serverPlayer.getUUID();
        position = serverPlayer.getPosition();
        rotation = serverPlayer.getRotation();
        velocity = serverPlayer.getVelocity();
    }


    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }

}