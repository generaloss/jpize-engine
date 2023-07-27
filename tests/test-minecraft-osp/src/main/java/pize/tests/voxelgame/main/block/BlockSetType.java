package pize.tests.voxelgame.main.block;

public enum BlockSetType{

    DESTROY,
    PLACE,
    REPLACE;

    public static BlockSetType from(boolean oldBlockIsEmpty, boolean newBlockIsEmpty){
        if(oldBlockIsEmpty && !newBlockIsEmpty)
            return PLACE;
        else if(newBlockIsEmpty && !oldBlockIsEmpty)
            return DESTROY;
        return REPLACE;
    }

    public static BlockSetType from(short oldBlockState, short newBlockState){
        return from(
            BlockData.getID(oldBlockState) == 0,
            BlockData.getID(newBlockState) == 0
        );
    }

    public static BlockSetType from(byte oldBlockID, byte newBlockID){
        return from(
            oldBlockID == 0,
            newBlockID == 0
        );
    }

}
