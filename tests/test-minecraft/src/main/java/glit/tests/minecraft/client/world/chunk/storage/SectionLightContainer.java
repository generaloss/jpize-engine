package glit.tests.minecraft.client.world.chunk.storage;

import glit.tests.minecraft.client.world.light.LightType;
import glit.tests.minecraft.client.world.chunk.ChunkSection;

public class SectionLightContainer extends ChunkData{

    private final LightType type;
    private final byte[] light;

    public SectionLightContainer(LightType type){
        this.type = type;
        light = new byte[ChunkSection.VOLUME];
    }


    public byte get(int x,int y,int z){
        return light[getIndex(x,y,z)];
    }

    public void set(int x,int y,int z,byte state){
        light[getIndex(x,y,z)] = state;
    }

    public byte swap(int x,int y,int z,byte state){
        byte swap = light[getIndex(x,y,z)];
        light[getIndex(x,y,z)] = state;
        return swap;
    }


    public LightType getType(){
        return type;
    }

    public byte[] array(){
        return light;
    }

}
