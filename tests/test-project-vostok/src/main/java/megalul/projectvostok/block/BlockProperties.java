package megalul.projectvostok.block;

import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.block.model.BlockTextureRegion;

import static megalul.projectvostok.chunk.ChunkUtils.*;

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
    
    
    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        
        if(object == null || getClass() != object.getClass())
            return false;
        
        BlockProperties blockProperties = (BlockProperties) object;
        return id == blockProperties.id;
    }
    
    @Override
    public int hashCode(){
        return id;
    }
}
