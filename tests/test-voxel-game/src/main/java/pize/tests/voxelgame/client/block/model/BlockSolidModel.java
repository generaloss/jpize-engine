package pize.tests.voxelgame.client.block.model;

import pize.graphics.texture.Region;

public class BlockSolidModel extends BlockModel{
    
    private final Region nx, px, ny, py, nz, pz;
    
    public BlockSolidModel(Region nx, Region px, Region ny, Region py, Region nz, Region pz){
        this.nx = nx;
        this.px = px;
        this.ny = ny;
        this.py = py;
        this.nz = nz;
        this.pz = pz;
    }
    
    public BlockSolidModel(Region side, Region ny, Region py){
        this(side, side, ny, py, side, side);
    }
    
    public BlockSolidModel(Region side, Region y){
        this(side, y, y);
    }
    
    public BlockSolidModel(Region region){
        this(region, region);
    }
    
    
    public Region getSideNxRegion(){
        return nx;
    }
    
    public Region getSidePxRegion(){
        return px;
    }
    
    public Region getSideNyRegion(){
        return ny;
    }
    
    public Region getSidePyRegion(){
        return py;
    }
    
    public Region getSideNzRegion(){
        return nz;
    }
    
    public Region getSidePzRegion(){
        return pz;
    }
    
}
