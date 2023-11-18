package jpize.io;

import jpize.sdl.Sdl;
import jpize.sdl.gl.SdlGlContext;
import jpize.sdl.window.SdlWinFlags;
import jpize.sdl.window.SdlWindow;

public class Window extends SdlWindow{

    private SdlGlContext glContext;

    private int windowedWidth, windowedHeight, windowedX, windowedY;
    private boolean fullscreen, focused;


    public Window(String title, int width, int height, boolean vsync){
        super(title, width, height, SdlWinFlags.DEFAULT);
        initContextGL(vsync);
    }

    public Window(String title, int width, int height){
        this(title, width, height, true);
    }

    public Window(String title, int width, int height, boolean vsync, SdlWinFlags flags){
        super(title, width, height, flags);
        initContextGL(vsync);
    }
    
    public Window(String title, int x, int y, int width, int height, boolean vsync, SdlWinFlags flags){
        super(title, x, y, width, height, flags);
        initContextGL(vsync);
    }


    private void initContextGL(boolean vsync){
        glContext = new SdlGlContext(this);
        glContext.makeCurrent();

        Sdl.enableVsync(vsync);
    }

    public SdlGlContext getGlContext(){
        return glContext;
    }


    @Override
    public void dispose(){
        glContext.dispose();
        super.dispose();
    }

}
