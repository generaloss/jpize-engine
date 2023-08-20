package pize.lib.glfw.window;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;
import pize.app.Disposable;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;
import pize.lib.glfw.GlfwObject;
import pize.lib.glfw.cursor.GlfwCursor;
import pize.lib.glfw.glfwenum.GlfwHint;
import pize.lib.glfw.glfwenum.GlfwInputMode;
import pize.lib.glfw.monitor.GlfwMonitor;
import pize.lib.glfw.window.callback.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class GlfwWindow extends GlfwObject implements Disposable{

    public void create(int width, int height, CharSequence title, long monitor, long share){
        ID = glfwCreateWindow(width, height, title, monitor, share);
        if(ID == 0L)
            throw new Error("Failed to create the GLFW Window");
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

    public void swapBuffers(){
        glfwSwapBuffers(ID);
    }

    public boolean closeRequest(){
        return glfwWindowShouldClose(ID);
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


    public void setInputMode(GlfwInputMode inputMode, int value){
        glfwSetInputMode(ID, inputMode.GLFW, value);
    }

    public int getInputMode(GlfwInputMode inputMode){
        return glfwGetInputMode(ID, inputMode.GLFW);
    }

    public long getMonitorID(){
        return glfwGetWindowMonitor(ID);
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


    public void setCursor(GlfwCursor glfwCursor){
        glfwSetCursor(ID, glfwCursor != null ? glfwCursor.getID() : 0);
    }


    public void setIcon(Pixmap pixmap){
        final GLFWImage image = GLFWImage.malloc();
        final GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
        image.set(pixmap.getWidth(), pixmap.getHeight(), pixmap.getBuffer());

        iconBuffer.put(0, image);
        glfwSetWindowIcon(ID, iconBuffer);
    }

    public void setIcon(String filepath){
        this.setIcon(PixmapIO.load(filepath));
    }


    public void setFileDropCallback(GlfwWindowDropCallback callback){
        glfwSetDropCallback(ID, (long windowID, int count, long names)->{
            final PointerBuffer nameBuffer = MemoryUtil.memPointerBuffer(names, count);

            final String[] files = new String[count];
            for(int i = 0; i < count; i++)
                files[i] = MemoryUtil.memUTF8(MemoryUtil.memByteBufferNT1(nameBuffer.get(i)));

            callback.invoke(count, files);
        });
    }

    public void setFocusCallback(GlfwWindowFocusCallback callback){
        glfwSetWindowFocusCallback(ID, (long windowID, boolean focus) -> callback.invoke(focus));
    }

    public void setPosCallback(GlfwWindowPosCallback callback){
        glfwSetWindowPosCallback(ID, (long windowID, int x, int y) -> callback.invoke(x, y));
    }

    public void setSizeCallback(GlfwWindowSizeCallback callback){
        glfwSetWindowSizeCallback(ID, (long windowID, int width, int height) -> callback.invoke(width, height));
    }

    public void setCloseCallback(GlfwWindowCloseCallback callback){
        glfwSetWindowCloseCallback(ID, (long windowID) -> callback.invoke());
    }

    public void setIconifyCallback(GlfwWindowIconifyCallback callback){
        glfwSetWindowIconifyCallback(ID, (long windowID, boolean iconify) -> callback.invoke(iconify));
    }

    public void setMaximizeCallback(GlfwWindowMaximizeCallback callback){
        glfwSetWindowMaximizeCallback(ID, (long windowID, boolean maximize) -> callback.invoke(maximize));
    }

    public void setRefreshCallback(GlfwWindowRefreshCallback callback){
        glfwSetWindowRefreshCallback(ID, (long windowID) -> callback.invoke());
    }

    public void setContentScaleCallback(GlfwWindowContentScaleCallback callback){
        glfwSetWindowContentScaleCallback(ID, (long windowID, float contentScaleX, float contentScaleY) -> callback.invoke(contentScaleX, contentScaleY));
    }


    @Override
    public void dispose(){
        glfwFreeCallbacks(ID);
        glfwDestroyWindow(ID);
    }

}
