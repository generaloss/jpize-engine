package pize.tests.voxelgame.main.net.packet;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.main.audio.Sound;

import java.io.IOException;

public class CBPacketPlaySound extends IPacket<PacketHandler>{

    public static final int PACKET_ID = 23;

    public CBPacketPlaySound(){
        super(PACKET_ID);
    }


    public String soundID;
    public float volume, pitch, x, y, z;

    public CBPacketPlaySound(String soundID, float volume, float pitch, float x, float y, float z){
        this();
        this.soundID = soundID;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CBPacketPlaySound(Sound sound, float volume, float pitch, float x, float y, float z){
        this(sound.getID(), volume, pitch, x, y, z);
    }

    public CBPacketPlaySound(Sound sound, float volume, float pitch, Vec3f position){
        this(sound.getID(), volume, pitch, position.x, position.y, position.z);
    }


    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(soundID);
        stream.writeFloat(volume);
        stream.writeFloat(pitch);
        stream.writeFloat(x);
        stream.writeFloat(y);
        stream.writeFloat(z);
    }

    @Override
    public void read(PizeInputStream stream) throws IOException{
        soundID = stream.readUTF();
        volume = stream.readFloat();
        pitch = stream.readFloat();
        x = stream.readFloat();
        y = stream.readFloat();
        z = stream.readFloat();
    }

}
