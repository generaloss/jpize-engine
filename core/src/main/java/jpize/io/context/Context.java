package jpize.io.context;

import jpize.gl.Gl;
import jpize.io.Window;
import jpize.io.SdlInput;
import jpize.sdl.event.SdlCallbacks;
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
            if(adapter != null)
                adapter.resize(width, height);

            Gl.viewport(width, height);
            setCurrent(prev);
        });

        callbacks.addWinCloseCallback((window) -> dispose());
    }

    protected void render(){
        setCurrent();

        // Render screen
        if(screen != null)
            screen.render();

        // Render adapter
        if(adapter != null){
            adapter.update();
            if(window.isShown())
                adapter.render();
        }

        if(window.isShown())
            window.swapBuffers();
    }
    

    public void setScreen(Screen screen){
        this.screen.hide();
        this.screen = screen;
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
