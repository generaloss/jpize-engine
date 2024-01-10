package jpize.al.source;

import static org.lwjgl.openal.AL11.*;

public enum AlSourceState{

    INITIAL(AL_INITIAL),
    PLAYING(AL_PLAYING),
    PAUSED (AL_PAUSED ),
    STOPPED(AL_STOPPED),

    NONE(0);

    public final int AL;

    AlSourceState(int AL){
        this.AL = AL;
    }


    public static AlSourceState byAlConst(int AL){
        if(AL == 0)
            return NONE;
        return values()[AL - AL_INITIAL];
    }

}
