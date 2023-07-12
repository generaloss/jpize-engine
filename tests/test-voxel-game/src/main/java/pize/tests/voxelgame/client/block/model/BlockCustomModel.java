package pize.tests.voxelgame.client.block.model;

import java.util.ArrayList;
import java.util.List;

public class BlockCustomModel extends BlockModel{
    
    final List<Float> vertices;
    
    public BlockCustomModel(){
        vertices = new ArrayList<>();
    }
    
    public List<Float> getVertices(){
        return vertices;
    }
    
}
