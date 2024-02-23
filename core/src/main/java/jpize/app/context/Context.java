package jpize.app.context;

import jpize.gl.Gl;
import jpize.app.input.ContextInput;
import jpize.app.input.callback.CallbackHolder;
import jpize.app.JpizeApplication;
import jpize.app.Screen;
import jpize.sdl.gl.SdlGlContext;
import jpize.sdl.window.SdlWindow;
import jpize.app.Disposable;

public class Context implements Disposable{

    private static final ContextManager contextManager = ContextManager.getInstance();

    private final SdlWindow window;
    private final SdlGlContext gl;
    private final ContextInput input;
    private final CallbackHolder callbacks;
    protected JpizeApplication adapter;
    private Screen screen;
    private boolean exitOnClose, enabled, showWindowOnInit, alive;

    protected Context(SdlWindow window){
        contextManager.registerContext(this);

        this.window = window;
        this.gl = new SdlGlContext(window);
        this.exitOnClose = true;
        this.showWindowOnInit = true;

        this.input = new ContextInput(this);
        this.callbacks = new CallbackHolder();

        setCurrent();

        this.alive = true;
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


    public SdlWindow window(){
        return window;
    }

    public SdlGlContext gl(){
        return gl;
    }

    public ContextInput input(){
        return input;
    }

    public CallbackHolder callbacks(){
        return callbacks;
    }


    private void setCurrent(Context context){
        contextManager.setCurrentContext(context);
    }

    private Context setCurrent(){
        final Context previous = contextManager.getCurrentContext();
        setCurrent(this);
        return previous;
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

        callbacks.addWinCloseCallback((window) -> dispose());
    }

    protected void render(){
        setCurrent();

        // Update callback
        callbacks.invokeUpdateLoopCallbacks();

        // Render adapter
        if(adapter != null){
            adapter.update();
            if(window.isShown())
                adapter.render();
        }

        // Render screen
        if(screen != null)
            screen.render();

        // Render callback
        callbacks.invokeRenderLoopCallbacks();

        // Swap buffers
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

        alive = false;
        if(exitOnClose)
            contextManager.exit();
    }

    public boolean isAlive(){
        return alive;
    }

}
