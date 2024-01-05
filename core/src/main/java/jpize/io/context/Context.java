package jpize.io.context;

import jpize.gl.Gl;
import jpize.io.SdlInput;
import jpize.sdl.event.callback.SdlCallbacks;
import jpize.sdl.gl.SdlGlContext;
import jpize.sdl.window.SdlWindow;
import jpize.util.Disposable;

public class Context implements Disposable{

    private static final ContextManager contextManager = ContextManager.getInstance();

    private final SdlWindow window;
    private final SdlGlContext gl;
    private final SdlInput input;
    private final SdlCallbacks callbacks;
    protected JpizeApplication adapter;
    private Screen screen;
    private boolean exitOnClose, enabled, showWindowOnInit;

    protected Context(SdlWindow window){
        contextManager.registerContext(this);

        this.window = window;
        this.gl = new SdlGlContext(window);
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


    public SdlWindow window(){
        return window;
    }

    public SdlGlContext gl(){
        return gl;
    }

    public SdlInput input(){
        return input;
    }

    public SdlCallbacks callbacks(){
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

}
