package jpize.io.context;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.event.SdlEvents;
import jpize.audio.AudioDeviceManager;
import jpize.gl.buffer.GlBuffer;
import jpize.gl.shader.GlProgram;
import jpize.gl.vertex.GlVertexArray;
import jpize.graphics.texture.CubeMap;
import jpize.graphics.texture.GlTexture;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureArray;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.ScreenQuad;
import jpize.graphics.util.ScreenQuadShader;
import jpize.graphics.util.TextureUtils;
import jpize.sdl.Sdl;
import jpize.util.Utils;
import jpize.util.time.DeltaTimeCounter;
import jpize.util.time.FpsCounter;
import org.lwjgl.opengl.GL;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContextManager{

    private final static ContextManager instance;

    static{
        Sdl.init();
        AudioDeviceManager.init();
        instance = new ContextManager();
    }

    public static ContextManager getInstance(){
        return instance;
    }


    private final List<Context> contextsToInit;
    private final Map<Integer, Context> contexts;
    private Context currentContext;
    private final SyncTaskExecutor syncTaskExecutor;

    private final FpsCounter fpsCounter;
    private final DeltaTimeCounter dtCounter;

    private boolean exitWhenNoWindows;
    private boolean stopRequest;

    public boolean isExitWhenNoWindows(){
        return exitWhenNoWindows;
    }

    public void exitWhenNoWindows(boolean exitWhenNoWindows){
        this.exitWhenNoWindows = exitWhenNoWindows;
    }


    private ContextManager(){
        this.contextsToInit = new CopyOnWriteArrayList<>();
        this.contexts = new ConcurrentHashMap<>();
        this.syncTaskExecutor = new SyncTaskExecutor();

        this.fpsCounter = new FpsCounter();
        this.fpsCounter.count();
        this.dtCounter = new DeltaTimeCounter();
        this.dtCounter.count();
    }

    public void run(){
        // Loop
        while(!Thread.interrupted()){
            if(stopRequest)
                break;

            // Init
            for(Context context: contextsToInit){
                if(context.adapter == null)
                    continue;

                context.init();
                contextsToInit.remove(context);
                contexts.put(context.window().getID(), context);
            }

            // Render
            if(!contexts.isEmpty()){
                // Fps & DeltaTime
                fpsCounter.count();
                dtCounter.count();

                // Handle events
                handleEvents();

                // Render
                for(Context context: contexts.values()){
                    if(!context.isEnabled())
                        continue;

                    context.render();
                }
            }
            // Exit
            else if(exitWhenNoWindows)
                break;

            // Sync tasks
            syncTaskExecutor.executeTasks();
        }

        stop();
    }

    private void handleEvents(){
        for(Context context: contexts.values())
            context.input().clear();

        final SDL_Event event = new SDL_Event();
        while(SdlEvents.SDL_PollEvent(event) != 0){
            for(Context context: contexts.values())
                context.onEvent(event);
        }
    }

    private void stop(){
        syncTaskExecutor.dispose();

        // Unbind GL objects
        GlBuffer.unbindAll();
        GlTexture.unbindAll();
        GlProgram.unbind();
        GlVertexArray.unbind();
        Texture.unbind();
        CubeMap.unbind();
        TextureArray.unbind();

        // Dispose contexts
        for(Context context: contexts.values())
            context.dispose();

        // 'private static' dispose Methods
        Utils.invokeStatic(TextureUtils.class, "dispose");
        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(BaseShader.class, "disposeShaders");
        Utils.invokeStatic(AudioDeviceManager.class, "dispose");

        GL.setCapabilities(null);

        // Terminate
        Sdl.quit();
    }

    public void exit(){
        stopRequest = true;
    }

    public void closeAllWindows(){
        for(Context context: contexts.values())
            context.dispose();
    }

    public void closeOtherWindows(){
        final Context current = currentContext;

        for(Context context: contexts.values())
            if(context != current)
                context.dispose();

        setCurrentContext(current);
    }


    public Context getCurrentContext(){
        return currentContext;
    }

    protected void setCurrentContext(Context context){
        currentContext = context;
        context.window().getGlContext().makeCurrent();
    }


    public Collection<Context> getContexts(){
        return contexts.values();
    }


    public void registerContext(Context context){
        contextsToInit.add(context);
    }

    public void unregisterContext(Context context){
        contexts.remove(context.window().getID());
    }


    public int getFPS(){
        return fpsCounter.get();
    }

    public float getDeltaTime(){
        return dtCounter.get();
    }

    
    public SyncTaskExecutor getSyncTaskExecutor(){
        return syncTaskExecutor;
    }

}
