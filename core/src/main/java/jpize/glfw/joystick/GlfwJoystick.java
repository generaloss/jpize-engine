package jpize.glfw.joystick;

import jpize.glfw.object.GlfwObjectInt;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class GlfwJoystick extends GlfwObjectInt{
    
    public GlfwJoystick(int ID){
        super(ID);
    }





    public String getName(){
        return glfwGetJoystickName(ID);
    }

    public boolean isGamepad(){
        return glfwJoystickIsGamepad(ID);
    }


    public FloatBuffer getAxes(){
        return glfwGetJoystickAxes(ID);
    }

    public ByteBuffer getButtons(){
        return glfwGetJoystickButtons(ID);
    }

    public ByteBuffer getHats(){
        return glfwGetJoystickHats(ID);
    }


    public long getUserPointer(){
        return glfwGetJoystickUserPointer(ID);
    }
    
    public void setUserPointer(long pointer){
        glfwSetJoystickUserPointer(ID, pointer);
    }

}
