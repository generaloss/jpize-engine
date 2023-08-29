package jpize.io;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import jpize.glfw.input.GlfwAction;
import jpize.glfw.joystick.GlfwJoystick;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick extends GlfwJoystick{

    public Joystick(int ID){
        super(ID);

        GLFWGamepadState state = new GLFWGamepadState(BufferUtils.createByteBuffer(1));
        state.set(getButtons(), getAxes());
        glfwGetGamepadState(GLFW_JOYSTICK_3, state);
        if(state.buttons().get(GLFW_GAMEPAD_BUTTON_A) == GlfwAction.PRESS.GLFW)
            System.out.println("Jump");

        state.axes().get(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);
    }

}
