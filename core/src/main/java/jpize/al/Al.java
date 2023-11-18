package jpize.al;

import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.openal.AL11.*;

public class Al{

    public static String getSpecifier(){
        return alcGetString(0, ALC_DEVICE_SPECIFIER);
    }

    public static String getDefaultDeviceSpecifier(){
        return alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    }

    public static String getCaptureDeviceName(){
        return alcGetString(0, ALC_CAPTURE_DEVICE_SPECIFIER);
    }

    public static String getDefaultCaptureDeviceName(){
        return alcGetString(0, ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER);
    }


    public static String getVendor(){
        return alGetString(AL_VENDOR);
    }

    public static String getVersion(){
        return alGetString(AL_VERSION);
    }

    public static String getRenderer(){
        return alGetString(AL_RENDERER);
    }

    public static String getExtensions(){
        return alGetString(AL_EXTENSIONS);
    }


    public static float getDopplerFactor(){
        return alGetFloat(AL_DOPPLER_FACTOR);
    }

    public static void setDopplerFactor(float dopplerFactor){
        alDopplerFactor(dopplerFactor);
    }


    public static float getSpeedOfSound(){
        return alGetFloat(AL_SPEED_OF_SOUND);
    }

    public static void setSpeedOfSound(float speedOfSound){
        alSpeedOfSound(speedOfSound);
    }


    public static AlDistModel getDistanceModel(){
        return AlDistModel.fromAL(alGetInteger(AL_DISTANCE_MODEL));
    }

    public static void setDistanceModel(AlDistModel distanceModel){
        alDistanceModel(distanceModel.AL);
    }

}
