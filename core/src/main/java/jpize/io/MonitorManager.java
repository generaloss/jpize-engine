package jpize.io;

import jpize.glfw.monitor.GlfwMonitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class MonitorManager{

    private static MonitorManager instance;
    
    public static void init(){
        instance = new MonitorManager();
    }


    private final Map<Long, GlfwMonitor> monitors;

    private MonitorManager(){
        monitors = new HashMap<>(2);

        long primaryMonitorID = glfwGetPrimaryMonitor();
        monitors.put(primaryMonitorID, new GlfwMonitor(primaryMonitorID));

        glfwSetMonitorCallback((long monitorID, int event)->{
            switch(event){
                case GLFW_CONNECTED -> monitors.put(monitorID, new GlfwMonitor(monitorID));
                case GLFW_DISCONNECTED -> monitors.remove(monitorID);
            }
        });
    }


    public static GlfwMonitor getMonitor(long id){
        return instance.monitors.get(id);
    }

    public static GlfwMonitor getPrimary(){
        return instance.monitors.get(glfwGetPrimaryMonitor());
    }


    public static Collection<GlfwMonitor> getMonitors(){
        return instance.monitors.values();
    }

}
