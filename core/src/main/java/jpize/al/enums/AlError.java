package jpize.al.enums;

import static org.lwjgl.openal.AL11.*;

public enum AlError{

    INVALID_NAME     (AL_INVALID_NAME     ),
    INVALID_ENUM     (AL_INVALID_ENUM     ),
    INVALID_VALUE    (AL_INVALID_VALUE    ),
    INVALID_OPERATION(AL_INVALID_OPERATION),
    OUT_OF_MEMORY    (AL_OUT_OF_MEMORY    ),

    NO_ERROR         (AL_NO_ERROR         );


    public final int AL;

    AlError(int AL){
        this.AL = AL;
    }


    public static AlError fromALC(int AL){
        if(AL == 0)
            return NO_ERROR;

        return values()[AL - INVALID_NAME.AL];
    }

}
