package pize.glfw.monitor;

import org.lwjgl.glfw.GLFWVidMode;
import pize.glfw.object.GlfwObjectLong;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec2i;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class GlfwMonitor extends GlfwObjectLong{

    private final GLFWVidMode videoMode;

    public GlfwMonitor(long ID){
        super.ID = ID;
        videoMode = glfwGetVideoMode(ID);
    }

    public GLFWVidMode getVideoMode(){
        return videoMode;
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
        return glfwGetMonitorName(ID);
    }


    public Vec2f getContentScale(){
        final float[] scaleX = new float[1];
        final float[] scaleY = new float[1];

        glfwGetMonitorContentScale(ID, scaleX, scaleY);
        return new Vec2f(scaleX[0], scaleY[0]);
    }

    public Vec2i getPos(){
        final int[] x = new int[1];
        final int[] y = new int[1];

        glfwGetMonitorPos(ID, x, y);
        return new Vec2i(x[0], y[0]);
    }

    public Vec2i getPhysicalSize(){
        final int[] width = new int[1];
        final int[] height = new int[1];

        glfwGetMonitorPhysicalSize(ID, width, height);
        return new Vec2i(width[0], height[0]);
    }

    public Rectangle getWorkarea(){
        final int[] x = new int[1];
        final int[] y = new int[1];
        final int[] width = new int[1];
        final int[] height = new int[1];

        glfwGetMonitorWorkarea(ID, x, y, width, height);
        return new Rectangle(x[0], y[0], width[0], height[0]);
    }

}
