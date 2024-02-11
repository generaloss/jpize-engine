package jpize.app.input;

import com.sun.jna.ptr.IntByReference;
import io.github.libsdl4j.api.event.SdlEventsConst;
import io.github.libsdl4j.api.event.events.SDL_MouseMotionEvent;
import io.github.libsdl4j.api.event.events.SDL_MouseWheelEvent;
import io.github.libsdl4j.api.keyboard.SDL_Keysym;
import io.github.libsdl4j.api.keyboard.SdlKeyboard;
import io.github.libsdl4j.api.mouse.SDL_Button;
import io.github.libsdl4j.api.mouse.SdlMouse;
import io.github.libsdl4j.api.scancode.SdlScancodeConst;
import jpize.app.context.Context;
import jpize.util.math.vector.Vec2i;
import jpize.sdl.input.Btn;
import jpize.sdl.input.Key;
import jpize.sdl.input.KeyMods;
import jpize.sdl.window.SdlWindow;

import java.util.Arrays;

public class ContextInput{

    private static final IntByReference tmp_int_1 = new IntByReference();
    private static final IntByReference tmp_int_2 = new IntByReference();


    private final Context context;
    private final boolean[] keysDown, keysPressed, keysReleased;
    private final boolean[] buttonsDown, buttonsPressed, buttonsReleased;
    private int scrollX, scrollY, x, y, xrel, yrel;
    private final KeyMods keyMods;

    public ContextInput(Context context){
        this.context = context;

        this.keysDown = new boolean[SdlScancodeConst.SDL_NUM_SCANCODES];
        this.keysPressed = new boolean[SdlScancodeConst.SDL_NUM_SCANCODES];
        this.keysReleased = new boolean[SdlScancodeConst.SDL_NUM_SCANCODES];
        
        this.buttonsDown = new boolean[SDL_Button.SDL_BUTTON_X2];
        this.buttonsPressed = new boolean[SDL_Button.SDL_BUTTON_X2];
        this.buttonsReleased = new boolean[SDL_Button.SDL_BUTTON_X2];

        this.keyMods = new KeyMods();
    }


    public void clear(){
        Arrays.fill(keysDown, false);
        Arrays.fill(keysReleased, false);
        Arrays.fill(buttonsDown, false);
        Arrays.fill(buttonsReleased, false);

        scrollX = 0;
        scrollY = 0;
        xrel = 0;
        yrel = 0;
    }

    public void updateKeyDown(SDL_Keysym keySym){
        keysPressed[keySym.scancode] = true;
        keysDown[keySym.scancode] = true;
        keyMods.set(keySym.mod);
    }

    public void updateKeyUp(SDL_Keysym keySym){
        keysPressed[keySym.scancode] = false;
        keysReleased[keySym.scancode] = true;
        keyMods.set(keySym.mod);
    }

    public void updateButtonDown(int button){
        buttonsPressed[button - 1] = true;
        buttonsDown[button - 1] = true;
    }

    public void updateButtonUp(int button){
        buttonsPressed[button - 1] = false;
        buttonsReleased[button - 1] = true;
    }

    public void updateScroll(SDL_MouseWheelEvent event){
        scrollX += event.x;
        scrollY += event.y;
    }

    public void updatePos(SDL_MouseMotionEvent event){
        this.x = event.x;
        this.y = event.y;
        this.xrel = event.xrel;
        this.yrel = event.yrel;
    }

    public void startTextInput(){
        SdlKeyboard.SDL_StartTextInput();
    }

    public void stopTextInput(){
        SdlKeyboard.SDL_StopTextInput();
    }


    // Getters

    public int getScrollX(){
        return scrollX;
    }

    public int getScroll(){
        return scrollY;
    }


    public int getDx(){
        return xrel;
    }

    public int getDy(){
        return yrel;
    }


    public void toCenter(){
        final SdlWindow window = context.window();
        setPos(window.getWidth() / 2, window.getHeight() / 2);
    }


