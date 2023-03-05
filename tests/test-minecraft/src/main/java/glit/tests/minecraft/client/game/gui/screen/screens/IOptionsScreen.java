package glit.tests.minecraft.client.game.gui.screen.screens;

import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.gui.screen.Screen;

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
        return !session.getGame().isInGame();
    }

    @Override
    public void dispose(){ }

    public void saveOptions(){
        session.getOptions().save();
    }

}
