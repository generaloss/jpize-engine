package pize.io.keyboard;

import pize.io.glfw.KeyAction;

@FunctionalInterface
public interface KeyCallback{
    
    void invoke(int keyCode, KeyAction action);
    
}
