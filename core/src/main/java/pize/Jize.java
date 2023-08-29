package pize;

import pize.audio.Audio;
import pize.glfw.glfw.Glfw;
import pize.glfw.key.MBtn;
import pize.glfw.monitor.GlfwMonitor;
import pize.io.Keyboard;
import pize.io.MonitorManager;
import pize.io.Mouse;
import pize.io.Window;
import pize.io.context.Context;
import pize.io.context.ContextManager;
import pize.io.context.Screen;
import pize.math.vecmath.vector.Vec2f;

import java.util.function.BooleanSupplier;

public class Jize{

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

    public static Window window(){
        return context().getWindow();
    }

    public static Keyboard keyboard(){
        return context().getKeyboard();
    }

    public static Mouse mouse(){
        return context().getMouse();
    }

    public static GlfwMonitor monitor(){
        return MonitorManager.getMonitor(window().getMonitorID());
    }

    public static GlfwMonitor primaryMonitor(){
        return MonitorManager.getPrimary();
    }

    public static Audio audio(){
        return Audio.getInstance();
    }


    public static boolean isTouchDown(){
        return mouse().anyDown(MBtn.LEFT, MBtn.MIDDLE, MBtn.RIGHT);
    }

    public static boolean isTouched(){
        return mouse().anyPressed(MBtn.LEFT, MBtn.MIDDLE, MBtn.RIGHT);
    }

    public static boolean isTouchReleased(){
        return mouse().anyReleased(MBtn.LEFT, MBtn.MIDDLE, MBtn.RIGHT);
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


    public static float getX(){
        return mouse().getX();
    }

    public static float getY(){
        return window().getHeight() - mouse().getY();
    }

    public static float getInvY(){
        return mouse().getY();
    }

    public static Vec2f getCursorPos(){
        return new Vec2f(getX(), getY());
    }


    public static void setVsync(boolean vsync){
        Glfw.setVsync(vsync);
    }

    public static int getFPS(){
        return contextManager.getFPS();
    }

    public static float getDt(){
        return contextManager.getDeltaTime();
    }


    public static String getClipboardString(){
        return window().getClipboardString();
    }
    
    public static void setClipboardString(CharSequence charSequence){
        window().setClipboardString(charSequence);
    }


    public static void execSync(Runnable runnable){
        contextManager.getSyncTaskExecutor().exec(runnable);
    }

    public static void execIf(Runnable runnable, BooleanSupplier condition){
        contextManager.getSyncTaskExecutor().execIf(runnable, condition);
    }

    public static float getFixedUpdateDt(){
        return context().getFixedUpdate().getDeltaTime();
    }

    public static void startFixedUpdate(float tps){
        context().startFixedUpdate(tps);
    }


    public static void closeWindow(){
        context().getWindow().setShouldClose(true);
    }

    public static void closeAllWindows(){
        contextManager.closeAllWindows();
    }

    public static void exitWhenNoWindows(boolean exitWhenNoWindows){
        contextManager.exitWhenNoWindows(exitWhenNoWindows);
    }

    public static void exit(){
        contextManager.exit();
    }

}