    public Vec2i getPos(){
        return new Vec2i(x, y);
    }

    public void setPos(int x, int y){
        SdlMouse.SDL_WarpMouseInWindow(context.window().getSDL(), x, y);
    }

    public int getX(){
        return x;
    }

    public int getInvY(){
        return y;
    }

    public int getY(){
        return context.window().getHeight() - y;
    }


    public Vec2i getGlobalPos(){
        SdlMouse.SDL_GetGlobalMouseState(tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public void setGlobalPos(int x, int y){
        SdlMouse.SDL_WarpMouseGlobal(x, y);
    }

    public int getGlobalX(){
        SdlMouse.SDL_GetGlobalMouseState(tmp_int_1, null);
        return tmp_int_1.getValue();
    }

    public int getGlobalY(){
        SdlMouse.SDL_GetGlobalMouseState(null, tmp_int_2);
        return tmp_int_2.getValue();
    }


    public Vec2i getRelativeState(Vec2i ref){
        SdlMouse.SDL_GetRelativeMouseState(tmp_int_1, tmp_int_2);
        return ref.set(tmp_int_1.getValue(), tmp_int_2.getValue());
    }


    public boolean isShow(){
        return SdlMouse.SDL_ShowCursor(SdlEventsConst.SDL_QUERY) == 1;
    }

    public void setShow(boolean show){
        SdlMouse.SDL_ShowCursor(show ? 1 : 0);
    }

    public void show(){
        setShow(true);
    }

    public void hide(){
        setShow(false);
    }

    public void setRelativeMode(boolean enabled){
        SdlMouse.SDL_SetRelativeMouseMode(enabled);
    }


    public boolean isInWindow(){
        return isInBounds(0, 0, context.window().getWidth(), context.window().getHeight());
    }

    public boolean isInBounds(double x, double y, double width, double height){
        final float cursorX = getX();
        final float cursorY = getY();
        return !(cursorX < x || cursorY < y || cursorX >= x + width || cursorY >= y + height);
    }

    // public float getTouchDownX(){
    //     return touchDownX;
    // }

    // public float getTouchDownY(){
    //     return touchDownY;
    // }


    public KeyMods keyMods(){
        return keyMods;
    }


    public boolean isKeyDown(Key key){
        return keysDown[key.SDL];
    }

    public boolean isKeyPressed(Key key){
        return keysPressed[key.SDL];
    }

    public boolean isKeyReleased(Key key){
        return keysReleased[key.SDL];
    }


    public boolean anyKeyDown(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isKeyDown);
    }

    public boolean anyKeyPressed(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isKeyPressed);
    }

    public boolean anyKeyReleased(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isKeyReleased);
    }


    public boolean allKeysDown(Key... keys){
        return Arrays.stream(keys).allMatch(this::isKeyDown);
    }

    public boolean allKeysPressed(Key... keys){
        return Arrays.stream(keys).allMatch(this::isKeyPressed);
    }

    public boolean allKeysReleased(Key... keys){
        return Arrays.stream(keys).allMatch(this::isKeyReleased);
    }


    public boolean isButtonDown(Btn button){
        return buttonsDown[button.SDL - 1];
    }

    public boolean isButtonPressed(Btn button){
        return buttonsPressed[button.SDL - 1];
    }

    public boolean isButtonReleased(Btn button){
        return buttonsReleased[button.SDL - 1];
    }


    public boolean anyButtonDown(Btn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isButtonDown);
    }

    public boolean anyButtonPressed(Btn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isButtonPressed);
    }

    public boolean anyButtonReleased(Btn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isButtonReleased);
    }


    public boolean allButtonsDown(Btn... buttons){
        return Arrays.stream(buttons).allMatch(this::isButtonDown);
    }

    public boolean allButtonsPressed(Btn... buttons){
        return Arrays.stream(buttons).allMatch(this::isButtonPressed);
    }

    public boolean allButtonsReleased(Btn... buttons){
        return Arrays.stream(buttons).allMatch(this::isButtonReleased);
    }

}
