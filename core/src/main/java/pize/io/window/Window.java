package pize.io.window;

import pize.io.monitor.MonitorManager;
import pize.lib.glfw.Glfw;
import pize.lib.glfw.glfwenum.GlfwHint;
import pize.lib.glfw.monitor.GlfwMonitor;
import pize.lib.glfw.window.GlfwWindow;
import pize.lib.glfw.window.callback.GlfwWindowSizeCallback;

import java.util.ArrayList;
import java.util.List;

public class Window extends GlfwWindow{

    private int width, height, x, y, windowedWidth, windowedHeight, windowedX, windowedY;
    private boolean vsync, fullscreen, focused, resizable;
    private String title;
    private final List<GlfwWindowSizeCallback> sizeCallbackList;
    private float contentScaleX, contentScaleY;
    
    public Window(String title, int width, int height){
        this(title, width, height, true, true, 1);
    }
    
    public Window(String title, int width, int height, boolean resizable, boolean vsync, int samples){
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.vsync = vsync;

        Glfw.windowHint(GlfwHint.VISIBLE, 0);
        Glfw.windowHint(GlfwHint.RESIZABLE, resizable ? 1 : 0);
        Glfw.windowHint(GlfwHint.SAMPLES, samples);

        super.create(width, height, title, 0, 0);

        final GlfwMonitor monitor = MonitorManager.getPrimary();
        final int windowX = monitor.getWidth() / 2 - width / 2;
        final int windowY = monitor.getHeight() / 2 - height / 2;

        super.setPos(windowX, windowY);
        super.makeCurrent();
        Glfw.setVsync(vsync);

        super.setFocusCallback(flag -> focused = flag);

        super.setPosCallback((x, y) -> {
            this.x = x;
            this.y = y;
        });

        super.setContentScaleCallback((scaleX, scaleY) -> {
            contentScaleX = scaleX;
            contentScaleY = scaleY;
        });
        
        sizeCallbackList = new ArrayList<>();

        super.setSizeCallback((w, h) -> {
            this.width = w;
            this.height = h;
            
            for(GlfwWindowSizeCallback sizeCallback: sizeCallbackList)
                sizeCallback.invoke(w, h);
        });
    }


    public boolean isFullscreen(){
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen){
        if(fullscreen == this.fullscreen)
            return;

        this.fullscreen = fullscreen;
        
        final GlfwMonitor monitor = MonitorManager.getPrimary();
        if(fullscreen){
            windowedX = x;
            windowedY = y;
            windowedWidth = width;
            windowedHeight = height;
            super.setFullscreen(monitor);
        }else
            super.setWindowed(windowedX, windowedY, windowedWidth, windowedHeight, monitor.getRefreshRate());
    }

    public void toggleFullscreen(){
        setFullscreen(!fullscreen);
    }


    public boolean isVsync(){
        return vsync;
    }

    public void setVsync(boolean vsync){
        if(vsync == this.vsync)
            return;

        this.vsync = vsync;
        Glfw.setVsync(vsync);
    }

    public void toggleVsync(){
        setVsync(!vsync);
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        super.setTitle(title);
    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public boolean isResizable(){
        return resizable;
    }

    public void setResizable(boolean resizable){
        this.resizable = resizable;
        super.setAttribute(GlfwHint.RESIZABLE, resizable ? 1 : 0);
    }

    public void toggleResizable(){
        setResizable(!resizable);
    }

    public boolean isFocused(){
        return focused;
    }

    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public float aspect(){
        return (float) width / height;
    }


    public void addSizeCallback(GlfwWindowSizeCallback sizeCallback){
        sizeCallbackList.add(sizeCallback);
    }
    
    public void removeSizeCallback(GlfwWindowSizeCallback sizeCallback){
        sizeCallbackList.remove(sizeCallback);
    }

}
