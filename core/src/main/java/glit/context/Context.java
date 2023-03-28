package glit.context;

import glit.audio.Audio;
import glit.graphics.gl.Gl;
import glit.graphics.postprocess.FrameBufferObject;
import glit.graphics.postprocess.RenderBufferObject;
import glit.graphics.texture.CubeMap;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureArray;
import glit.graphics.util.*;
import glit.graphics.vertex.ElementBuffer;
import glit.graphics.vertex.VertexArray;
import glit.graphics.vertex.VertexBuffer;
import glit.io.Keyboard;
import glit.io.Mouse;
import glit.io.Window;
import glit.util.Utils;
import glit.util.time.DeltaTimeCounter;
import glit.util.time.FpsCounter;
import org.lwjgl.glfw.GLFW;

public class Context{

    private final Audio audio;
    private final Window window;
    private final Keyboard keyboard;
    private final Mouse mouse;

    private final FpsCounter fpsCounter;
    private final DeltaTimeCounter deltaTimeCounter;

    private Screen screen;

    private int lastWidth, lastHeight;
    private boolean exitRequest;

    public Context(Window window, Keyboard keyboard, Mouse mouse){
        audio = new Audio();
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;

        fpsCounter = new FpsCounter();
        fpsCounter.update();
        deltaTimeCounter = new DeltaTimeCounter();
        deltaTimeCounter.update();
    }


    public void begin(ContextListener listener){
        listener.init();

        window.show();

        while(!window.closeRequest() && !exitRequest){
            int width = window.getWidth();
            int height = window.getHeight();
            if(lastWidth != width || lastHeight != height){
                lastWidth = width;
                lastHeight = height;

                Gl.setViewport(width, height);
                listener.resize(width, height);
            }

            fpsCounter.update();
            deltaTimeCounter.update();

            window.pollEvents();

            if(screen != null)
                screen.render();
            listener.render();

            mouse.reset();
            keyboard.reset();
            window.swapBuffers();
        }
    
        Shader.unbind();
        VertexArray.unbind();
        VertexBuffer.unbind();
        ElementBuffer.unbind();
        Texture.unbind();
        CubeMap.unbind();
        TextureArray.unbind();
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();
    
        window.dispose();
        audio.dispose();
        listener.dispose();

        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(TextureUtils.class, "dispose");

        GLFW.glfwTerminate();
    }

    public void setScreen(Screen screen){
        this.screen.hide();
        this.screen = screen;
        screen.show();
    }


    public int getFps(){
        return fpsCounter.get();
    }

    public DeltaTimeCounter getDeltaTime(){
        return deltaTimeCounter;
    }


    public Audio getAudio(){
        return audio;
    }

    public Window getWindow(){
        return window;
    }

    public Keyboard getKeyboard(){
        return keyboard;
    }

    public Mouse getMouse(){
        return mouse;
    }


    public void exit(){
        exitRequest = true;
    }

}
