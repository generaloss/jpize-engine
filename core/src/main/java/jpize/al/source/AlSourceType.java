package jpize.al.source;

import static org.lwjgl.openal.AL11.*;

public enum AlSourceType{

    STATIC      (AL_STATIC      ),
    STREAMING   (AL_STREAMING   ),
    UNDETERMINED(AL_UNDETERMINED);

    public final int AL;

    AlSourceType(int AL){
        this.AL = AL;
    }


    public static AlSourceType byAlConst(int AL){
        return switch(AL){
            case AL_STATIC -> STATIC;
            case AL_STREAMING -> STREAMING;
            default -> UNDETERMINED;
        };
    }

}
