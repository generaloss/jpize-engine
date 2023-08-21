package pize.app;

import pize.audio.Audio;
import pize.lib.gl.buffer.GlBuffer;
import pize.lib.gl.shader.GlProgram;
import pize.lib.gl.Gl;
import pize.lib.gl.vertex.GlVertexArray;
import pize.graphics.texture.CubeMap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureArray;
import pize.graphics.util.BaseShader;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.ScreenQuadShader;
import pize.graphics.util.TextureUtils;
import pize.io.keyboard.Keyboard;
import pize.io.mouse.Mouse;
import pize.io.window.Window;
import pize.util.Utils;
import pize.util.time.DeltaTimeCounter;
import pize.util.time.FpsCounter;
import pize.util.time.TickGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;

import static org.lwjgl.glfw.GLFW.*;

public class Context{

    private final Audio audio;
    private final Window window;

    private final FpsCounter fpsCounter;
    private final DeltaTimeCounter renderDeltaTime, fixedUpdateDeltaTime;
    
    private final ExecutorService fixedUpdateExecutor;
    private TickGenerator fixedUpdateGenerator;
    private float initialUpdateTPS;

    private Screen screen;
    private boolean exitRequest;
    private final ContextTaskExecutor syncTaskExecutor;

    public Context(Window window){
        this.audio = new Audio();
        this.window = window;

        this.fpsCounter = new FpsCounter();
        this.fpsCounter.count();
        this.renderDeltaTime = new DeltaTimeCounter();
        this.renderDeltaTime.update();
        
        this.fixedUpdateExecutor = Executors.newFixedThreadPool(3);
        this.fixedUpdateDeltaTime = new DeltaTimeCounter();
        
        this.syncTaskExecutor = new ContextTaskExecutor();
    }


    public void begin(AppAdapter listener){
        listener.init(); /* INIT */
        
        // Window initialization
        window.show();
        window.addSizeCallback((int width, int height) -> {
            listener.resize(width, height);
            Gl.viewport(width, height);
        });
        
        // Fixed update
        if(initialUpdateTPS != 0){
            fixedUpdateGenerator = new TickGenerator(initialUpdateTPS);
            
            fixedUpdateGenerator.startAsync(()->{
                if(fixedUpdateExecutor.isShutdown())
                    return;

                fixedUpdateDeltaTime.update();
                fixedUpdateExecutor.execute(listener::fixedUpdate); /* FIXED UPDATE */
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
        GlBuffer.unbindAll();
        GlProgram.unbind();
        GlVertexArray.unbind();
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
        window.getMouse().reset();
        window.getKeyboard().reset();
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
    
    public void setFixedUpdateTPS(float updateTPS){
        if(fixedUpdateGenerator != null)
            fixedUpdateGenerator.setTPS(updateTPS);
        else
            initialUpdateTPS = updateTPS;
    }
    
    
    public void execSync(Runnable runnable){
        syncTaskExecutor.exec(runnable);
    }

    public void execIf(Runnable runnable, BooleanSupplier condition){
        syncTaskExecutor.execIf(runnable, condition);
    }
    

    public Audio getAudio(){
        return audio;
    }

    public Window getWindow(){
        return window;
    }

    public Keyboard getKeyboard(){
        return window.getKeyboard();
    }

    public Mouse getMouse(){
        return window.getMouse();
    }


    public void exit(){
        syncTaskExecutor.dispose();
        exitRequest = true;
    }

}
