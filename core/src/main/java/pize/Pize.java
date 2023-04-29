package pize;

import pize.audio.Audio;
import pize.context.Context;
import pize.context.ContextListener;
import pize.context.Screen;
import pize.graphics.gl.BlendFactor;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.io.*;
import pize.io.glfw.Key;
import pize.math.vecmath.point.Point2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Pize{

    private static Context context;

    public static void create(String title, int width, int height, boolean resizable, boolean vsync, int samples){
        GLFWErrorCallback.createPrint(System.err).set();
        GLFW.glfwInit();

        Window window = new Window(title, width, height, resizable, vsync, samples);
        context = new Context(window, new Keyboard(window), new Mouse(window));

        Gl.enable(Target.BLEND, Target.CULL_FACE);
        Gl.blendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
    }
    
    public static void create(String title, int width, int height){
        create(title, width, height, true, true, 0);
    }

    public static void run(ContextListener listener){
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


    public static boolean isDown(Key key){
        return keyboard().isDown(key);
    }

    public static boolean isPressed(Key key){
        return keyboard().isPressed(key);
    }

    public static boolean isReleased(Key key){
        return keyboard().isReleased(key);
    }


    public static boolean isDown(Key... keys){
        return keyboard().isDown(keys);
    }

    public static boolean isPressed(Key... keys){
        return keyboard().isPressed(keys);
    }

    public static boolean isReleased(Key... keys){
        return keyboard().isReleased(keys);
    }


    public static boolean isDownAll(Key... keys){
        return keyboard().isDownAll(keys);
    }

    public static boolean isPressedAll(Key... keys){
        return keyboard().isPressedAll(keys);
    }

    public static boolean isReleasedAll(Key... keys){
        return keyboard().isReleasedAll(keys);
    }


    public static boolean isTouchDown(){
        return mouse().isLeftDown() || mouse().isRightDown();
    }

    public static boolean isTouched(){
        return mouse().isLeftPressed() || mouse().isRightPressed();
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

    public static Point2f getCursorPos(){
        return new Point2f(getX(), getY());
    }


    public static int getFPS(){
        return context.getFps();
    }

    public static float getDeltaTime(){
        return context.getDeltaTime().get();
    }


    public static void exit(){
        context.exit();
    }

}
