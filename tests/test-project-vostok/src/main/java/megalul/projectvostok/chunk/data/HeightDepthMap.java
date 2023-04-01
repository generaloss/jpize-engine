package megalul.projectvostok.chunk.data;

import glit.math.Mathc;

import java.util.Arrays;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class HeightDepthMap{

    private final short[] heights, depths;
    private short max, min;

    public HeightDepthMap(){
        heights = new short[AREA];
        depths = new short[AREA];
        Arrays.fill(depths, (short) HEIGHT_IDX);
    }


    public int getHeight(int x, int z){
        return heights[getIndex(x, z)];
    }

    public void setHeight(int x, int z, int height){
        heights[getIndex(x, z)] = (short) height;
    }


    public int getDepth(int x, int z){
        return depths[getIndex(x, z)];
    }

    public void setDepth(int x, int z, int depth){
        depths[getIndex(x, z)] = (short) depth;
    }


    protected void updateMax(){
        max = 0;
        for(short height: heights)
            max = Mathc.max(height, max);
    }

    protected void updateMin(){
        min = HEIGHT_IDX;
        for(short depth: depths)
            min = Mathc.min(depth, min);
    }


    public int getMax(){
        return max;
    }

    public int getMin(){
        return min;
    }

}
