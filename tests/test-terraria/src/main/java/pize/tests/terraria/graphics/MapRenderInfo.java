package pize.tests.terraria.graphics;

import pize.Pize;

public class MapRenderInfo{

    public static final int TILES_PER_SCREEN = 30;

    private float cellSize, cellsPerWidth, cellsPerHeight, scale;

    public MapRenderInfo(){
        scale = 1;
    }


    public void update(){
        int windowWidth = Pize.getWidth();
        int windowHeight = Pize.getHeight();

        if(windowWidth > windowHeight)
            cellSize = (float) windowWidth / TILES_PER_SCREEN;
        else
            cellSize = (float) windowHeight / TILES_PER_SCREEN;

        cellsPerWidth = windowWidth / cellSize;
        cellsPerHeight = windowHeight / cellSize;
    }


    public float getCellSize(){
        return cellSize;
    }

    public float getCellsPerWidth(){
        return cellsPerWidth;
    }

    public float getCellsPerHeight(){
        return cellsPerHeight;
    }


    public float getScale(){
        return scale;
    }

    public void mulScale(double scale){
        this.scale *= scale;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

}
