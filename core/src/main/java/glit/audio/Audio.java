package glit.audio;

import glit.context.Disposable;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.ALC11.*;

public class Audio implements Disposable{

    private final AudioDevice currentDevice;
    private final Map<String, AudioDevice> devices;

    public Audio(){
        devices = new HashMap<>();

        currentDevice = new AudioDevice(getDefaultOutputDevice());
        currentDevice.getContext().makeCurrent();
        devices.put(currentDevice.getName(), currentDevice);
    }


    public Collection<AudioDevice> getDevices(){
        return devices.values();
    }

    public AudioDevice getCurrent(){
        return currentDevice;
    }

    public AudioDevice getDevice(String name){
        if(!devices.containsKey(name)){
            List<String> availableDevices = getAvailableDevices();
            if(availableDevices != null && availableDevices.contains(name)){
                AudioDevice device = new AudioDevice(getDefaultOutputDevice());
                devices.put(name, device);
            }
        }

        return devices.get(name);
    }


    @Override
    public void dispose(){
        alcMakeContextCurrent(0);
        for(AudioDevice device: devices.values())
            device.dispose();

        ALC.destroy();
    }


    public static String getDefaultOutputDevice(){
        return alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    }

    public static String getDefaultInputDevice(){
        return alcGetString(0, ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER);
    }

    public static List<String> getAvailableDevices(){
        if(alcIsExtensionPresent(0, "ALC_ENUMERATION_EXT"))
            return ALUtil.getStringList(0, ALC_CAPTURE_DEVICE_SPECIFIER);
        else
            return null;
    }

}
