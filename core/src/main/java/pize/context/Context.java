package pize.context;

import pize.audio.Audio;
import pize.graphics.gl.Gl;
import pize.graphics.texture.CubeMap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureArray;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.ScreenQuadShader;
import pize.graphics.util.Shader;
import pize.graphics.util.TextureUtils;
import pize.graphics.vertex.ElementBuffer;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexBuffer;
import pize.io.Keyboard;
import pize.io.Mouse;
import pize.io.Window;
import pize.util.Utils;
import pize.util.time.DeltaTimeCounter;
import pize.util.time.PerSecCounter;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Context{

    private final Audio audio;
    private final Window window;
    private final Keyboard keyboard;
    private final Mouse mouse;

    private final PerSecCounter fpsCounter;
    private final DeltaTimeCounter deltaTimeCounter;

    private Screen screen;

    private int lastWidth, lastHeight;
    private boolean exitRequest;

    public Context(Window window, Keyboard keyboard, Mouse mouse){
        audio = new Audio();
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;

        fpsCounter = new PerSecCounter();
        fpsCounter.count();
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

            fpsCounter.count();
            deltaTimeCounter.update();

            glfwPollEvents();

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
    
        window.dispose();
        audio.dispose();
        listener.dispose();

        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(TextureUtils.class, "dispose");

        glfwTerminate();
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
