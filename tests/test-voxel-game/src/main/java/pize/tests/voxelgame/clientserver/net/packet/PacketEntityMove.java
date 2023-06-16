package pize.tests.voxelgame.clientserver.net.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.physic.Motion3D;
import pize.tests.voxelgame.server.player.OnlinePlayer;

import java.io.IOException;

public class PacketEntityMove extends IPacket{

    public static final int PACKET_ID = 9;

    public PacketEntityMove(){
        super(PACKET_ID);
    }


    public Vec3f position;
    public EulerAngles rotation;
    public Motion3D motion;
    public String playerName; //: HARAM IT S NOT A PLAER !!!! !!! !! !

    public PacketEntityMove(OnlinePlayer onlinePlayer){
        this();
        position = onlinePlayer.getPosition();
        rotation = onlinePlayer.getRotation();
        motion = onlinePlayer.getMotion();
        playerName = onlinePlayer.getName();
    }


    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeTuple3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
        stream.writeTuple3d(motion);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException{
        position = (Vec3f) stream.readTuple3f();
        rotation = stream.readEulerAngles();
        playerName = stream.readUTF();
        motion = new Motion3D(stream.readTuple3d());
    }

}