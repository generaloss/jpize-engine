package jpize.tests.minecraftose.server.gen.pool;

public class ChunkBlock{

    public final byte lx, lz;
    public final short y;
    public final short blockData;

    public ChunkBlock(int lx, int y, int lz, short blockData){
        this.lx = (byte) lx;
        this.y = (short) y;
        this.lz = (byte) lz;
        this.blockData = blockData;
    }

}
