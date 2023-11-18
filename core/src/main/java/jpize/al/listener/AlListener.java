package jpize.al.listener;

import jpize.math.vecmath.vector.Vec3f;

import static org.lwjgl.openal.AL11.*;

public class AlListener {

    private static final float[] tmp_float_1 = new float[1];
    private static final float[] tmp_float_2 = new float[1];
    private static final float[] tmp_float_3 = new float[1];


    public static float getGain(){
        return alGetListenerf(AL_GAIN);
    }

    public static void setGain(float gain){
        alListenerf(AL_GAIN, gain);
    }


    public static Vec3f getVelocity(){
        alGetListener3f(AL_VELOCITY, tmp_float_1, tmp_float_2, tmp_float_3);
        return new Vec3f(tmp_float_1[0], tmp_float_2[0], tmp_float_3[0]);
    }

    public static void setVelocity(float x, float y, float z){
        alListener3f(AL_VELOCITY, x, y, z);
    }

    public static void setVelocity(Vec3f velocity){
        alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }


    public static Vec3f getPosition(){
        alGetListener3f(AL_POSITION, tmp_float_1, tmp_float_2, tmp_float_3);
        return new Vec3f(tmp_float_1[0], tmp_float_2[0], tmp_float_3[0]);
    }

    public static void setPosition(float x, float y, float z){
        alListener3f(AL_POSITION, x, y, z);
    }

    public static void setPosition(Vec3f position){
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }


    public static void setOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ){
        alListenerfv(AL_ORIENTATION, new float[]{ atX, atY, atZ, upX, upY, upZ });
    }

    public static void setOrientation(Vec3f at, Vec3f up){
        setOrientation(at.x, at.y, at.z, up.x, up.y, up.z);
    }

}
