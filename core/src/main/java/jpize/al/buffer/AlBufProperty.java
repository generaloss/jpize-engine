package jpize.al.buffer;

import static org.lwjgl.openal.AL11.*;

public enum AlBufProperty{

    FREQUENCY(AL_FREQUENCY),
    BITS     (AL_BITS     ),
    CHANNELS (AL_CHANNELS ),
    SIZE     (AL_SIZE     );

    public final int AL;

    AlBufProperty(int AL){
        this.AL = AL;
    }

}
