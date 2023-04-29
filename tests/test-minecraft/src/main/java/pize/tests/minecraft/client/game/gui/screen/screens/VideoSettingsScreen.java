package pize.tests.minecraft.client.game.gui.screen.screens;

import pize.graphics.util.batch.Batch;
import pize.tests.minecraft.client.game.Session;

public class VideoSettingsScreen extends IOptionsScreen{

    public static final int MIN_FOV = 30;
    public static final int MAX_FOV = 110;
    public static final int MIN_RENDER_DISTANCE = 1;
    public static final int MAX_RENDER_DISTANCE = 128;
    public static final int MAX_SETTING_FRAMERATE = 255;
    public static final int FRAMERATE_SETTING_INTERVAL = 5;

    public VideoSettingsScreen(Session session){
        super(session);

        // Main Layout

        // <----------TEXTS---------->
        // < Title >

        // Title

        // <----------OPTIONS: 1 LINE---------->
        // [ FOV ] [ Render Distance ]

        // 1 Line Layout

        // Fov

        // Render Distance

        // <----------OPTIONS: 2 LINE---------->
        // [ Max Framerate ] [ Show FPS ]

        // 2 Line Layout

        // Max Framerate

        // Show FPS

        // <----------OPTIONS: 3 LINE---------->
        // [ Mipmap Levels ] [ Fullscreen ]

        // 3 Line Layout

        // Mipmap Levels

        // Fullscreen

        // <----------DONE---------->
        // [ Done ]

        // Done

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
    public void close(){
        toScreen("options");
    }

}