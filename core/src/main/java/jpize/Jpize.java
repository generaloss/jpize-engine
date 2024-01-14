package jpize;

import io.github.libsdl4j.api.clipboard.SdlClipboard;
import jpize.audio.AudioDeviceManager;
import jpize.app.input.ContextInput;
import jpize.sdl.window.SdlWindow;
import jpize.app.context.Context;
import jpize.app.context.ContextManager;
import jpize.app.Screen;
import jpize.util.math.vecmath.vector.Vec2f;
import jpize.sdl.Sdl;
import jpize.sdl.input.Btn;

import java.util.function.BooleanSupplier;

public class Jpize{

    private static final ContextManager contextManager = ContextManager.getInstance();
    

    public static void runContexts(){
        contextManager.run();
    }

    public static void setScreen(Screen screen){
        context().setScreen(screen);
    }


    public static Context context(){
        return contextManager.getCurrentContext();
    }

    public static SdlWindow window(){
        return context().window();
    }

    public static AudioDeviceManager audio(){
        return AudioDeviceManager.getInstance();
    }


    public static boolean isTouchDown(){
        return input().anyButtonDown(Btn.LEFT, Btn.MIDDLE, Btn.RIGHT);
    }

    public static boolean isTouched(){
        return input().anyButtonPressed(Btn.LEFT, Btn.MIDDLE, Btn.RIGHT);
    }

    public static boolean isTouchReleased(){
        return input().anyButtonReleased(Btn.LEFT, Btn.MIDDLE, Btn.RIGHT);
    }


    public static int getWidth(){
        return window().getWidth();
    }

    public static int getHeight(){
        return window().getHeight();
    }

    public static float getAspect(){
        return window().aspect();
    }


    public static ContextInput input(){
        return context().input();
    }


    public static int getX(){
        return input().getX();
    }

    public static int getY(){
        return input().getY();
    }

    public static int getInvY(){
        return input().getInvY();
    }

    public static Vec2f getCursorPos(){
        return new Vec2f(getX(), getY());
    }


    public static void setVsync(boolean vsync){
        Sdl.enableVsync(vsync);
    }

    public static int getFPS(){
        return contextManager.getFPS();
    }

    public static float getDt(){
        return contextManager.getDeltaTime();
    }


    public static String getClipboardText(){
        return SdlClipboard.SDL_GetClipboardText();
    }
    
    public static void setClipboardText(String text){
        SdlClipboard.SDL_SetClipboardText(text);
    }


    public static void execSync(Runnable runnable){
        contextManager.getSyncTaskExecutor().exec(runnable);
    }

    public static void execIf(Runnable runnable, BooleanSupplier condition){
        contextManager.getSyncTaskExecutor().execIf(runnable, condition);
    }


    public static void closeWindow(){
        context().dispose();
    }

    public static void closeAllWindows(){
        contextManager.closeAllWindows();
    }

    public static void closeOtherWindows(){
        contextManager.closeOtherWindows();
    }

    public static void exitOnWindowsClosed(boolean exitWhenNoWindows){
        contextManager.exitWhenNoWindows(exitWhenNoWindows);
    }

    public static void exit(){
        contextManager.exit();
    }

}
