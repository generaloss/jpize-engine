package jpize.al.enums;

import static org.lwjgl.openal.ALC11.*;

public enum AlcIntvAttr{

    MAJOR_VERSION   (ALC_MAJOR_VERSION  ),
    MINOR_VERSION   (ALC_MINOR_VERSION  ),
    ATTRIBUTES_SIZE (ALC_ATTRIBUTES_SIZE),
    ALL_ATTRIBUTES  (ALC_ALL_ATTRIBUTES );

    public final int ALC;

    AlcIntvAttr(int ALC){
        this.ALC = ALC;
    }

}
