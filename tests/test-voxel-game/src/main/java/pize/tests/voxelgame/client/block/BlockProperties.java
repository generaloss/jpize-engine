package pize.tests.voxelgame.client.block;

import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.block.model.BlockShape;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.*;

public abstract class BlockProperties{

    private final int id;

    protected BlockProperties(int id){
        this.id = id;
    }

    public final int getID(){
        return id;
    }
    
    
    /** Возвращает True если это воздух */
    public final boolean isEmpty(){
        return id == Block.AIR.ID;
    }
    
    /** Возвращает True если блок является источником света */
    public final boolean isGlow(){
        return getLightLevel() != 0;
    }
    
    /** Возвращает True если блок пропускает свет */
    public boolean isTransparent(){
        return getOpacity() != MAX_LIGHT_LEVEL;
    }
    
    /** Возвращает True если блок имеет форму стандартного вокселя
     * (куб, а не любая сложная модель)
     */
    public abstract boolean isSolid();
    
    /** Возвращает регионы на атласе для сторон блока */
    public abstract BlockTextureRegion getTextureRegion();
    
    /** Возвращает уровень света блока */
    public abstract int getLightLevel();
    
    /** Возвращает непрозрачность блока
     * (например: 0 - стекло, 15 - камень)
     */
    public abstract int getOpacity();
    
    /** Возвращает форму блока для коллизии */
    public abstract BlockShape getShape();
    
    
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
