package pize.io.monitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class MonitorManager{

    private static MonitorManager instance;
    
    public static void init(){
        instance = new MonitorManager();
    }


    private final Map<Long, Monitor> monitors;

    private MonitorManager(){
        monitors = new HashMap<>(2);

        long primaryMonitorID = glfwGetPrimaryMonitor();
        monitors.put(primaryMonitorID, new Monitor(primaryMonitorID));

        glfwSetMonitorCallback((long monitorID, int event)->{
            switch(event){
                case GLFW_CONNECTED -> monitors.put(monitorID, new Monitor(monitorID));
                case GLFW_DISCONNECTED -> monitors.remove(monitorID);
            }
        });
    }


    public static Monitor getMonitor(long id){
        return instance.monitors.get(id);
    }

    public static Monitor getPrimary(){
        return instance.monitors.get(glfwGetPrimaryMonitor());
    }


    public static Collection<Monitor> getMonitors(){
        return instance.monitors.values();
    }

}
