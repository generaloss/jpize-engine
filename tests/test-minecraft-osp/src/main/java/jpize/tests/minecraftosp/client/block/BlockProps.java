package jpize.tests.minecraftosp.client.block;

import jpize.tests.minecraftosp.client.block.model.BlockModel;
import jpize.tests.minecraftosp.client.block.shape.BlockCollide;
import jpize.tests.minecraftosp.client.block.shape.BlockCursor;
import jpize.tests.minecraftosp.main.Dir;
import jpize.tests.minecraftosp.main.audio.BlockSoundPack;

import java.util.Objects;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class BlockProps{

    private final Block block;
    private final byte state;

    private boolean solid;
    private int lightLevel;
    private int opacity;
    private boolean translucent;
    private BlockSoundPack soundPack;

    private Dir facing;
    private BlockModel model;
    private BlockCollide collide;
    private BlockCursor cursor;

    public BlockProps(Block block, byte state){
        this.block = block;
        this.state = state;

        this.solid = false;
        this.lightLevel = 0;
        this.opacity = 0;
        this.translucent = false;
        this.soundPack = null;

        this.facing = Dir.NONE;
        this.model = null;
        this.collide = null;
        this.cursor = null;

        // --- EXAMPLE OF BLOCK DEFINITION --- //

        // final BlockModel model = ;
        //
        // newState()
        //         .setSolid(true)
        //         .setLightLevel(0)
        //         .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
        //         .setTranslucent(false)
        //         .setSoundPack(BlockSoundPack.STONE)
        //
        //         .setFacing(Dir.NONE)
        //         .setModel(model)
        //         .setCollide(BlockCollide.SOLID)
        //         .setCursor(BlockCursor.SOLID);
    }

    public Block getBlock(){
        return block;
    }

    public byte getState(){
        return state;
    }

    public short getData(){
        return block.getData(state);
    }

    public final byte getID(){
        return block.getID();
    }

    /** Возвращает True если это воздух */
    public final boolean isEmpty(){
        return block.isEmpty();
    }


    public BlockProps setSolid(boolean solid){
        this.solid = solid;
        return this;
    }

    public BlockProps setLightLevel(int lightLevel){
        this.lightLevel = lightLevel;
        return this;
    }

    public BlockProps setOpacity(int opacity){
        this.opacity = opacity;
        return this;
    }

    public BlockProps setTranslucent(boolean translucent){
        this.translucent = translucent;
        return this;
    }

    public BlockProps setSoundPack(BlockSoundPack soundPack){
        this.soundPack = soundPack;
        return this;
    }


    public BlockProps setFacing(Dir facing){
        this.facing = facing;
        return this;
    }

    public BlockProps setModel(BlockModel model){
        this.model = model;
        return this;
    }

    public BlockProps setCollide(BlockCollide collide){
        this.collide = collide;
        return this;
    }

    public BlockProps setCursor(BlockCursor cursor){
        this.cursor = cursor;
        return this;
    }


    /** Возвращает True если блок является источником света */
    public final boolean isGlow(){
        return lightLevel != 0;
    }

    /** Возвращает True если блок пропускает свет */
    public boolean isLightTranslucent(){
        return opacity != MAX_LIGHT_LEVEL;
    }

    /** Возвращает True если блок полностью прозрачен для света */
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

    /** Возвращает набор звуков для блока */
    public BlockSoundPack getSoundPack(){
        return soundPack;
    }


    /** Возвращает поворот блока */
    public Dir getFacing(){
        return facing;
    }

    /** Возвращает форму блока для коллизии */
    public BlockCollide getCollide(){
        return collide;
    }

    /** Возвращает форму блока для курсора */
    public BlockCursor getCursor(){
        return cursor;
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

        final BlockProps blockState = (BlockProps) object;
        return state == blockState.state && block.getID() == blockState.block.getID();
    }

    @Override
    public int hashCode(){
        return Objects.hash(state, block.getID());
    }

}
