package pize.io.context;

import pize.gl.Gl;
import pize.io.Keyboard;
import pize.io.Mouse;
import pize.io.Window;
import pize.util.Disposable;

public class Context implements Disposable{

    private final Window window;
    private ContextAdapter adapter;
    private final FixedUpdate fixedUpdate;
    private Screen screen;
    private boolean exitOnClose, enabled;

    protected Context(Window window){
        this.window = window;
        this.exitOnClose = true;
        this.fixedUpdate = new FixedUpdate(0);

        ContextManager.getInstance().registerContext(this);
    }


    public boolean isExitOnClose(){
        return exitOnClose;
    }

    public void setExitOnClose(boolean exitOnClose){
        this.exitOnClose = exitOnClose;
    }


    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }


    public void init(ContextAdapter adapter){
        this.adapter = adapter;
        this.adapter.init();

        this.window.setSizeCallback((int width, int height) -> {
            window.makeCurrent();
            adapter.resize(width, height);
            Gl.viewport(width, height);
        });

        setEnabled(true);
        this.window.show();
    }
    
    protected void render(){
        if(window.closeRequest()){
            dispose();
            return;
        }

        // Render
        if(screen != null)
            screen.render();

        adapter.update();
        adapter.render();
        
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


    public FixedUpdate getFixedUpdate(){
        return fixedUpdate;
    }

    public void startFixedUpdate(float tps){
        fixedUpdate.setTPS(tps);
        fixedUpdate.start(() -> {
            adapter.fixedUpdate();
        });
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
        window.hide();
        fixedUpdate.stop();
        adapter.dispose();
        window.dispose();

        ContextManager.getInstance().unregisterContext(this);
        if(exitOnClose)
            ContextManager.getInstance().exit();
    }

}
