package megalul.projectvostok.options;

import pize.Pize;
import pize.files.FileHandle;
import pize.io.glfw.Key;
import pize.util.io.FastReader;
import megalul.projectvostok.Main;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Options{

    public static int UNLIMITED_FPS_SETTING_THRESHOLD = 256;


    private final Main sessionOF;
    private final FileHandle optionsFile;

    private final Map<KeyMapping, Key> keyMappings;
    private int fov = 70;
    private int renderDistance = 16;
    private int maxFramerate = 0;
    private boolean fullscreen = false;
    private boolean showFps = false;
    private float mouseSensitivity = 0.5F;
    private float brightness = 0.5F;

    public Options(Main sessionOF, String gameDirPath){
        this.sessionOF = sessionOF;

        keyMappings = new HashMap<>();

        optionsFile = new FileHandle(gameDirPath + "options.txt", true);
        optionsFile.create();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    

    public void load(){
        FastReader reader = optionsFile.reader();

        try{
            while(reader.hasNext()){
                String[] parts = reader.nextLine().split(" : ");
                if(parts.length != 2)
                    continue;

                String value = parts[1].trim();

                String[] keyParts = parts[0].split("\\.");
                if(keyParts.length != 2)
                    continue;

                String category = keyParts[0];
                String key = keyParts[1];

                // category.key : value
                switch(category){
                    case "graphics" -> {
                        switch(key){
                            case "fov" -> setFOV(Integer.parseInt(value));
                            case "renderDistance" -> setRenderDistance(Integer.parseInt(value));
                            case "maxFramerate" -> setMaxFramerate(Integer.parseInt(value));
                            case "fullscreen" -> setFullscreen(Boolean.parseBoolean(value));
                            case "showFps" -> setShowFPS(Boolean.parseBoolean(value));
                            case "brightness" -> setBrightness(Float.parseFloat(value));
                        }
                    }
                    case "key" -> setKey(KeyMapping.valueOf(key.toUpperCase()), Key.valueOf(value.toUpperCase()));
                    case "control" -> {
                        switch(key){
                            case "mouseSensitivity" -> setMouseSensitivity(Float.parseFloat(value));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save(){
        PrintStream out = optionsFile.writer();

        out.println("graphics.fov : " + fov);
        out.println("graphics.renderDistance : " + renderDistance);
        out.println("graphics.maxFramerate : " + maxFramerate);
        out.println("graphics.fullscreen : " + fullscreen);
        out.println("graphics.showFps : " + showFps);
        out.println("graphics.brightness : " + brightness);

        out.println("control.mouseSensitivity : " + mouseSensitivity);

        for(KeyMapping keyType: KeyMapping.values())
            out.println("key." + keyType.toString().toLowerCase() + " : " + keyMappings.getOrDefault(keyType, keyType.getDefault()).toString().toLowerCase());

        out.close();
    }


    public Key getKey(KeyMapping keyType){
        return keyMappings.getOrDefault(keyType, keyType.getDefault());
    }

    public void setKey(KeyMapping keyType, Key key){
        keyMappings.put(keyType, key);
    }


    public int getFOV(){
        return fov;
    }

    public void setFOV(int fov){
        this.fov = fov;
        sessionOF.getCamera().setFov(fov);
    }


    public int getRenderDistance(){
        return renderDistance;
    }

    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
        sessionOF.getCamera().setDistance(renderDistance);
    }


    public int getMaxFramerate(){
        return maxFramerate;
    }

    public void setMaxFramerate(int maxFramerate){
        this.maxFramerate = maxFramerate;
        sessionOF.getFpsSync().setFps(maxFramerate);

        sessionOF.getFpsSync().enable(maxFramerate > 0 && maxFramerate < UNLIMITED_FPS_SETTING_THRESHOLD);
        Pize.window().setVsync(maxFramerate == 0);
    }


    public boolean isFullscreen(){
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;

        Pize.window().setFullscreen(fullscreen);
    }


    public boolean isShowFPS(){
        return showFps;
    }

    public void setShowFPS(boolean showFps){
        this.showFps = showFps;
    }
    
    
    public float getBrightness(){
        return brightness;
    }
    
    public void setBrightness(float brightness){
        this.brightness = brightness;
    }


    public float getMouseSensitivity(){
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity){
        this.mouseSensitivity = mouseSensitivity;
        sessionOF.getPlayerController().getRotationController().setSensitivity(mouseSensitivity);
    }

}
