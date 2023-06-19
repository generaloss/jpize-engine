package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.tuple.Tuple3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketTeleportPlayer extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 20;
    
    public CBPacketTeleportPlayer(){
        super(PACKET_ID);
    }
    
    
    public Tuple3f position;
    public EulerAngles rotation;
    public String levelName;
    
    public CBPacketTeleportPlayer(String levelName, Tuple3f position, EulerAngles rotation){
        this();
        this.levelName = levelName;
        this.position = position;
        this.rotation = rotation;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readTuple3f();
        rotation = stream.readEulerAngles();
    }
    
}