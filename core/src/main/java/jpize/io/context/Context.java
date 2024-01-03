package jpize.io.context;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.event.SDL_EventType;
import io.github.libsdl4j.api.video.SDL_WindowEventID;
import jpize.gl.Gl;
import jpize.io.SdlInput;
import jpize.io.Window;
import jpize.sdl.event.SdlCallbacks;
import jpize.sdl.event.mouse.MouseButtonAction;
import jpize.sdl.input.KeyAction;
import jpize.util.Disposable;

public class Context implements Disposable{

    private static final ContextManager contextManager = ContextManager.getInstance();

    private final Window window;
    private final SdlInput input;
    private final SdlCallbacks callbacks;
    protected JpizeApplication adapter;
    private Screen screen;
    private boolean exitOnClose, enabled, showWindowOnInit;

    protected Context(Window window){
        contextManager.registerContext(this);

        this.window = window;
        this.exitOnClose = true;
        this.showWindowOnInit = true;

        this.input = new SdlInput(this);
        this.callbacks = new SdlCallbacks();

        setCurrent();
    }


    public boolean isExitOnClose(){
        return exitOnClose;
    }

    public void setExitOnClose(boolean exitOnClose){
        this.exitOnClose = exitOnClose;
    }


    public boolean isShowWindowOnInit(){
        return showWindowOnInit;
    }

    public void setShowWindowOnInit(boolean showWindowOnInit){
        this.showWindowOnInit = showWindowOnInit;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }


    public Window window(){
        return window;
    }

    public SdlInput input(){
        return input;
    }

    public SdlCallbacks callbacks(){
        return callbacks;
    }


    private Context setCurrent(){
        final Context previous = contextManager.getCurrentContext();
        setCurrent(this);
        return previous;
    }

    private void setCurrent(Context context){
        contextManager.setCurrentContext(context);
    }


    public void setAdapter(JpizeApplication adapter){
        this.adapter = adapter;
    }

    protected void init(){
        setCurrent();
        if(adapter != null)
            adapter.init();

        setEnabled(true);
        if(showWindowOnInit){
            window.show();
            Gl.viewport(window.getWidth(), window.getHeight()); // Windows bug fix
            window.swapBuffers();
        }

        callbacks.addWinSizeChangedCallback((window, width, height) -> {
            final Context prev = setCurrent();

            Gl.viewport(width, height);

            if(adapter != null)
                adapter.resize(width, height);

            setCurrent(prev);
        });
    }

    protected void render(){
        setCurrent();

        // Render adapter
        if(adapter != null){
            adapter.update();
            if(window.isShown())
                adapter.render();
        }

        // Render screen
        if(screen != null)
            screen.render();

        if(window.isShown())
            window.swapBuffers();
    }
    

    public void setScreen(Screen screen){
        if(this.screen != null)
            this.screen.hide();

        this.screen = screen;
        if(this.screen != null)
            this.screen.show();
    }

    @Override
    public void dispose(){
        contextManager.unregisterContext(this);

        setCurrent();

        window.hide();
        if(adapter != null)
            adapter.dispose();
        window.dispose();

        if(exitOnClose)
            contextManager.exit();
    }


    protected void onEvent(SDL_Event event){
        switch(event.type){
            // Quit
            case SDL_EventType.SDL_QUIT ->
                dispose();

            // Mouse
            case SDL_EventType.SDL_MOUSEWHEEL ->
                input.updateScroll(event.wheel);

            case SDL_EventType.SDL_MOUSEMOTION ->
                input.updatePos(event.motion);

            // Text
            case SDL_EventType.SDL_TEXTINPUT ->
                callbacks.invokeCharCallbacks((char) event.text.text[0]);

            // Keys
            case SDL_EventType.SDL_KEYDOWN -> {
                final KeyAction action = (event.key.repeat == 0) ? KeyAction.DOWN : KeyAction.REPEAT;
                callbacks.invokeKeyCallbacks(event.key.keysym, action);

                if(event.key.repeat == 0)
                    input.updateKeyDown(event.key.keysym);
            }

            case SDL_EventType.SDL_KEYUP -> {
                input.updateKeyUp(event.key.keysym);
                callbacks.invokeKeyCallbacks(event.key.keysym, KeyAction.UP);
            }

            // Buttons
            case SDL_EventType.SDL_MOUSEBUTTONDOWN -> {
                input.updateButtonDown(event.button.button);
                callbacks.invokeMouseButtonCallback(event.button, MouseButtonAction.DOWN);
            }

            case SDL_EventType.SDL_MOUSEBUTTONUP -> {
                input.updateButtonUp(event.button.button);
                callbacks.invokeMouseButtonCallback(event.button, MouseButtonAction.UP);
            }

            // Window
            case SDL_EventType.SDL_WINDOWEVENT -> {
                switch(event.window.event){
                    case SDL_WindowEventID.SDL_WINDOWEVENT_SHOWN           -> callbacks.invokeWinShownCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_HIDDEN          -> callbacks.invokeWinHiddenCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_EXPOSED         -> callbacks.invokeWinExposedCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_MOVED           -> callbacks.invokeWinMovedCallbacks      (window, event.window.data1, event.window.data2);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_RESIZED         -> callbacks.invokeWinResizedCallbacks    (window, event.window.data1, event.window.data2);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_SIZE_CHANGED    -> callbacks.invokeWinSizeChangedCallbacks(window, event.window.data1, event.window.data2);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_MINIMIZED       -> callbacks.invokeWinMinimizedCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_MAXIMIZED       -> callbacks.invokeWinMaximizedCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_RESTORED        -> callbacks.invokeWinRestoredCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_ENTER           -> callbacks.invokeWinEnterCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_LEAVE           -> callbacks.invokeWinLeaveCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_FOCUS_GAINED    -> callbacks.invokeWinFocusGainedCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_FOCUS_LOST      -> callbacks.invokeWinFocusLostCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_CLOSE           -> callbacks.invokeWinCloseCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_TAKE_FOCUS      -> callbacks.invokeWinTakeFocusCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_HIT_TEST        -> callbacks.invokeWinHitTestCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_ICCPROF_CHANGED -> callbacks.invokeWinIccProfChangedCallbacks(window);
                    case SDL_WindowEventID.SDL_WINDOWEVENT_DISPLAY_CHANGE  -> callbacks.invokeWinDisplayChangeCallbacks(window, event.window.data1);
                }
            }
        }
    }

}
