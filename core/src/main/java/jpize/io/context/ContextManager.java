package jpize.io.context;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.event.SDL_EventType;
import io.github.libsdl4j.api.event.SdlEvents;
import io.github.libsdl4j.api.video.SDL_WindowEventID;
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
import jpize.sdl.input.KeyAction;
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
    private final SDL_Event event;


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

        this.event = new SDL_Event();
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
                contexts.put(context.getWindow().getID(), context);
            }

            // Sync tasks
            syncTaskExecutor.executeTasks();

            // Render
            if(!contexts.isEmpty()){
                // Poll events
                handleEvents();

                // Fps & DeltaTime
                fpsCounter.count();
                dtCounter.count();

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
        }

        stop();
    }

    private void handleEvents(){
        for(Context context: contexts.values())
            context.getInput().update();

        while(SdlEvents.SDL_PollEvent(event) != 0){
            switch(event.type){
                // Text
                case SDL_EventType.SDL_TEXTINPUT -> {
                    final Context context = contexts.get(event.text.windowID);
                    if(context == null)
                        continue;

                    context.getCallbacks().invokeCharCallbacks((char) event.text.text[0]);
                }

                // Keys
                case SDL_EventType.SDL_KEYDOWN -> {
                    final Context context = contexts.get(event.key.windowID);
                    if(context == null)
                        continue;

                    final KeyAction action = (event.key.repeat == 0) ? KeyAction.DOWN : KeyAction.REPEAT;
                    context.getCallbacks().invokeKeyCallbacks(event.key.keysym, action);

                    if(event.key.repeat == 0)
                        context.getInput().updateKeyDown(event.key.keysym);
                }

                case SDL_EventType.SDL_KEYUP -> {
                    final Context context = contexts.get(event.key.windowID);
                    if(context == null)
                        continue;

                    context.getInput().updateKeyUp(event.key.keysym);
                    context.getCallbacks().invokeKeyCallbacks(event.key.keysym, KeyAction.UP);
                }

                // Buttons
                case SDL_EventType.SDL_MOUSEBUTTONDOWN -> {
                    final Context context = contexts.get(event.button.windowID);
                    if(context == null)
                        continue;

                    context.getInput().updateButtonDown(event.button.button);
                }

                case SDL_EventType.SDL_MOUSEBUTTONUP -> {
                    final Context context = contexts.get(event.button.windowID);
                    if(context == null)
                        continue;

                    context.getInput().updateButtonUp(event.button.button);
                }

                // Mouse
                case SDL_EventType.SDL_MOUSEWHEEL -> {
                    final Context context = contexts.get(event.wheel.windowID);
                    if(context == null)
                        continue;

                    context.getInput().updateScroll(event.wheel);
                }

                case SDL_EventType.SDL_MOUSEMOTION -> {
                    final Context context = contexts.get(event.motion.windowID);
                    if(context == null)
                        continue;

                    context.getInput().updatePos(event.motion);
                }

                // Window
                case SDL_EventType.SDL_WINDOWEVENT -> {
                    final Context context = contexts.get(event.window.windowID);
                    if(context == null)
                        continue;

                    switch(event.window.event){
                        case SDL_WindowEventID.SDL_WINDOWEVENT_RESIZED -> context.resize();
                        case SDL_WindowEventID.SDL_WINDOWEVENT_CLOSE -> context.dispose();
                    }

                }
            }
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
        context.getWindow().getGlContext().makeCurrent();
    }


    public Collection<Context> getContexts(){
        return contexts.values();
    }


    public void registerContext(Context context){
        contextsToInit.add(context);
    }

    public void unregisterContext(Context context){
        contexts.remove(context.getWindow().getID());
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
