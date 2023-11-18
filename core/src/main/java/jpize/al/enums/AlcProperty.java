package jpize.al.enums;

import static org.lwjgl.openal.ALC11.*;

public enum AlcProperty{

    FREQUENCY     (ALC_FREQUENCY     ),
    MONO_SOURCES  (ALC_MONO_SOURCES  ),
    STEREO_SOURCES(ALC_STEREO_SOURCES),
    REFRESH       (ALC_REFRESH       ),
    SYNC          (ALC_SYNC          );

    public final int ALC;

    AlcProperty(int ALC){
        this.ALC = ALC;
    }

}
