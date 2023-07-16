package pize.io.joystick;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick{
    
    private final int id;
    private final String name;
    
    private final FloatBuffer axes;
    private final ByteBuffer buttons, hats;
    
    public Joystick(int id){
        this.id = id;
        this.name = glfwGetJoystickName(id);
        
        this.axes = glfwGetJoystickAxes(id);
        this.buttons = glfwGetJoystickButtons(id);
        this.hats = glfwGetJoystickHats(id);
        
        // GLFWGamepadState state = new GLFWGamepadState();
        // state.set(buttons, axes);
        // glfwGetGamepadState(GLFW_JOYSTICK_3, state);
        // {
        //     if (state.buttons[GLFW_GAMEPAD_BUTTON_A])
        //     {
        //         input_jump();
        //     }
        //
        //     input_speed(state.axes[GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER]);
        // }
    }
    
    
    public long getUserPointer(){
        return glfwGetJoystickUserPointer(id);
    }
    
    public void setUserPointer(long pointer){
        glfwSetJoystickUserPointer(id, pointer);
    }
    
    
    public boolean isGamepad(){
        return glfwJoystickIsGamepad(id);
    }
    
    
    public String getName(){
        return name;
    }
    
    public long getID(){
        return id;
    }

}
