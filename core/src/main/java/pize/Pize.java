package pize;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import pize.app.AppAdapter;
import pize.app.Context;
import pize.app.Screen;
import pize.audio.Audio;
import pize.graphics.gl.BlendFactor;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.io.keyboard.Keyboard;
import pize.io.monitor.Monitor;
import pize.io.monitor.MonitorManager;
import pize.io.mouse.Mouse;
import pize.io.window.Window;
import pize.math.vecmath.vector.Vec2f;

public class Pize{

    private static Context context;

    public static void create(String title, int width, int height, boolean resizable, boolean vsync, int samples){
        GLFWErrorCallback.createPrint(System.err).set();
        GLFW.glfwInit();

        final Window window = new Window(title, width, height, resizable, vsync, samples);
        context = new Context(window, new Keyboard(window), new Mouse(window));

        Gl.enable(Target.BLEND, Target.CULL_FACE);
        Gl.blendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
    }
    
    public static void create(String title, int width, int height){
        create(title, width, height, true, true, 0);
    }

    public static void run(AppAdapter listener){
        context.begin(listener);
    }

    public static void setScreen(Screen screen){
        context.setScreen(screen);
    }


    public static Context context(){
        return context;
    }

    public static Window window(){
        return context.getWindow();
    }

    public static Keyboard keyboard(){
        return context.getKeyboard();
    }

    public static Mouse mouse(){
        return context.getMouse();
    }

    public static Monitor monitor(){
        return MonitorManager.getPrimary();
    }

    public static Audio audio(){
        return context.getAudio();
    }

    
    public static boolean isTouchDown(){
        return mouse().isLeftDown() || mouse().isRightDown();
    }

    public static boolean isTouched(){
        return mouse().isLeftPressed() || mouse().isMiddlePressed() || mouse().isRightPressed();
    }

    public static boolean isTouchReleased(){
        return mouse().isLeftReleased() || mouse().isRightReleased();
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


    public static int getX(){
        return mouse().getX();
    }

    public static int getY(){
        return window().getHeight() - mouse().getY();
    }

    public static Vec2f getCursorPos(){
        return new Vec2f(getX(), getY());
    }


    public static int getFPS(){
        return context.getFps();
    }
    
    public static float getDt(){
        return context.getRenderDeltaTime();
    }
    
    public static float getUpdateDt(){
        return context.getFixedUpdateDeltaTime();
    }
    
    public static void setUpdateTPS(float updateTPS){
        context.setUpdateTPS(updateTPS);
    }
    
    
    public static void execSync(Runnable runnable){
        context.execSync(runnable);
    }


    public static void exit(){
        context.exit();
    }

}
