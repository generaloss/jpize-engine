package glit.tests.minecraft.client.game;

import glit.Glit;
import glit.files.FileHandle;
import glit.graphics.util.ScreenUtils;
import glit.io.glfw.Key;
import glit.tests.minecraft.client.auth.PlayerProfile;
import glit.tests.minecraft.client.game.audio.AudioManager;
import glit.tests.minecraft.client.game.audio.MusicManager;
import glit.tests.minecraft.client.game.gui.screen.ScreenManager;
import glit.tests.minecraft.client.game.gui.screen.screens.*;
import glit.tests.minecraft.client.game.lang.LanguageInfo;
import glit.tests.minecraft.client.game.lang.LanguageManager;
import glit.tests.minecraft.client.game.options.KeyMapping;
import glit.tests.minecraft.client.game.options.Options;
import glit.tests.minecraft.client.game.resources.ResourceManager;
import glit.tests.minecraft.server.server.IntegratedServer;
import glit.tests.minecraft.utils.log.Logger;
import glit.util.time.Sync;

import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Session implements Renderer{

    public static final String CLIENT_NAME = "GeneralPashon";
    public static final String GAME_DIR_PATH = System.getProperty("user.home") + "/minecraft/";

    private final Options options;
    private final LanguageManager languageManager;
    private final ResourceManager resourceManager;
    private final MusicManager musicManager;
    private final AudioManager audioManager;
    private final ScreenManager screenManager;
    private final PlayerProfile clientProfile;
    private final ClientGame clientGame;
    private final IngameGUI ingameGui;
    private final GameRenderer gameRenderer;
    private final IntegratedServer integratedServer;
    private final Sync fpsLimiter;

    public Session(){
        Logger.instance().info("INIT:");

        fpsLimiter = new Sync(0);
        options = new Options(this, GAME_DIR_PATH);

        languageManager = new LanguageManager();
        languageManager.updateAvailableLanguages();
        LanguageInfo info = languageManager.getLanguageInfo(options.getLanguage());
        languageManager.selectLanguage(info == null ? "en_us" : info.getCode());

        {
            resourceManager = new ResourceManager();
            // TEXTURE: GUI
            resourceManager.setLocation("vanilla/textures/gui/");
            {
                resourceManager.putTexture("button", "widgets.png", new Rectangle(0, 66, 200, 20));
                resourceManager.putTexture("button_hover", "widgets.png", new Rectangle(0, 86, 200, 20));
                resourceManager.putTexture("button_blocked", "widgets.png", new Rectangle(0, 46, 200, 20));
                resourceManager.putTexture("options_background", "options_background.png");
                resourceManager.putTexture("title_left_part", "title/minecraft.png", new Rectangle(0, 0, 155, 44));
                resourceManager.putTexture("title_right_part", "title/minecraft.png", new Rectangle(0, 45, 119, 44));
                resourceManager.putTexture("title_edition", "title/edition.png", new Rectangle(0, 0, 98, 14));
                resourceManager.putTexture("title_edition", "title/edition.png", new Rectangle(0, 0, 98, 14));
                resourceManager.putTexture("panorama_overlay", "title/background/panorama_overlay.png");
            }
            // FONT
            resourceManager.setLocation("");
            {
                resourceManager.putFontFnt("font_minecraft", "vanilla/font/default.fnt");
            }
            // MUSIC
            resourceManager.setLocation("music/menu/");
            {
                resourceManager.putMusic("Beginning 2", "beginning2.ogg");
                resourceManager.putMusic("Moog City 2", "moogcity2.ogg");
                resourceManager.putMusic("Floating Trees", "floatingtrees.ogg");
                resourceManager.putMusic("Mutation", "mutation.ogg");
            }
            // SOUND
            resourceManager.setLocation("sound/");
            {
                resourceManager.putSound("click", "random/click.ogg");
            }
            // LOAD
            resourceManager.load();
        }

        Logger.instance().info("Init Sound");

        musicManager = new MusicManager(this);
        audioManager = new AudioManager(this);

        Logger.instance().info("Init GUI");

        screenManager = new ScreenManager();
        screenManager.putScreen("main_menu", new MainMenuScreen(this));
        screenManager.putScreen("world_selection", new WorldSelectionScreen(this));
        screenManager.putScreen("options", new OptionsScreen(this));
        screenManager.putScreen("video_settings", new VideoSettingsScreen(this));
        screenManager.putScreen("audio_settings", new AudioSettingsScreen(this));
        screenManager.setCurrentScreen("main_menu");

        Logger.instance().info("Init Renderer");

        clientProfile = new PlayerProfile(UUID.randomUUID(), CLIENT_NAME);
        clientGame = new ClientGame(this);
        ingameGui = new IngameGUI(this);
        gameRenderer = new GameRenderer(this);

        integratedServer = new IntegratedServer(this);

        Logger.instance().info("RENDERING:");
    }

    @Override
    public void render(){
        fpsLimiter.sync();

        gameRenderer.render();

        if(Glit.isDown(Key.R))
            resourceManager.reload();

        if(Glit.isDown(options.getKey(KeyMapping.FULLSCREEN)))
            Glit.window().toggleFullscreen();

        if(Glit.isDown(options.getKey(KeyMapping.SCREENSHOT)))
            takeScreenshot();
    }

    @Override
    public void resize(int width, int height){
        gameRenderer.resize(width, height);
        screenManager.resize(width, height);
        ingameGui.resize(width, height);
    }

    @Override
    public void dispose(){
        integratedServer.stop();
        gameRenderer.dispose();
        resourceManager.dispose();
        screenManager.dispose();
        musicManager.dispose();
        audioManager.dispose();
    }


    public Options getOptions(){
        return options;
    }

    public LanguageManager getLanguageManager(){
        return languageManager;
    }

    public GameRenderer getGameRenderer(){
        return gameRenderer;
    }

    public IntegratedServer getIntegratedServer(){
        return integratedServer;
    }

    public ClientGame getGame(){
        return clientGame;
    }

    public ResourceManager getResourceManager(){
        return resourceManager;
    }

    public ScreenManager getScreenManager(){
        return screenManager;
    }

    public IngameGUI getIngameGui(){
        return ingameGui;
    }

    public Sync getFpsLimiter(){
        return fpsLimiter;
    }

    public MusicManager getMusicManager(){
        return musicManager;
    }

    public AudioManager getAudioManager(){
        return audioManager;
    }

    public PlayerProfile getClientProfile(){
        return clientProfile;
    }


    private void takeScreenshot(){
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh.mm.ss");

        String date = dateTimeFormatter.format(ZonedDateTime.now());

        FileHandle file = new FileHandle(Session.GAME_DIR_PATH + "screenshots/" + date + ".png");
        file.mkdirs();

        ScreenUtils.saveScreenshot(file.getPath());
    }

}
