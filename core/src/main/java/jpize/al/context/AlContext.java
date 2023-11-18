package jpize.al.context;

import jpize.al.device.AlAbstractDevice;
import jpize.al.device.AlDevice;
import jpize.al.object.AlObjectLong;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;

import static org.lwjgl.openal.ALC11.*;

public class AlContext extends AlObjectLong{

    public AlContext(long ID){
        super(ID);
    }

    public AlContext(AlAbstractDevice device){
        super(alcCreateContext(device.getID(), new int[]{ 0 }));

        final AlContext prev = getCurrent();
        this.makeCurrent();
        AL.createCapabilities(ALC.createCapabilities(device.getID()));
        prev.makeCurrent();
    }

    public void makeCurrent(){
        alcMakeContextCurrent(ID);
    }

    public void process(){
        alcProcessContext(ID);
    }

    public void suspend(){
        alcSuspendContext(ID);
    }


    public long getDeviceID(){
        return alcGetContextsDevice(ID);
    }

    public AlDevice getDevice(){
        return new AlDevice(getDeviceID());
    }


    @Override
    public void dispose(){
        alcDestroyContext(ID);
    }


    public static long getCurrentContextID(){
        return alcGetCurrentContext();
    }

    public static AlContext getCurrent(){
        return new AlContext(getCurrentContextID());
    }

}
