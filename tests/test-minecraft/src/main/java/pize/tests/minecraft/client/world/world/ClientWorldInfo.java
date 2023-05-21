package pize.tests.minecraft.client.world.world;

import pize.tests.minecraft.client.world.block.BlockPos;

public class ClientWorldInfo{

    private final GameRules gameRules;
    private final BlockPos spawnPoint;
    private float spawnAngle;
    private long gameTime;

    public ClientWorldInfo(){
        gameRules = new GameRules();
        spawnPoint = new BlockPos();
    }

    public long getGameTime(){
        return this.gameTime;
    }

    public void setGameTime(long time){
        this.gameTime = time;
    }

    public float getSpawnAngle(){
        return this.spawnAngle;
    }

    public void setSpawnAngle(float spawnAngle){
        this.spawnAngle = spawnAngle;
    }

    public BlockPos getSpawnPoint(){
        return this.spawnPoint;
    }

    public void setSpawnPoint(BlockPos spawnPoint){
        this.spawnPoint.set(spawnPoint);
    }

    public void setSpawn(BlockPos spawnPoint,float angle){
        this.spawnPoint.set(spawnPoint);
        this.spawnAngle = angle;
    }

    public GameRules getGameRules(){
        return this.gameRules;
    }

}
