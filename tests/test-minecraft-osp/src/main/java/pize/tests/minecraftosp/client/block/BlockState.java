package pize.tests.minecraftosp.client.block;

import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.main.Direction;

public class BlockState{

    private final Direction facing;
    private final BlockModel model;
    private final BlockCollide collide;
    private final BlockCursor cursor;

    public BlockState(Direction facing, BlockModel model, BlockCollide collide, BlockCursor cursor){
        this.facing = facing;
        this.model = model;
        this.collide = collide;
        this.cursor = cursor;
    }

    /** Возвращает поворот блока */
    public Direction getFacing(){
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


}
