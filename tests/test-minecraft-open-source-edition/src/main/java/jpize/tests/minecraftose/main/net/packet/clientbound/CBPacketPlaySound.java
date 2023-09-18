package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.tests.minecraftose.main.audio.Sound;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketPlaySound extends IPacket<ClientConnection>{

    public static final int PACKET_ID = 23;

    public CBPacketPlaySound(){
        super(PACKET_ID);
    }


    public Sound sound;
    public float volume, pitch, x, y, z;

    public CBPacketPlaySound(Sound sound, float volume, float pitch, float x, float y, float z){
        this();
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CBPacketPlaySound(Sound sound, float volume, float pitch, Vec3f position){
        this(sound, volume, pitch, position.x, position.y, position.z);
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeByte(sound.ordinal());
        stream.writeFloat(volume);
        stream.writeFloat(pitch);
        stream.writeFloat(x);
        stream.writeFloat(y);
        stream.writeFloat(z);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        sound = Sound.values()[stream.readByte()];
        volume = stream.readFloat();
        pitch = stream.readFloat();
        x = stream.readFloat();
        y = stream.readFloat();
        z = stream.readFloat();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.playSound(this);
    }

}
