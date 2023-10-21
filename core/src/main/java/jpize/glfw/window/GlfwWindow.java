package jpize.glfw.window;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;
import jpize.glfw.glfw.GlfwHint;
import jpize.glfw.keyboard.GlfwKeyboard;
import jpize.glfw.monitor.GlfwMonitor;
import jpize.glfw.mouse.GlfwMouse;
import jpize.glfw.object.GlfwObjectLong;
import jpize.glfw.window.callback.*;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.PixmapIO;
import jpize.io.MonitorManager;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec2i;
import jpize.util.Disposable;
import jpize.util.Utils;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class GlfwWindow extends GlfwObjectLong implements Disposable{

    private static final int[] tmp_x_int = new int[1];
    private static final int[] tmp_y_int = new int[1];
    private static final int[] tmp_z_int = new int[1];
    private static final int[] tmp_w_int = new int[1];
    private static final float[] tmp_x_float = new float[1];
    private static final float[] tmp_y_float = new float[1];


    private GLCapabilities capabilities;
    private GlfwMouse glfwMouse;
    private GlfwKeyboard glfwKeyboard;


    public void create(int width, int height, CharSequence title, long monitor, long share){
        ID = glfwCreateWindow(width, height, title, monitor, share);
        if(ID == 0L)
            throw new Error("Failed to create the GLFW Window");

        glfwMouse = new GlfwMouse(this);
        glfwKeyboard = new GlfwKeyboard(this);
    }

    public GLCapabilities getCapabilities(){
        return capabilities;
    }

    public void createCapabilities(){
        capabilities = GL.createCapabilities();
    }

    public GlfwMouse getGlfwMouse(){
        return glfwMouse;
    }

    public GlfwKeyboard getGlfwKeyboard(){
        return glfwKeyboard;
    }


    public void setPos(int x, int y){
        glfwSetWindowPos(ID, x, y);
    }

    public void setSize(int width, int height){
        glfwSetWindowSize(ID, width, height);
    }

    public void makeCurrent(){
        glfwMakeContextCurrent(ID);
    }

    public void setAttribute(GlfwHint attribute, int value){
        glfwSetWindowAttrib(ID, attribute.GLFW, value);
    }

    public void setAttribute(GlfwHint attribute, boolean value){
        glfwSetWindowAttrib(ID, attribute.GLFW, value ? GLFW_TRUE : GLFW_FALSE);
    }

    public int getAttribute(GlfwHint attribute){
        return glfwGetWindowAttrib(ID, attribute.GLFW);
    }

    public void swapBuffers(){
        glfwSwapBuffers(ID);
    }

    public boolean closeRequest(){
        return glfwWindowShouldClose(ID);
    }

    public void setShouldClose(boolean shouldClose){
        glfwSetWindowShouldClose(ID, shouldClose);
    }


    public void show(){
        glfwShowWindow(ID);
    }

    public void hide(){
        glfwHideWindow(ID);
    }

    public void focus(){
        glfwFocusWindow(ID);
    }

    public void iconify(){
        glfwIconifyWindow(ID);
    }

    public void maximize(){
        glfwMaximizeWindow(ID);
    }

    public void restore(){
        glfwRestoreWindow(ID);
    }

    public void requestAttention(){
        glfwRequestWindowAttention(ID);
    }


    public void setSizeLimits(int minWidth, int minHeight, int maxWidth, int maxHeight){
        glfwSetWindowSizeLimits(ID, minWidth, minHeight, maxWidth, maxHeight);
    }


    public Insets getFrameSize(){
        glfwGetWindowFrameSize(ID, tmp_x_int, tmp_y_int, tmp_z_int, tmp_w_int);
        return new Insets(tmp_y_int[0], tmp_x_int[0], tmp_w_int[0], tmp_z_int[0]);
    }


    public Vec2i getPos(){
        glfwGetWindowPos(ID, tmp_x_int, tmp_y_int);
        return new Vec2i(tmp_x_int[0], tmp_y_int[0]);
    }

    public int getX(){
        glfwGetWindowPos(ID, tmp_x_int, null);
        return tmp_x_int[0];
    }

    public int getY(){
        glfwGetWindowPos(ID, null, tmp_y_int);
        return tmp_y_int[0];
    }


    public Vec2i getSize(){
        glfwGetWindowSize(ID, tmp_x_int, tmp_y_int);
        return new Vec2i(tmp_x_int[0], tmp_y_int[0]);
    }

    public int getWidth(){
        glfwGetWindowSize(ID, tmp_x_int, null);
        return tmp_x_int[0];
    }

    public int getHeight(){
        glfwGetWindowSize(ID, null, tmp_y_int);
        return tmp_y_int[0];
    }


    public Vec2i getFramebufferSize(){
        glfwGetFramebufferSize(ID, tmp_x_int, tmp_y_int);
        return new Vec2i(tmp_x_int[0], tmp_y_int[0]);
    }

    public int getFramebufferWidth(){
        glfwGetFramebufferSize(ID, tmp_x_int, null);
        return tmp_x_int[0];
    }

    public int getFramebufferHeight(){
        glfwGetFramebufferSize(ID, null, tmp_y_int);
        return tmp_y_int[0];
    }


    public Vec2f getContentScale(){
        glfwGetWindowContentScale(ID, tmp_x_float, tmp_y_float);
        return new Vec2f(tmp_x_float[0], tmp_y_float[0]);
    }

    public float getContentScaleX(){
        glfwGetWindowContentScale(ID, tmp_x_float, null);
        return tmp_x_float[0];
    }

    public float getContentScaleY(){
        glfwGetWindowContentScale(ID, null, tmp_y_float);
        return tmp_y_float[0];
    }


    public void setOpacity(float opacity){
        glfwSetWindowOpacity(ID, opacity);
    }

    public void setMonitor(GlfwMonitor monitor, int x, int y, int width, int height){
        glfwSetWindowMonitor(ID, monitor.getID(), x, y, width, height, monitor.getRefreshRate());
    }

    public void setAspect(int numerator, int denominator){
        glfwSetWindowAspectRatio(ID, numerator, denominator);
    }

    public void setTitle(CharSequence title){
        glfwSetWindowTitle(ID, title);
    }


    public long getMonitorID(){
        return glfwGetWindowMonitor(ID);
    }

    public GlfwMonitor getMonitor(){
        return MonitorManager.getMonitor(this.getMonitorID());
    }


    public String getClipboardString(){
        return glfwGetClipboardString(ID);
    }

    public void setClipboardString(CharSequence charSequence){
        glfwSetClipboardString(ID, charSequence);
    }

    public void setClipboardString(ByteBuffer buffer){
        glfwSetClipboardString(ID, buffer);
    }


    public void setFullscreen(GlfwMonitor monitor){
        glfwSetWindowMonitor(ID, monitor.getID(), 0, 0, monitor.getWidth(), monitor.getHeight(), monitor.getRefreshRate());
    }

    public void setWindowed(int x, int y, int width, int height, int refreshRate){
        glfwSetWindowMonitor(ID, 0L, x, y, width, height, refreshRate);
    }


    public void setIcon(PixmapRGBA pixmap){
        final GLFWImage image = GLFWImage.malloc();
        final GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
        image.set(pixmap.getWidth(), pixmap.getHeight(), pixmap.getBuffer());

        iconBuffer.put(0, image);
        glfwSetWindowIcon(ID, iconBuffer);
    }

    public void setIcon(String filepath){
        this.setIcon(PixmapIO.load(filepath));
    }


    private AutoCloseable
            fileDropCallback, focusCallback, posCallback, sizeCallback, closeCallback,
            iconifyCallback, maximizeCallback, refreshCallback, contentScaleCallback;

    public void setFileDropCallback(GlfwWindowDropCallback callback){
        Utils.close(fileDropCallback);
        fileDropCallback = glfwSetDropCallback(ID, (long windowID, int count, long names)->{
            final PointerBuffer nameBuffer = MemoryUtil.memPointerBuffer(names, count);

            final String[] files = new String[count];
            for(int i = 0; i < count; i++)
                files[i] = MemoryUtil.memUTF8(MemoryUtil.memByteBufferNT1(nameBuffer.get(i)));

            callback.invoke(count, files);
        });
    }

    public void setFocusCallback(GlfwWindowFocusCallback callback){
        Utils.close(focusCallback);
        focusCallback = glfwSetWindowFocusCallback(ID, (long windowID, boolean focus) -> callback.invoke(focus));
    }

    public void setPosCallback(GlfwWindowPosCallback callback){
        Utils.close(posCallback);
        posCallback = glfwSetWindowPosCallback(ID, (long windowID, int x, int y) -> callback.invoke(x, y));
    }

    public void setSizeCallback(GlfwWindowSizeCallback callback){
        Utils.close(sizeCallback);
        sizeCallback = glfwSetWindowSizeCallback(ID, (long windowID, int width, int height) -> callback.invoke(width, height));
    }

    public void setCloseCallback(GlfwWindowCloseCallback callback){
        Utils.close(closeCallback);
        closeCallback = glfwSetWindowCloseCallback(ID, (long windowID) -> callback.invoke());
    }

    public void setIconifyCallback(GlfwWindowIconifyCallback callback){
        Utils.close(iconifyCallback);
        iconifyCallback = glfwSetWindowIconifyCallback(ID, (long windowID, boolean iconify) -> callback.invoke(iconify));
    }

    public void setMaximizeCallback(GlfwWindowMaximizeCallback callback){
        Utils.close(maximizeCallback);
        maximizeCallback = glfwSetWindowMaximizeCallback(ID, (long windowID, boolean maximize) -> callback.invoke(maximize));
    }

    public void setRefreshCallback(GlfwWindowRefreshCallback callback){
        Utils.close(refreshCallback);
        refreshCallback = glfwSetWindowRefreshCallback(ID, (long windowID) -> callback.invoke());
    }

    public void setContentScaleCallback(GlfwWindowContentScaleCallback callback){
        Utils.close(contentScaleCallback);
        contentScaleCallback = glfwSetWindowContentScaleCallback(ID, (long windowID, float contentScaleX, float contentScaleY) -> callback.invoke(contentScaleX, contentScaleY));
    }

    @Override
    public void dispose(){
        glfwFreeCallbacks(ID);
        glfwDestroyWindow(ID);
    }

}
