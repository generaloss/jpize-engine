package pize.tests.voxelgame.client.block;

import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollide;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;
import pize.tests.voxelgame.main.audio.BlockSoundPack;
import pize.tests.voxelgame.main.block.BlockData;

import java.util.ArrayList;
import java.util.List;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public abstract class BlockProperties{
    
    private final byte ID;
    private final short defaultState;

    protected boolean solid;
    protected int lightLevel;
    protected int opacity;
    protected boolean translucent;
    protected BlockSoundPack soundPack;
    protected List<BlockState> states;

    protected BlockProperties(int ID){
        this.ID = (byte) ID;
        this.defaultState = BlockData.getData(ID);
        
        solid = false;
        lightLevel = 0;
        opacity = 0;
        translucent = false;
        soundPack = BlockSoundPack.STONE;
        states = new ArrayList<>(1);
    }

    protected abstract void load(GameResources resources);


    public final byte getID(){
        return ID;
    }
    
    public short getDefaultData(){
        return defaultState;
    }
    
    
    /** Возвращает True если это воздух */
    public final boolean isEmpty(){
        return ID == Blocks.AIR.getID();
    }
    
    /** Возвращает True если блок является источником света */
    public final boolean isGlow(){
        return lightLevel != 0;
    }
    
    /** Возвращает True если блок пропускает свет */
    public boolean isLightTranslucent(){
        return opacity != MAX_LIGHT_LEVEL;
    }

    public boolean isLightTransparent(){
        return opacity == 0;
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

    /** Возвращает True если блок полупрозрачный */
    public boolean isTranslucent(){
        return translucent;
    }


    /** Возвращает все возможные состояния блока */
    public List<BlockState> getStates(){
        return states;
    }

    /** Возвращает одно из состояний блока */
    public BlockState getState(int index){
        return states.get(index);
    }

    protected void newState(Direction facing, BlockModel model, BlockCollide collide, BlockCursor cursor){
        states.add(states.size(), new BlockState(facing, model, collide, cursor));
    }


    /** Возвращает набор звуков для блока */
    public BlockSoundPack getSoundPack(){
        return soundPack;
    }
    
    
    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        
        if(object == null || getClass() != object.getClass())
            return false;
        
        final BlockProperties blockProperties = (BlockProperties) object;
        return ID == blockProperties.ID;
    }
    
    @Override
    public int hashCode(){
        return ID;
    }
}
