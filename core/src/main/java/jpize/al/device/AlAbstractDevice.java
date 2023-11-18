package jpize.al.device;

import jpize.al.context.AlContext;
import jpize.al.enums.AlcError;
import jpize.al.enums.AlcIntvAttr;
import jpize.al.enums.AlcStrAttr;
import jpize.al.object.AlObjectLong;

import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;

public abstract class AlAbstractDevice extends AlObjectLong{

    private final AlContext context;

    public AlAbstractDevice(long ID){
        super(ID);
        this.context = new AlContext(this);
    }

    public AlContext getContext(){
        return context;
    }


    public String getAttribute(AlcStrAttr attribute){
        return alcGetString(ID, attribute.ALC);
    }

    public void getAttribute(AlcIntvAttr attribute, int[] dest){
        alcGetIntegerv(ID, attribute.ALC, dest);
    }

    public void getAttribute(AlcIntvAttr attribute, IntBuffer dest){
        alcGetIntegerv(ID, attribute.ALC, dest);
    }

    public String getSpecifier(){
        return getAttribute(AlcStrAttr.DEVICE_SPECIFIER);
    }

    public String getCaptureSpecifier(){
        return getAttribute(AlcStrAttr.CAPTURE_DEVICE_SPECIFIER);
    }

    public String getExtensions(){
        return getAttribute(AlcStrAttr.EXTENSIONS);
    }


    public String getName(){
        return alcGetString(ID, ALC_ALL_DEVICES_SPECIFIER);
    }

    public int getStereoSources(){
        return alcGetInteger(ID, ALC_STEREO_SOURCES);
    }

    public int getMonoSources(){
        return alcGetInteger(ID, ALC_MONO_SOURCES);
    }

    public int getSync(){
        return alcGetInteger(ID, ALC_SYNC);
    }

    public int getSamples(){
        return alcGetInteger(ID, ALC_CAPTURE_SAMPLES);
    }

    public int getFrequency(){
        return alcGetInteger(ID, ALC_FREQUENCY);
    }

    public int getRefreshRate(){
        return alcGetInteger(ID, ALC_REFRESH);
    }

    public AlcError getError(){
        return AlcError.fromALC(alcGetError(ID));
    }


    public boolean isExtensionPresent(CharSequence extName){
        return alcIsExtensionPresent(ID, extName);
    }

    public long getProcAddress(CharSequence funcName){
        return alcGetProcAddress(ID, funcName);
    }

    public int getEnumValue(CharSequence enumName){
        return alcGetEnumValue(ID, enumName);
    }

}
