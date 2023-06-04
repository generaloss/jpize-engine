package pize.io.monitor;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

public class Monitor{

    private final long id;
    private final GLFWVidMode videoMode;

    public Monitor(long id){
        this.id = id;
        videoMode = glfwGetVideoMode(id);
    }


    public int getRefreshRate(){
        return videoMode.refreshRate();
    }

    public int getWidth(){
        return videoMode.width();
    }

    public int getHeight(){
        return videoMode.height();
    }
    
    public float getAspect(){
        return (float) getWidth() / getHeight();
    }


    public int getRedBits(){
        return videoMode.redBits();
    }

    public int getGreenBits(){
        return videoMode.greenBits();
    }

    public int getBlueBits(){
        return videoMode.blueBits();
    }

    public int bitsPerPixel(){
        return videoMode.sizeof();
    }


    public String getName(){
        return glfwGetMonitorName(id);
    }


    public long getID(){
        return id;
    }

}
