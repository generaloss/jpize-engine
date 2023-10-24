package jpize.io.context;

import jpize.gl.Gl;
import jpize.io.Keyboard;
import jpize.io.Mouse;
import jpize.io.Window;
import jpize.util.Disposable;

public class Context implements Disposable{

    private static final ContextManager contextManager = ContextManager.getInstance();

    private final Window window;
    protected JpizeApplication adapter;
    private Screen screen;
    private boolean exitOnClose, enabled, showWindowOnInit;

    protected Context(Window window){
        contextManager.registerContext(this);

        this.window = window;
        this.exitOnClose = true;
        this.showWindowOnInit = true;

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

        window.setSizeCallback((int width, int height) -> {
            final Context prev = setCurrent();
            if(adapter != null)
                adapter.resize(width, height);

            Gl.viewport(width, height);
            setCurrent(prev);
        });

        setEnabled(true);
        if(showWindowOnInit){
            window.show();
            window.swapBuffers();
        }
    }

    protected void render(){
        setCurrent();

        if(window.closeRequest()){
            dispose();
            return;
        }

        // Render screen
        if(screen != null)
            screen.render();

        // Render adapter
        if(adapter != null){
            adapter.update();
            if(window.isVisible())
                adapter.render();
        }
        
        // Reset
        window.getMouse().reset();
        window.getKeyboard().reset();

        if(window.isVisible())
            window.swapBuffers();
    }
    

    public void setScreen(Screen screen){
        this.screen.hide();
        this.screen = screen;
        this.screen.show();
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


    @Override
    public void dispose(){
        setCurrent();

        window.hide();
        if(adapter != null) adapter.dispose();
        window.dispose();

        contextManager.unregisterContext(this);
        if(exitOnClose)
            contextManager.exit();

    }

}
