package jpize.io.context;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Platform;
import jpize.audio.Audio;
import jpize.gl.buffer.GlBuffer;
import jpize.gl.shader.GlProgram;
import jpize.gl.vertex.GlVertexArray;
import jpize.glfw.glfw.Glfw;
import jpize.glfw.glfw.GlfwHint;
import jpize.graphics.texture.CubeMap;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureArray;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.ScreenQuad;
import jpize.graphics.util.ScreenQuadShader;
import jpize.graphics.util.TextureUtils;
import jpize.io.JoystickManager;
import jpize.io.MonitorManager;
import jpize.io.Window;
import jpize.util.Utils;
import jpize.util.time.DeltaTimeCounter;
import jpize.util.time.FpsCounter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextManager{

    private static ContextManager instance;

    public static void init(){
        if(instance == null){
            instance = new ContextManager();
            instance.initLibs();
        }
    }

    public static ContextManager getInstance(){
        init();
        return instance;
    }


    private final Map<Long, Context> contextMap;
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
        this.contextMap = new ConcurrentHashMap<>();
        this.syncTaskExecutor = new SyncTaskExecutor();

        this.fpsCounter = new FpsCounter();
        this.fpsCounter.count();
        this.dtCounter = new DeltaTimeCounter();
        this.dtCounter.count();
    }


    private void initLibs(){
        // Init GLFW
        Glfw.setErrorCallback();
        GLFW.glfwInit();

        Glfw.defaultWindowHints();
        if(Platform.get() == Platform.MACOSX)
            Glfw.windowHint(GlfwHint.COCOA_RETINA_FRAMEBUFFER, 0);

        // Init Managers & Audio
        MonitorManager.init();
        JoystickManager.init();
        Audio.init();
    }


    public void run(){
        // Render
        while(!Thread.interrupted()){
            if(stopRequest)
                break;

            if(!contextMap.isEmpty()){
                // Poll events
                Glfw.pollEvents();

                // Fps & DeltaTime
                fpsCounter.count();
                dtCounter.count();

                // Render
                for(Context context: contextMap.values()){
                    if(!context.isEnabled())
                        continue;

                    setCurrentContext(context);
                    context.render();
                }
            }
            else if(exitWhenNoWindows)
                break;

            // Sync tasks
            syncTaskExecutor.executeTasks();
        }

        stop();
    }

    private void stop(){
        syncTaskExecutor.dispose();

        // Unbind GL objects
        GlBuffer.unbindAll();
        GlProgram.unbind();
        GlVertexArray.unbind();
        Texture.unbind();
        CubeMap.unbind();
        TextureArray.unbind();

        // 'private static' dispose Methods
        Utils.invokeStatic(Audio.class, "dispose");
        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(TextureUtils.class, "dispose");
        Utils.invokeStatic(BaseShader.class, "disposeShaders");

        // Dispose contexts
        for(Context context: contextMap.values()){
            setCurrentContext(context);
            context.dispose();
        }

        GL.setCapabilities(null);

        // Terminate
        Glfw.terminate();
    }

    public void exit(){
        stopRequest = true;
    }

    public void closeAllWindows(){
        for(Context context: contextMap.values()){
            setCurrentContext(context);
            context.dispose();
        }
    }


    private void setCurrentContext(Context context){
        currentContext = context;
        final Window window = context.getWindow();
        window.makeCurrent();
        GL.setCapabilities(window.getCapabilities());
    }


    public Collection<Context> getContexts(){
        return contextMap.values();
    }

    public Context getCurrentContext(){
        return currentContext;
    }

    public Context getContext(long windowID){
        return contextMap.get(windowID);
    }

    public void registerContext(Context context){
        setCurrentContext(context);
        contextMap.put(context.getWindow().getID(), context);
    }

    public void unregisterContext(Context context){
        contextMap.remove(context.getWindow().getID());
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
