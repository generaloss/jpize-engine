package glit.tests.minecraft.client.net.packets;

import glit.math.Maths;

import java.nio.ByteBuffer;

public class PacketData{

    private ByteBuffer buffer;

    public PacketData(){
        buffer = ByteBuffer.allocateDirect(10);
        Maths.map(0,0,0,0,0);
    }

    public void put(int i){

    }

    public int read(){
        return 0;
    }

}
