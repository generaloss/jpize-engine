package jpize.tests.minecraftose.server.level.light;

import jpize.tests.minecraftose.main.chunk.LevelChunk;

public class LightNode{

    public final LevelChunk chunk;
    public final byte lx;
    public final short y;
    public final byte lz;
    public final byte level;

    public LightNode(LevelChunk chunk, int lx, int y, int lz, int level){
        this.chunk = chunk;
        this.lx = (byte) lx;
        this.y = (short) y;
        this.lz = (byte) lz;
        this.level = (byte) level;
    }
}