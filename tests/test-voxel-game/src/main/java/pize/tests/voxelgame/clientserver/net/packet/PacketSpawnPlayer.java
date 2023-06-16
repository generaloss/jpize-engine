package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.player.OnlinePlayer;

import java.io.IOException;

public class PacketSpawnPlayer extends IPacket{

    public static final int PACKET_ID = 14;

    public PacketSpawnPlayer(){
        super(PACKET_ID);
    }


    public Vec3f position;
    public EulerAngles rotation;
    public String playerName;

    public PacketSpawnPlayer(OnlinePlayer player){
        this();
        position = player.getPosition();
        rotation = player.getRotation();
        playerName = player.getName();
    }


    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException{
        position = (Vec3f) stream.readTuple3f();
        rotation = stream.readEulerAngles();
        playerName = stream.readUTF();
    }

}