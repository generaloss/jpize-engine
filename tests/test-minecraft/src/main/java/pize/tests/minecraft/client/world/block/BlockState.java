package pize.tests.minecraft.client.world.block;

import pize.tests.minecraft.server.item.Material;

public class BlockState{

    private Material type;

    public BlockState(Material type){
        setType(type);
    }


    public Material getType(){
        return type;
    }

    public void setType(Material type){
        this.type = type;
    }


    public boolean isEmpty(){
        return type.isEmpty();
    }


    public static final BlockState EMPTY = new BlockState(Material.AIR);

}
