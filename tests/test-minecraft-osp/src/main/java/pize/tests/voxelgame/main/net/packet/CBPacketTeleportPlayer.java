package pize.tests.voxelgame.main.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class CBPacketTeleportPlayer extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 20;
    
    public CBPacketTeleportPlayer(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    public EulerAngles rotation;
    public String levelName;
    
    public CBPacketTeleportPlayer(String levelName, Vec3f position, EulerAngles rotation){
        this();
        this.levelName = levelName;
        this.position = position;
        this.rotation = rotation;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }
    
}