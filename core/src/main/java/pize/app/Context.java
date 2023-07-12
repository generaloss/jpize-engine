package pize.app;

import pize.audio.Audio;
import pize.audio.util.TaskExecutor;
import pize.graphics.gl.Gl;
import pize.graphics.texture.CubeMap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureArray;
import pize.graphics.util.*;
import pize.graphics.vertex.ElementBuffer;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexBuffer;
import pize.io.keyboard.Keyboard;
import pize.io.mouse.Mouse;
import pize.io.window.Window;
import pize.util.Utils;
import pize.util.time.DeltaTimeCounter;
import pize.util.time.PerSecCounter;
import pize.util.time.TickGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Context{

    private final Audio audio;
    private final Window window;
    private final Keyboard keyboard;
    private final Mouse mouse;

    private final PerSecCounter fpsCounter;
    private final DeltaTimeCounter renderDeltaTime, fixedUpdateDeltaTime;
    
    private final ExecutorService fixedUpdateExecutor;
    private TickGenerator fixedUpdateGenerator;
    private float initialUpdateTPS;

    private Screen screen;
    private boolean exitRequest;
    private final TaskExecutor syncTaskExecutor;

    public Context(Window window, Keyboard keyboard, Mouse mouse){
        this.audio = new Audio();
        this.window = window;
        this.keyboard = keyboard;
        this.mouse = mouse;

        this.fpsCounter = new PerSecCounter();
        this.fpsCounter.count();
        this.renderDeltaTime = new DeltaTimeCounter();
        this.renderDeltaTime.update();
        
        this.fixedUpdateExecutor = Executors.newFixedThreadPool(3);
        this.fixedUpdateDeltaTime = new DeltaTimeCounter();
        
        this.syncTaskExecutor = new TaskExecutor();
    }


    public void begin(AppAdapter listener){
        listener.init(); /* INIT */
        
        // Window initialization
        window.show();
        window.addSizeCallback((int width, int height)->{
            listener.resize(width, height);
            Gl.viewport(width, height);
        });
        
        // Fixed update
        if(initialUpdateTPS != 0){
            fixedUpdateGenerator = new TickGenerator(initialUpdateTPS);
            
            fixedUpdateGenerator.startAsync(()->{
                fixedUpdateDeltaTime.update();
                fixedUpdateExecutor.submit(listener::fixedUpdate); /* FIXED UPDATE */
            });
        }
        
        // Render loop
        while(!window.closeRequest()){
            if(exitRequest)
                break;
            
            render(listener);
            
            // Pize.syncExec() tasks
            syncTaskExecutor.executeTasks();
        }
        
        // Stop fixed update
        if(fixedUpdateGenerator != null){
            fixedUpdateGenerator.stop();
            fixedUpdateExecutor.shutdownNow();
        }
    
        // Unbind OGL objects
        Shader.unbind();
        VertexArray.unbind();
        VertexBuffer.unbind();
        ElementBuffer.unbind();
        Texture.unbind();
        CubeMap.unbind();
        TextureArray.unbind();
    
        // Dispose
        window.dispose();
        audio.dispose();
        listener.dispose(); /* DISPOSE */
        
        // Static dispose methods
        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(TextureUtils.class, "dispose");
        Utils.invokeStatic(BaseShader.class, "disposeShaders");

        // Terminate
        glfwTerminate();
    }
    
    private void render(AppAdapter listener){
        glfwPollEvents();
        
        // Render screen
        if(screen != null)
            screen.render(); /* RENDER */
        
        // FPS & DeltaTime
        fpsCounter.count();
        renderDeltaTime.update();
        
        // Render app
        listener.update(); /* UPDATE */
        listener.render(); /* RENDER */
        
        // Reset
        mouse.reset();
        keyboard.reset();
        window.swapBuffers();
    }
    

    public void setScreen(Screen screen){
        this.screen.hide();
        this.screen = screen;
        this.screen.show();
    }


    public int getFps(){
        return fpsCounter.get();
    }

    public float getRenderDeltaTime(){
        return renderDeltaTime.get();
    }
    
    public float getFixedUpdateDeltaTime(){
        return fixedUpdateDeltaTime.get();
    }
    
    public void setUpdateTPS(float updateTPS){
        if(fixedUpdateGenerator != null)
            fixedUpdateGenerator.setTPS(updateTPS);
        else
            initialUpdateTPS = updateTPS;
    }
    
    
    public void execSync(Runnable runnable){
        syncTaskExecutor.newTask(runnable);
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
        syncTaskExecutor.dispose();
        exitRequest = true;
    }

}
