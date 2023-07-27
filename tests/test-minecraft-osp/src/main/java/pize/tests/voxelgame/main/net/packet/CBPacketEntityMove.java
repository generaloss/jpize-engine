package pize.tests.voxelgame.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.physic.Velocity3f;
import pize.tests.voxelgame.server.player.ServerPlayer;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(PizeInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }

}