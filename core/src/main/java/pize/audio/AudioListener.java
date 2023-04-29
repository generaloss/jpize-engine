package pize.audio;

import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.tuple.Tuple3f;

import static org.lwjgl.openal.AL11.*;

public class AudioListener{

    public static void setSpeed(Tuple3f speed){
        alListener3f(AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    public static void setSpeed(Tuple3d speed){
        alListener3f(AL_VELOCITY, (float) speed.x, (float) speed.y, (float) speed.z);
    }

    public static void setPosition(Tuple3f position){
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }

    public static void setPosition(Tuple3d position){
        alListener3f(AL_POSITION, (float) position.x, (float) position.y, (float) position.z);
    }

    public static void setOrientation(Tuple3f at, Tuple3f up){
        float[] orientation = new float[6];

        orientation[0] = at.x;
        orientation[1] = at.y;
        orientation[2] = at.z;

        orientation[3] = up.x;
        orientation[4] = up.y;
        orientation[5] = up.z;

        alListenerfv(AL_ORIENTATION, orientation);
    }

}
