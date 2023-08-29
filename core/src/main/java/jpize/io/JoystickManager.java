package jpize.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class JoystickManager{
    
    public static final int MAX_JOYSTICKS = GLFW_JOYSTICK_LAST + 1;
    
    private static JoystickManager instance;
    
    public static void init(){
        if(instance == null)
            instance = new JoystickManager();
    }
    
    
    private final Map<Integer, Joystick> joysticks;
    
    private JoystickManager(){
        joysticks = new HashMap<>(MAX_JOYSTICKS);
        glfwSetJoystickCallback((int joystickID, int event)->{
            switch(event){
                case GLFW_CONNECTED -> {
                    joysticks.put(joystickID, new Joystick(joystickID));
                    System.out.println("Connect Joystick");
                }
                case GLFW_DISCONNECTED -> {
                    joysticks.remove(joystickID);
                    System.out.println("Remove Joystick");
                }
            }
        });
    }
    
    
    public static Collection<Joystick> getJoysticks(){
        return instance.joysticks.values();
    }
    
    public static Joystick getMonitor(int id){
        return instance.joysticks.get(id);
    }
    
    
    public static boolean isPresent(int id){
        return glfwJoystickPresent(id);
    }

}
