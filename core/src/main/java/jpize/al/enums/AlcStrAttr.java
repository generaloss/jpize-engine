package jpize.al.enums;

import static org.lwjgl.openal.ALC11.*;

public enum AlcStrAttr{

    DEFAULT_DEVICE_SPECIFIER        (ALC_DEFAULT_DEVICE_SPECIFIER        ),
    CAPTURE_DEFAULT_DEVICE_SPECIFIER(ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER),
    DEVICE_SPECIFIER                (ALC_DEVICE_SPECIFIER                ),
    CAPTURE_DEVICE_SPECIFIER        (ALC_CAPTURE_DEVICE_SPECIFIER        ),
    EXTENSIONS                      (ALC_EXTENSIONS                      );

    public final int ALC;

    AlcStrAttr(int ALC){
        this.ALC = ALC;
    }

}
