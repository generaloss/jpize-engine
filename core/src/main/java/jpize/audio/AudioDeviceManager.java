package jpize.audio;

import jpize.al.Al;
import jpize.al.device.AlDevice;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALUtil;

import java.util.*;

import static org.lwjgl.openal.ALC11.*;

public class AudioDeviceManager{

    private static AudioDeviceManager instance;

    public static void init(){
        if(instance == null)
            instance = new AudioDeviceManager();
    }

    public static AudioDeviceManager getInstance(){
        return instance;
    }


    private final AlDevice currentDevice;
    private final Map<String, AlDevice> devices;

    private AudioDeviceManager(){
        this.devices = new HashMap<>();

        this.currentDevice = new AlDevice(Al.getDefaultDeviceSpecifier());
        this.currentDevice.getContext().makeCurrent();
        this.devices.put(currentDevice.getName(), currentDevice);
    }


    public Collection<AlDevice> getDevices(){
        return devices.values();
    }

    public AlDevice getCurrentDevice(){
        return currentDevice;
    }

    public AlDevice getDevice(String name){
        if(!devices.containsKey(name)){
            final List<String> availableDevices = getAvailableDevices();

            if(availableDevices.contains(name)){
                final AlDevice device = new AlDevice(Al.getDefaultDeviceSpecifier());
                devices.put(name, device);
            }
        }

        return devices.get(name);
    }


    private static void dispose(){ // Calls from ContextManager
        alcMakeContextCurrent(0);
        for(AlDevice device: instance.devices.values())
            device.dispose();

        ALC.destroy();
    }


    public static List<String> getAvailableDevices(){
        final List<String> devicesList = ALUtil.getStringList(0, ALC_CAPTURE_DEVICE_SPECIFIER);
        return (devicesList == null) ? Collections.emptyList() : devicesList;
    }

}
