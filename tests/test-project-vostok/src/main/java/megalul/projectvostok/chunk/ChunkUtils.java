package megalul.projectvostok.chunk;

public class ChunkUtils{

    public static final int SIZE_SHIFT = 4;

    public static final int SIZE = 1 << SIZE_SHIFT;
    public static final int HEIGHT = 256;

    public static final int AREA = SIZE * SIZE;
    public static final int VOLUME = AREA * HEIGHT;

    public static final int SIZE_IDX = SIZE - 1;
    public static final int HEIGHT_IDX = HEIGHT - 1;

    // C = data Container
    public static final int C_HEIGHT = HEIGHT + 2;
    public static final int C_SIZE = SIZE + 2;

    public static final int C_AREA = C_SIZE * C_SIZE;
    public static final int C_VOLUME = C_AREA * C_HEIGHT;

    public static final int C_SIZE_IDX = C_SIZE - 1;
    public static final int C_HEIGHT_IDX = C_HEIGHT - 1;


    public static int getIndexC(int x, int y, int z){
        return (x + 1) + (z + 1) * C_SIZE + (y + 1) * C_AREA;
    }

    public static int getIndex(int x, int y, int z){
        return x + z * SIZE + y * AREA;
    }

    public static int getIndex(int x, int z){
        return x + z * SIZE;
    }


    public static boolean isOutOfBounds(int x, int y, int z){
        return x >= SIZE || y >= HEIGHT || z >= SIZE || x < 0 || y < 0 || z < 0;
    }

    public static boolean isOutOfBounds(int x, int z){
        return x >= SIZE || z >= SIZE || x < 0 || z < 0;
    }


    public static int getLocalPos(int xOrZ){
        return xOrZ & SIZE_IDX;
    }

    public static int getChunkPos(int xOrZ){
        return xOrZ >> SIZE_SHIFT;
    }

}
