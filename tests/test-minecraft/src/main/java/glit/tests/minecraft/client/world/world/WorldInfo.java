package glit.tests.minecraft.client.world.world;

import glit.tests.minecraft.client.world.block.BlockPos;

public class WorldInfo{

    private final String worldName;
    private final BlockPos spawnPosition;
    private final GameRules gameRules;

    public WorldInfo(String worldName){
        this.worldName = worldName;

        gameRules = new GameRules();
        spawnPosition = new BlockPos();
    }


    public String getWorldName(){
        return worldName;
    }

    public BlockPos getSpawnPosition(){
        return spawnPosition;
    }

    public GameRules getGameRules(){
        return gameRules;
    }

}
