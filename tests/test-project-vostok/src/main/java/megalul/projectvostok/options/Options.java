package megalul.projectvostok.options;

import glit.Pize;
import glit.files.FileHandle;
import glit.io.glfw.Key;
import glit.util.io.FastReader;
import megalul.projectvostok.Main;
import megalul.projectvostok.chunk.ChunkUtils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Options{

    public static int UNLIMITED_FPS_SETTING_THRESHOLD = 256;


    private final Main session;
    private final FileHandle optionsFile;

    private final Map<KeyMapping, Key> keyMappings;
    private int fov = 90;
    private int renderDistance = 16;
    private int maxFramerate = 0;
    private boolean fullscreen = false;
    private boolean showFps = false;
    private float mouseSensitivity = 0.5F;

    public Options(Main session, String gameDirPath){
        this.session = session;

        keyMappings = new HashMap<>();

        optionsFile = new FileHandle(gameDirPath + "options.txt", true);
        optionsFile.create();

        load();
        init();
    }


    private void init(){
        Pize.window().setFullscreen(fullscreen);
        setMaxFramerate(maxFramerate);
    }

    private void load(){
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
        }catch(Throwable ignored){ }
    }

    public void save(){
        PrintStream out = optionsFile.writer();

        out.println("graphics.fov : " + fov);
        out.println("graphics.renderDistance : " + renderDistance);
        out.println("graphics.maxFramerate : " + maxFramerate);
        out.println("graphics.fullscreen : " + fullscreen);
        out.println("graphics.showFps : " + showFps);

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
        session.getCamera().setFov(fov);
    }


    public int getRenderDistance(){
        return renderDistance;
    }

    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
        session.getCamera().setFar((renderDistance + 0.5F) * ChunkUtils.SIZE * 2);
    }


    public int getMaxFramerate(){
        return maxFramerate;
    }

    public void setMaxFramerate(int maxFramerate){
        this.maxFramerate = maxFramerate;
        session.getFpsSync().setFps(maxFramerate);

        session.getFpsSync().enable(maxFramerate > 0 && maxFramerate < UNLIMITED_FPS_SETTING_THRESHOLD);
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


    public float getMouseSensitivity(){
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity){
        this.mouseSensitivity = mouseSensitivity;
    }

}
