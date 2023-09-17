package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketTeleportPlayer extends IPacket<ClientConnection>{
    
    public static final byte PACKET_ID = 20;
    
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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.teleportPlayer(this);
    }
    
}