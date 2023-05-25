package pize.tests.minecraft.game.gui.screen.screens;

import pize.graphics.util.batch.Batch;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.screen.Screen;

public class WorldSelectionScreen extends Screen{

    public WorldSelectionScreen(Session session){
        super(session);

        // Main Layout

        // <----------TEXTS---------->
        // < Title >

        // Title

        // <----------LINE 1---------->
        // [ Edit ] [ Delete ] [ Re-Create ] [ Cancel ]

        // Horizontal Layout

        // Edit

        // Delete

        // Re-Create

        // Cancel

        // <LINE---------------------------->
        // [ Play Selected World ] [ Create New World ]

        // Horizontal Layout

        // Play Selected World

        // Create New World

        // <----------CALLBACKS---------->


    }

    @Override
    public void render(Batch batch){

    }

    @Override
    public void resize(int width, int height){ }


    @Override
    public void onShow(){ }

    @Override
    public boolean shouldCloseOnEsc(){
        return true;
    }

    @Override
    public void close(){
        toScreen("main_menu");
    }

    @Override
    public void dispose(){ }

    @Override
    public boolean renderDirtBackground(){
        return true;
    }

}