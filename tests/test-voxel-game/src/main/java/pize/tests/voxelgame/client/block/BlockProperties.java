package pize.tests.voxelgame.client.block;

import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollideShape;
import pize.tests.voxelgame.client.block.shape.BlockCursorShape;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public abstract class BlockProperties{
    
    private final int id;
    private final short defaultState;

    protected boolean solid;
    protected int lightLevel;
    protected int opacity;
    protected BlockCollideShape collideShape;
    protected BlockCursorShape cursorShape;
    protected BlockModel model;
    
    protected BlockProperties(int id){
        this.id = id;
        this.defaultState = BlockState.getState(id);
        
        solid = false;
        lightLevel = 0;
        opacity = 0;
        collideShape = null;
        cursorShape = null;
        model = null;
    }

    public final int getID(){
        return id;
    }
    
    public short getDefaultState(){
        return defaultState;
    }
    
    
    /** Возвращает True если это воздух */
    public final boolean isEmpty(){
        return id == Blocks.AIR.getID();
    }
    
    /** Возвращает True если блок является источником света */
    public final boolean isGlow(){
        return lightLevel != 0;
    }
    
    /** Возвращает True если блок пропускает свет */
    public boolean isTransparent(){
        return opacity != MAX_LIGHT_LEVEL;
    }
    
    
    /** Возвращает True если блок имеет форму стандартного вокселя
     * (куб, а не любая сложная модель) */
    public boolean isSolid(){
        return solid;
    }
    
    /** Возвращает уровень света блока */
    public int getLightLevel(){
        return lightLevel;
    }
    
    /** Возвращает непрозрачность блока
     * (например: 0 - стекло, 15 - камень) */
    public int getOpacity(){
        return opacity;
    }
    
    /** Возвращает форму блока для коллизии */
    public BlockCollideShape getCollideShape(){
        return collideShape;
    }
    
    /** Возвращает форму блока для курсора */
    public BlockCursorShape getCursorShape(){
        return cursorShape;
    }
    
    /** Возвращает модель блока */
    public BlockModel getModel(){
        return model;
    }
    
    
    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        
        if(object == null || getClass() != object.getClass())
            return false;
        
        final BlockProperties blockProperties = (BlockProperties) object;
        return id == blockProperties.id;
    }
    
    @Override
    public int hashCode(){
        return id;
    }
}
