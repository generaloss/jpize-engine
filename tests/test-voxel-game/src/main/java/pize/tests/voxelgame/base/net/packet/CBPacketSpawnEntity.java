package pize.tests.voxelgame.base.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.base.entity.Entity;
import pize.tests.voxelgame.base.entity.EntityType;

import java.io.IOException;
import java.util.UUID;

public class CBPacketSpawnEntity extends IPacket<PacketHandler>{

    public static final int PACKET_ID = 14;

    public CBPacketSpawnEntity(){
        super(PACKET_ID);
    }
    
    public EntityType<?> type;
    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;

    public CBPacketSpawnEntity(Entity entity){
        this();
        type = entity.getEntityType();
        uuid = entity.getUUID();
        position = entity.getPosition();
        rotation = entity.getRotation();
    }


    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(type.getID());
        stream.writeUTF(uuid.toString());
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException{
        type = EntityType.fromEntityID(stream.readInt());
        uuid = UUID.fromString(stream.readUTF());
        position = (Vec3f) stream.readTuple3f();
        rotation = stream.readEulerAngles();
    }
    
}