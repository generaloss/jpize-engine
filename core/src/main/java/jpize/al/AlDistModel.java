package jpize.al;

import static org.lwjgl.openal.AL11.*;

public enum AlDistModel{

    INVERSE         (AL_INVERSE_DISTANCE         ),
    INVERSE_CLAMPED (AL_INVERSE_DISTANCE_CLAMPED ),
    LINEAR          (AL_LINEAR_DISTANCE          ),
    LINEAR_CLAMPED  (AL_LINEAR_DISTANCE_CLAMPED  ),
    EXPONENT        (AL_EXPONENT_DISTANCE        ),
    EXPONENT_CLAMPED(AL_EXPONENT_DISTANCE_CLAMPED),
    NONE            (AL_NONE                     );

    public final int AL;

    AlDistModel(int AL){
        this.AL = AL;
    }


    public static AlDistModel byAlConst(int AL){
        return values()[AL - AL_INVERSE_DISTANCE];
    }

}
