package pize.io.keyboard;

import pize.io.key.KeyState;

@FunctionalInterface
public interface KeyCallback{
    
    void invoke(int keyCode, KeyState action);
    
}
