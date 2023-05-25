package pize.tests.minecraft.game.options;

import pize.Pize;
import pize.audio.Audio;
import pize.files.Resource;
import pize.io.glfw.Key;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.screen.screens.VideoSettingsScreen;
import pize.util.io.FastReader;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Options{

    private final Session session;

    private final Resource resOptions;

    private final Map<SoundCategory, Float> soundVolumes;
    private final Map<KeyMapping, Key> keyMappings;
    private String language = "en_us";
    private int fov = 90;
    private int renderDistance = 8;
    private int mipmapLevels = 1;
    private int maxFramerate = Pize.monitor().getRefreshRate();
    private boolean fullscreen = false;
    private boolean showFps = false;
    private float mouseSensitivity = 0.5F;
    private String audioDevice = Pize.audio().getCurrent().getName();


    public Options(Session session, String gameDirPath){
        this.session = session;

        soundVolumes = new HashMap<>();
        keyMappings = new HashMap<>();

        resOptions = new Resource(gameDirPath + "options.txt", true);
        resOptions.mkParentDirs();
        resOptions.create();
        
        init();
    }


    private void init(){
        load();
        
        List<String> availableAudioDevices = Audio.getAvailableDevices();
        if(availableAudioDevices != null && availableAudioDevices.contains(audioDevice))
            Pize.audio().getDevice(audioDevice).makeCurrent();
        else{
            audioDevice = Pize.audio().getCurrent().getName();
            save();
        }

        Pize.window().setFullscreen(fullscreen);
        setMaxFramerate(maxFramerate, VideoSettingsScreen.MAX_SETTING_FRAMERATE);
    }


    private void load(){
        FastReader in = resOptions.getReader();

        while(in.hasNext()){
            String[] parts = in.nextLine().split(" : ");
            if(parts.length != 2)
                continue;

            String value = parts[1];

            parts = parts[0].split("\\.");
            if(parts.length != 2)
                continue;

            String category = parts[0];
            String key = parts[1];

            try{
                switch(category){
                    case "language" -> {
                        switch(key){
                            case "code" -> language = value;
                        }
                    }
                    case "graphics" -> {
                        switch(key){
                            case "fov" -> fov = Integer.parseInt(value);
                            case "renderDistance" -> renderDistance = Integer.parseInt(value);
                            case "mipmapLevels" -> mipmapLevels = Integer.parseInt(value);
                            case "maxFramerate" -> maxFramerate = Integer.parseInt(value);
                            case "fullscreen" -> fullscreen = Boolean.parseBoolean(value);
                            case "showFps" -> showFps = Boolean.parseBoolean(value);
                        }
                    }
                    case "key" -> keyMappings.put(KeyMapping.valueOf(key.toUpperCase()), Key.valueOf(value.toUpperCase()));
                    case "control" -> {
                        switch(key){
                            case "mouseSensitivity" -> mouseSensitivity = Float.parseFloat(value);
                        }
                    }
                    case "sound" -> soundVolumes.put(SoundCategory.valueOf(key.toUpperCase()), Float.parseFloat(value));
                    case "audio" -> {
                        switch(key){
                            case "device" -> audioDevice = value;
                        }
                    }

                }
            }catch(IllegalArgumentException ignored){ }
        }

        in.close();
    }

    public void save(){
        PrintStream out = resOptions.getWriter();

        out.println("language.code : " + language);

        out.println("graphics.fov : " + fov);
        out.println("graphics.renderDistance : " + renderDistance);
        out.println("graphics.mipmapLevels : " + mipmapLevels);
        out.println("graphics.maxFramerate : " + maxFramerate);
        out.println("graphics.fullscreen : " + fullscreen);
        out.println("graphics.showFps : " + showFps);

        for(KeyMapping keyType: KeyMapping.values())
            out.println("key." + keyType.toString().toLowerCase() + " : " + keyMappings.getOrDefault(keyType, keyType.getDefaultKey()).toString().toLowerCase());

        out.println("control.mouseSensitivity : " + mouseSensitivity);

        for(SoundCategory soundCategory: SoundCategory.values())
            out.println("sound." + soundCategory.toString().toLowerCase() + " : " + soundVolumes.getOrDefault(soundCategory, soundCategory.getDefaultVolume()));

        out.println("audio.device : " + audioDevice);

        out.close();
    }


    public String getLanguage(){
        return language;
    }

    public void setLanguage(String language){
        this.language = language;
    }


    public Key getKey(KeyMapping keyType){
        return keyMappings.getOrDefault(keyType, keyType.getDefaultKey());
    }

    public void setKey(KeyMapping keyType, Key key){
        keyMappings.put(keyType, key);
    }


    public int getFov(){
        return fov;
    }

    public void setFov(int fov){
        this.fov = fov;
    }


    public int getRenderDistance(){
        return renderDistance;
    }

    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
    }


    public int getMipmapLevels(){
        return mipmapLevels;
    }

    public void setMipmapLevels(int mipmapLevels){
        this.mipmapLevels = mipmapLevels;
    }


    public int getMaxFramerate(){
        return maxFramerate;
    }

    public void setMaxFramerate(int maxFramerate, int unlimitedThreshold){
        this.maxFramerate = maxFramerate;
        session.getFpsLimiter().setFps(maxFramerate);

        session.getFpsLimiter().enable(maxFramerate > 0 && maxFramerate < unlimitedThreshold);
        Pize.window().setVsync(maxFramerate == 0);
    }


    public boolean isFullscreen(){
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;

        Pize.window().setFullscreen(fullscreen);
    }


    public boolean isShowFps(){
        return showFps;
    }

    public void setShowFps(boolean showFps){
        this.showFps = showFps;

        session.getIngameGui().showFps(showFps);
    }


    public float getMouseSensitivity(){
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity){
        this.mouseSensitivity = mouseSensitivity;
    }


    public float getSoundVolume(SoundCategory category){
        return soundVolumes.getOrDefault(category, category.getDefaultVolume());
    }

    public void setSoundVolume(SoundCategory category, float volume){
        soundVolumes.put(category, volume);
    }


    public String getAudioDevice(){
        return audioDevice;
    }

    public void setAudioDevice(String audioDevice){
        this.audioDevice = audioDevice;

        session.getAudioManager().setDevice(audioDevice);
    }

}
