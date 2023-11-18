package jpize.al.source;

import jpize.al.buffer.AlBuffer;
import jpize.al.object.AlObjectInt;
import jpize.math.vecmath.vector.Vec3f;
import jpize.util.Utils;

import static org.lwjgl.openal.AL11.*;

public class AlSource extends AlObjectInt{

    private static final float[] tmp_float_1 = new float[1];
    private static final float[] tmp_float_2 = new float[1];
    private static final float[] tmp_float_3 = new float[1];


    public AlSource(int ID){
        super(ID);
    }

    public AlSource(){
        super(alGenSources());
    }


    public int getBufferID(){
        return alGetSourcei(ID, AL_BUFFER);
    }

    public AlBuffer getBuffer(){
        return new AlBuffer(getBufferID());
    }

    public void setBuffer(int bufferID){
        alSourcei(ID, AL_BUFFER, bufferID);
    }

    public void setBuffer(AlBuffer buffer){
        if(buffer != null)
            setBuffer(buffer.getID());
    }


    public boolean isLooping(){
        return alGetSourcei(ID, AL_LOOPING) == 1;
    }

    public void setLooping(boolean looping){
        alSourcei(ID, AL_LOOPING, looping ? 1 : 0);
    }


    public float getGain(){
        return alGetSourcef(ID, AL_GAIN);
    }

    public void setGain(float gain){
        alSourcef(ID, AL_GAIN, gain);
    }


    public float getMaxGain(){
        return alGetSourcef(ID, AL_MAX_GAIN);
    }

    public void setMaxGain(float maxGain){
        alSourcef(ID, AL_MAX_GAIN, maxGain);
    }


    public float getMinGain(){
        return alGetSourcef(ID, AL_MIN_GAIN);
    }

    public void setMinGain(float minGain){
        alSourcef(ID, AL_MIN_GAIN, minGain);
    }


    public float getConeOuterGain(){
        return alGetSourcef(ID, AL_CONE_OUTER_GAIN);
    }

    public void setConeOuterGain(float coneOuterGain){
        alSourcef(ID, AL_CONE_OUTER_GAIN, coneOuterGain);
    }


    public float getConeInnerAngle(){
        return alGetSourcef(ID, AL_CONE_INNER_ANGLE);
    }

    public void setConeInnerAngle(float coneInnerAngle){
        alSourcef(ID, AL_CONE_INNER_ANGLE, coneInnerAngle);
    }


    public float getConeOuterAngle(){
        return alGetSourcef(ID, AL_CONE_OUTER_ANGLE);
    }

    public void setConeOuterAngle(float coneOuterAngle){
        alSourcef(ID, AL_CONE_OUTER_ANGLE, coneOuterAngle);
    }


    public float getPitch(){
        return alGetSourcef(ID, AL_PITCH);
    }

    public void setPitch(float pitch){
        alSourcef(ID, AL_PITCH, pitch);
    }


    public float getSecOffset(){
        return alGetSourcef(ID, AL_SEC_OFFSET);
    }

    public void setSecOffset(float secOffset){
        alSourcef(ID, AL_SEC_OFFSET, secOffset);
    }


    public float getSampleOffset(){
        return alGetSourcef(ID, AL_SAMPLE_OFFSET);
    }

    public void setSampleOffset(float sampleOffset){
        alSourcef(ID, AL_SAMPLE_OFFSET, sampleOffset);
    }


    public float getByteOffset(){
        return alGetSourcef(ID, AL_BYTE_OFFSET);
    }

    public void setByteOffset(float byteOffset){
        alSourcef(ID, AL_BYTE_OFFSET, byteOffset);
    }


    public boolean isRelative(boolean relative){
        return alGetSourcei(ID, AL_SOURCE_RELATIVE) == 1;
    }

    public void setRelative(boolean relative){
        alSourcei(ID, AL_SOURCE_RELATIVE, relative ? 1 : 0);
    }


