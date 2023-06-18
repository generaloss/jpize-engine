package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.physic.Motion3D;
import pize.tests.voxelgame.server.player.Entity;

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
    public Motion3D motion;

    public CBPacketEntityMove(Entity entity){
        this();
        uuid = entity.getUUID();
        position = entity.getPosition();
        rotation = entity.getRotation();
        motion = entity.getMotion();
    }


    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeTuple3d(motion);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = (Vec3f) stream.readTuple3f();
        rotation = stream.readEulerAngles();
        motion = new Motion3D(stream.readTuple3d());
    }

}