package jpize.al.source;

import static org.lwjgl.openal.AL11.*;

public enum AlSourceState{

    INITIAL(AL_INITIAL),
    PLAYING(AL_PLAYING),
    PAUSED (AL_PAUSED ),
    STOPPED(AL_STOPPED);

    public final int AL;

    AlSourceState(int AL){
        this.AL = AL;
    }


    public static AlSourceState fromAL(int AL){
        return values()[AL - AL_INITIAL];
    }

}
