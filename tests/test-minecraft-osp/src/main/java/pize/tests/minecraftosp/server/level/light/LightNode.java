package pize.tests.minecraftosp.server.level.light;

import pize.tests.minecraftosp.server.chunk.ServerChunk;

public class LightNode{

    public final ServerChunk chunk;
    public final byte lx;
    public final short y;
    public final byte lz;
    public final byte level;

    public LightNode(ServerChunk chunk, int lx, int y, int lz, int level){
        this.chunk = chunk;
        this.lx = (byte) lx;
        this.y = (short) y;
        this.lz = (byte) lz;
        this.level = (byte) level;
    }
}