    public Vec3f getPosition(){
        alGetSource3f(ID, AL_POSITION, tmp_float_1, tmp_float_2, tmp_float_3);
        return new Vec3f(tmp_float_1[0], tmp_float_2[0], tmp_float_3[0]);
    }

    public void setPosition(float x, float y, float z){
        alSource3f(ID, AL_POSITION, x, y, z);
    }


    public Vec3f getVelocity(){
        alGetSource3f(ID, AL_VELOCITY, tmp_float_1, tmp_float_2, tmp_float_3);
        return new Vec3f(tmp_float_1[0], tmp_float_2[0], tmp_float_3[0]);
    }

    public void setVelocity(float x, float y, float z){
        alSource3f(ID, AL_VELOCITY, x, y, z);
    }


    public Vec3f getDirection(){
        alGetSource3f(ID, AL_DIRECTION, tmp_float_1, tmp_float_2, tmp_float_3);
        return new Vec3f(tmp_float_1[0], tmp_float_2[0], tmp_float_3[0]);
    }

    public void setDirection(float x, float y, float z){
        alSource3f(ID, AL_DIRECTION, x, y, z);
    }


    public float getRolloffFactor(){
        return alGetSourcef(ID, AL_ROLLOFF_FACTOR);
    }

    public void setRolloffFactor(float rolloffFactor){
        alSourcef(ID, AL_ROLLOFF_FACTOR, rolloffFactor);
    }


    public float getMaxDistance(){
        return alGetSourcef(ID, AL_MAX_DISTANCE);
    }

    public void setMaxDistance(float maxDistance){
        alSourcef(ID, AL_MAX_DISTANCE, maxDistance);
    }


    public float getReferenceDistance(){
        return alGetSourcef(ID, AL_REFERENCE_DISTANCE);
    }

    public void setReferenceDistance(float referenceDistance){
        alSourcef(ID, AL_REFERENCE_DISTANCE, referenceDistance);
    }


    public AlSourceType getType(){
        return AlSourceType.fromAL(alGetSourcei(ID, AL_SOURCE_TYPE));
    }

    public void setType(AlSourceType type){
        alSourcei(ID, AL_SOURCE_TYPE, type.AL);
    }


    private AlSourceState getState(){
        return AlSourceState.fromAL(alGetSourcei(ID, AL_SOURCE_STATE));
    }

    public boolean isPlaying(){
        return getState() == AlSourceState.PLAYING;
    }

    public boolean isPaused(){
        return getState() == AlSourceState.PAUSED;
    }

    public boolean isStopped(){
        return getState() == AlSourceState.STOPPED;
    }

    public boolean isInitial(){
        return getState() == AlSourceState.INITIAL;
    }


    public void play(){
        alSourcePlay(ID);
    }

    public void pause(){
        alSourcePause(ID);
    }

    public void stop(){
        alSourceStop(ID);
    }

    public void rewind(){
        alSourceRewind(ID);
    }


    public void waitForStop(){
        Utils.waitFor(this::isStopped);
    }


    public int getBuffersQueued(){
        return alGetSourcei(ID, AL_BUFFERS_QUEUED);
    }

    public int getBuffersProcessed(){
        return alGetSourcei(ID, AL_BUFFERS_PROCESSED);
    }


    public void queueBuffer(AlBuffer buffer){
        alSourceQueueBuffers(ID, buffer.getID());
    }

    public void queueBuffers(AlBuffer[] buffers){
        alSourceQueueBuffers(ID, AlBuffer.getIDs(buffers));
    }


    public void unqueueBuffer(AlBuffer buffer){
        alSourceUnqueueBuffers(ID, new int[]{ buffer.getID() });
    }

    public void unqueueBuffers(AlBuffer[] buffers){
        alSourceUnqueueBuffers(ID, AlBuffer.getIDs(buffers));
    }

    public void unqueueBuffers(){
        alSourceUnqueueBuffers(ID);
    }


    public AlSource copy(){
        return new AlSource(ID);
    }

    @Override
    public void dispose(){
        alDeleteSources(ID);
    }

}
