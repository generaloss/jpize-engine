package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.tests.mcose.main.entity.Entity;
import jpize.tests.mcose.main.entity.EntityType;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketSpawnEntity extends IPacket<ClientPacketHandler>{

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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(type.getID());
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        type = EntityType.fromEntityID(stream.readInt());
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnEntity(this);
    }
    
}