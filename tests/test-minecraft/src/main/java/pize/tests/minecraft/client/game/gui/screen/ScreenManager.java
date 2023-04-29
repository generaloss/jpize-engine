package pize.tests.minecraft.client.game.gui.screen;

import pize.context.Disposable;
import pize.context.Resizable;
import pize.graphics.util.batch.Batch;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager implements Resizable, Disposable{

    private final Map<String, Screen> screens;
    private String current;

    public ScreenManager(){
        screens = new HashMap<>();
    }

    public void setCurrentScreen(String id){
        if(screens.containsKey(id)){
            current = id;
            screens.get(id).onShow();
        }else
            current = null;
    }


    public void putScreen(String id, Screen screen){
        screens.put(id, screen);
    }

    public void putGui(String id, Screen screen){
        screens.put(id, screen);
    }

    public void render(Batch batch){
        Screen screen = screens.get(current);
        if(screen != null){
            screen.update(batch);
            screen.render(batch);
        }
    }

    @Override
    public void resize(int width, int height){
        for(Screen screen: screens.values())
            screen.resize(width, height);
    }

    @Override
    public void dispose(){
        for(Screen screen: screens.values())
            screen.dispose();
    }

}
