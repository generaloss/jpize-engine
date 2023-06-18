package pize.tests.minecraft.game;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.util.ScreenUtils;
import pize.io.glfw.Key;
import pize.tests.minecraft.game.audio.AudioManager;
import pize.tests.minecraft.game.audio.MusicManager;
import pize.tests.minecraft.game.gui.screen.ScreenManager;
import pize.tests.minecraft.game.gui.screen.screens.*;
import pize.tests.minecraft.game.lang.LanguageInfo;
import pize.tests.minecraft.game.lang.LanguageManager;
import pize.tests.minecraft.game.options.KeyMapping;
import pize.tests.minecraft.game.options.Options;
import pize.tests.minecraft.game.resources.ResourceManager;
import pize.tests.minecraft.utils.log.Logger;
import pize.util.time.Sync;

import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Session implements Renderer{
    
    public static final String GAME_DIR_PATH = System.getProperty("user.home") + "/minecraft/";

    private final Options options;
    private final LanguageManager languageManager;
    private final ResourceManager resourceManager;
    private final MusicManager musicManager;
    private final AudioManager audioManager;
    private final ScreenManager screenManager;
    private final IngameGUI ingameGui;
    private final GameRenderer gameRenderer;
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

        ingameGui = new IngameGUI(this);
        gameRenderer = new GameRenderer(this);

        Logger.instance().info("RENDERING:");
    }

    @Override
    public void render(){
        fpsLimiter.sync();

        gameRenderer.render();

        if(Key.R.isDown())
            resourceManager.reload();

        if(options.getKey(KeyMapping.FULLSCREEN).isDown())
            Pize.window().toggleFullscreen();

        if(options.getKey(KeyMapping.SCREENSHOT).isDown())
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


    private void takeScreenshot(){
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh.mm.ss");

        String date = dateTimeFormatter.format(ZonedDateTime.now());

        Resource resource = new Resource(Session.GAME_DIR_PATH + "screenshots/" + date + ".png");
        resource.mkDirs();

        ScreenUtils.saveScreenshot(resource.getPath());
    }

}
