package jpize.al.device;

import static org.lwjgl.openal.ALC11.alcCloseDevice;
import static org.lwjgl.openal.ALC11.alcOpenDevice;

public class AlDevice extends AlAbstractDevice {

    public AlDevice(long ID){
        super(ID);
    }

    public AlDevice(CharSequence deviceSpecifier){
        super(alcOpenDevice(deviceSpecifier));
    }

    @Override
    public void dispose(){
        alcCloseDevice(ID);
    }

}
