package pize.tests.minecraft.gui.screen.screens;

import pize.tests.minecraft.Session;
import pize.tests.minecraft.gui.screen.Screen;
import pize.tests.minecraft.options.Options;

public abstract class IOptionsScreen extends Screen{

    public IOptionsScreen(Session session){
        super(session);
    }


    @Override
    public boolean shouldCloseOnEsc(){
        return true;
    }

    @Override
    public boolean renderDirtBackground(){
        return true;
    }

    @Override
    public void dispose(){ }

    public void saveOptions(){
        session.getOptions().save();
    }
    
    public Options getOptions(){
        return session.getOptions();
    }

}
