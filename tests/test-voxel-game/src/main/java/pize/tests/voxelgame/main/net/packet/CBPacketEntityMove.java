package pize.tests.voxelgame.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.physic.Motion3f;
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
    public Motion3f motion;

    public CBPacketEntityMove(ServerPlayer serverPlayer){
        this();
        uuid = serverPlayer.getUUID();
        position = serverPlayer.getPosition();
        rotation = serverPlayer.getRotation();
        motion = serverPlayer.getMotion();
    }


    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(motion);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        motion = new Motion3f(stream.readVec3f());
    }

}