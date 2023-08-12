package pize.tests.minecraftosp.main.biome;

import pize.graphics.util.color.Color;

public abstract class BiomeProperties{

    protected float minTemperature, maxTemperature;
    protected Color grassColor, waterColor;
    protected float hillsMul, erosionMul;

    public BiomeProperties(){
        this.grassColor = new Color(0.44, 0.67, 0.28, 1);
        this.waterColor = new Color(0, 0.2, 0.9, 1);
        this.hillsMul = 1;
        this.erosionMul = 1;
    }

    public Color getGrassColor(){
        return grassColor;
    }

    public Color getWaterColor(){
        return waterColor;
    }

    public float getHillsMul(){
        return hillsMul;
    }

    public float getErosionMul(){
        return erosionMul;
    }

}
