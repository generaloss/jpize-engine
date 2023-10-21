package jpize.io;

import org.lwjgl.opengl.GL;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.texture.GlBlendFactor;
import jpize.glfw.glfw.Glfw;
import jpize.glfw.glfw.GlfwHint;
import jpize.glfw.monitor.GlfwMonitor;
import jpize.glfw.window.GlfwWindow;

public class Window extends GlfwWindow{

    private final Mouse mouse;
    private final Keyboard keyboard;

    private int windowedWidth, windowedHeight, windowedX, windowedY;
    private boolean fullscreen, focused;

    public Window(int width, int height, String title){
        this(width, height, title, true, null);
    }

    public Window(int width, int height, String title, Window shared){
        this(width, height, title, true, shared);
    }
    
    public Window(int width, int height, String title, boolean vsync, Window shared){
        // Create
        super.create(width, height, title, 0, shared == null ? 0 : shared.ID);

        // Capabilities
        super.makeCurrent();
        this.initContextGL();
        super.createCapabilities();
        Glfw.setVsync(vsync);

        // Input
        this.mouse = new Mouse(super.getGlfwMouse());
        this.keyboard = new Keyboard(super.getGlfwKeyboard());

        // Focus
        super.setFocusCallback(flag -> focused = flag);
    }

    private void initContextGL(){
        GL.createCapabilities();
        Gl.enable(GlTarget.BLEND, GlTarget.CULL_FACE);
        Gl.blendFunc(GlBlendFactor.SRC_ALPHA, GlBlendFactor.ONE_MINUS_SRC_ALPHA);
    }

    public void toCenter(){
        final GlfwMonitor monitor = MonitorManager.getPrimary();
        final int windowX = monitor.getWidth()  / 2 - super.getWidth()  / 2;
        final int windowY = monitor.getHeight() / 2 - super.getHeight() / 2;
        super.setPos(windowX, windowY);
    }


    public boolean isResizable(){
        return super.getAttribute(GlfwHint.RESIZABLE) == 1;
    }

    public void setResizable(boolean resizable){
        super.setAttribute(GlfwHint.RESIZABLE, resizable ? 1 : 0);
    }


    public boolean isFocused(){
        return focused;
    }

    public float aspect(){
        return (float) super.getWidth() / super.getHeight();
    }


    public Mouse getMouse(){
        return mouse;
    }

    public Keyboard getKeyboard(){
        return keyboard;
    }


    public boolean isFullscreen(){
        return fullscreen;
    }

    public void toggleFullscreen(){
        setFullscreen(!fullscreen);
    }

    public void setFullscreen(boolean fullscreen){
        if(fullscreen == this.fullscreen)
            return;

        this.fullscreen = fullscreen;

        final GlfwMonitor monitor = MonitorManager.getPrimary();
        if(fullscreen){
            windowedX = super.getX();
            windowedY = super.getY();
            windowedWidth = super.getWidth();
            windowedHeight = super.getHeight();
            super.setFullscreen(monitor);
        }else
            super.setWindowed(windowedX, windowedY, windowedWidth, windowedHeight, monitor.getRefreshRate());
    }

}
