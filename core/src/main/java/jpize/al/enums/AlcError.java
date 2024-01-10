package jpize.al.enums;

import static org.lwjgl.openal.ALC11.*;

public enum AlcError{

    INVALID_DEVICE (ALC_INVALID_DEVICE ),
    INVALID_CONTEXT(ALC_INVALID_CONTEXT),
    INVALID_ENUM   (ALC_INVALID_ENUM   ),
    INVALID_VALUE  (ALC_INVALID_VALUE  ),
    OUT_OF_MEMORY  (ALC_OUT_OF_MEMORY  ),

    NO_ERROR       (ALC_NO_ERROR       );


    public final int ALC;

    AlcError(int ALC){
        this.ALC = ALC;
    }

    public static AlcError byAlcConst(int ALC){
        if(ALC == 0)
            return NO_ERROR;
        return values()[ALC - INVALID_DEVICE.ALC];
    }

}
