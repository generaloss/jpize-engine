package glit.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class MonitorManager{

    private static final MonitorManager instance = new MonitorManager();


    private final Map<Long, Monitor> monitors;

    private MonitorManager(){
        monitors = new HashMap<>();

        long primaryMonitorId = glfwGetPrimaryMonitor();
        monitors.put(primaryMonitorId, new Monitor(primaryMonitorId));

        glfwSetMonitorCallback((long monitorId, int event)->{
            switch(event){
                case GLFW_CONNECTED -> monitors.put(monitorId, new Monitor(monitorId));
                case GLFW_DISCONNECTED -> monitors.remove(monitorId);
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
