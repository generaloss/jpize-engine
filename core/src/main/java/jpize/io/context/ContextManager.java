package jpize.io.context;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.event.SdlEvents;
import jpize.audio.AudioDeviceManager;
import jpize.gl.Gl;
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
import jpize.sdl.window.SdlWindow;
import jpize.sdl.Sdl;
import jpize.sdl.event.SdlEventType;
import jpize.sdl.event.SdlWindowEventType;
import jpize.sdl.event.callback.SdlCallbacks;
import jpize.sdl.event.callback.mouse.MouseButtonAction;
import jpize.sdl.input.KeyAction;
import jpize.util.Utils;
import jpize.util.time.DeltaTimeCounter;
import jpize.util.time.FpsCounter;

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
                // Handle events
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

            // Sync tasks
            syncTaskExecutor.executeTasks();
        }

        stop();
    }

    private void handleEvents(){
        for(Context context: contexts.values())
            context.input().clear();

        final SDL_Event event = new SDL_Event();
        while(SdlEvents.SDL_PollEvent(event) != 0)
            onEvent(event);
    }

    private Context getContext(int windowID){
        return contexts.get(windowID);
    }

    private void onEvent(SDL_Event event){
        final SdlEventType type = SdlEventType.bySdlConst(event.type); //: 1) It still does not make sense
        switch(type){
            // Mouse
            case MOUSEWHEEL -> {
                final Context context = getContext(event.wheel.windowID);
                if(context == null) return;
                context.input().updateScroll(event.wheel);
            }
            case MOUSEMOTION -> {
                final Context context = getContext(event.motion.windowID);
                if(context == null) return;
                context.input().updatePos(event.motion);
            }
            // Text
            case TEXTINPUT -> {
                final Context context = getContext(event.text.windowID);
                if(context == null) return;
                context.callbacks().invokeCharCallbacks((char) event.text.text[0]);
            }
            // Keys
            case KEYDOWN -> {
                final Context context = getContext(event.key.windowID);
                if(context == null) return;

                final KeyAction action = (event.key.repeat == 0) ? KeyAction.DOWN : KeyAction.REPEAT;
                context.callbacks().invokeKeyCallbacks(event.key.keysym, action);

                if(event.key.repeat == 0)
                    context.input().updateKeyDown(event.key.keysym);
            }
            case KEYUP -> {
                final Context context = getContext(event.key.windowID);
                if(context == null) return;
                context.input().updateKeyUp(event.key.keysym);
                context.callbacks().invokeKeyCallbacks(event.key.keysym, KeyAction.UP);
            }
            // Buttons
            case MOUSEBUTTONDOWN -> {
                final Context context = getContext(event.button.windowID);
                if(context == null) return;
                context.input().updateButtonDown(event.button.button);
                context.callbacks().invokeMouseButtonCallback(event.button, MouseButtonAction.DOWN);
            }
            case MOUSEBUTTONUP -> {
                final Context context = getContext(event.button.windowID);
                if(context == null) return;
                context.input().updateButtonUp(event.button.button);
                context.callbacks().invokeMouseButtonCallback(event.button, MouseButtonAction.UP);
            }
            // Window
            case WINDOWEVENT -> {
                final Context context = getContext(event.window.windowID);
                if(context == null) return;
                final SdlWindow window = context.window();
                final SdlCallbacks callbacks = context.callbacks();

                final SdlWindowEventType winEvent = SdlWindowEventType.byID(event.window.event); //: 2) It still does not make sense
                switch(winEvent){
                    case SHOWN           -> callbacks.invokeWinShownCallbacks(window);
                    case HIDDEN          -> callbacks.invokeWinHiddenCallbacks(window);
                    case EXPOSED         -> callbacks.invokeWinExposedCallbacks(window);
                    case MOVED           -> callbacks.invokeWinMovedCallbacks      (window, event.window.data1, event.window.data2);
                    case RESIZED         -> callbacks.invokeWinResizedCallbacks    (window, event.window.data1, event.window.data2);
                    case SIZE_CHANGED    -> callbacks.invokeWinSizeChangedCallbacks(window, event.window.data1, event.window.data2);
                    case MINIMIZED       -> callbacks.invokeWinMinimizedCallbacks(window);
                    case MAXIMIZED       -> callbacks.invokeWinMaximizedCallbacks(window);
                    case RESTORED        -> callbacks.invokeWinRestoredCallbacks(window);
                    case ENTER           -> callbacks.invokeWinEnterCallbacks(window);
                    case LEAVE           -> callbacks.invokeWinLeaveCallbacks(window);
                    case FOCUS_GAINED    -> callbacks.invokeWinFocusGainedCallbacks(window);
                    case FOCUS_LOST      -> callbacks.invokeWinFocusLostCallbacks(window);
                    case CLOSE           -> callbacks.invokeWinCloseCallbacks(window);
                    case TAKE_FOCUS      -> callbacks.invokeWinTakeFocusCallbacks(window);
                    case HIT_TEST        -> callbacks.invokeWinHitTestCallbacks(window);
                    case ICCPROF_CHANGED -> callbacks.invokeWinIccProfChangedCallbacks(window);
                    case DISPLAY_CHANGE  -> callbacks.invokeWinDisplayChangeCallbacks(window, event.window.data1);
                }
            }
        }
    }


    private void stop(){
        syncTaskExecutor.dispose();

        // Dispose contexts
        for(Context context: contexts.values())
            context.dispose();

        // 'private static' dispose Methods
        Utils.invokeStatic(TextureUtils.class, "dispose");
        Utils.invokeStatic(ScreenQuad.class, "dispose");
        Utils.invokeStatic(ScreenQuadShader.class, "dispose");
        Utils.invokeStatic(BaseShader.class, "disposeShaders");
        Utils.invokeStatic(AudioDeviceManager.class, "dispose");

        // Unbind GL objects
        GlBuffer.unbindAll();
        GlTexture.unbindAll();
        GlProgram.unbind();
        GlVertexArray.unbind();
        Texture.unbind();
        CubeMap.unbind();
        TextureArray.unbind();

        Gl.setCapabilities(null);
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
        context.gl().makeCurrent();
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
