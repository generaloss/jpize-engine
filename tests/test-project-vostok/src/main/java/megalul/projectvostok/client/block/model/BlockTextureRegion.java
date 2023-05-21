package megalul.projectvostok.client.block.model;

import pize.graphics.texture.Region;

public class BlockTextureRegion{
    
    private final Region nx, px, ny, py, nz, pz;
    
    public BlockTextureRegion(Region nx, Region px, Region ny, Region py, Region nz, Region pz){
        this.nx = nx;
        this.px = px;
        this.ny = ny;
        this.py = py;
        this.nz = nz;
        this.pz = pz;
    }
    
    public BlockTextureRegion(Region side, Region ny, Region py){
        this(side, side, ny, py, side, side);
    }
    
    public BlockTextureRegion(Region side, Region y){
        this(side, y, y);
    }
    
    public BlockTextureRegion(Region region){
        this(region, region);
    }
    
    
    public Region getNx(){
        return nx;
    }
    
    public Region getPx(){
        return px;
    }
    
    public Region getNy(){
        return ny;
    }
    
    public Region getPy(){
        return py;
    }
    
    public Region getNz(){
        return nz;
    }
    
    public Region getPz(){
        return pz;
    }
    
}
