package pize.tests.voxelgame.client.options;

import pize.Pize;
import pize.files.Resource;
import pize.io.glfw.Key;
import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.main.net.PlayerProfile;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.util.io.FastReader;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Options{

    public static int UNLIMITED_FPS_SETTING_THRESHOLD = 256;


    private final VoxelGame session;
    private final Resource resOptions;
    
    private String host = "0.0.0.0:22854";
    private String playerName = PlayerProfile.genFunnyName();
    private final Map<KeyMapping, Key> keyMappings;
    private int fov = 70;
    private int renderDistance = 7;
    private int maxFramerate = 0;
    private boolean fullscreen = false;
    private boolean showFps = false;
    private float mouseSensitivity = 1F;
    private float brightness = 0.5F;
    private boolean firstPersonModel = false;
    

    public Options(VoxelGame session, String gameDirPath){
        this.session = session;

        this.keyMappings = new HashMap<>();
        this.resOptions = new Resource(gameDirPath + "options.txt", true);
        this.resOptions.create();
    }
    

    public void load(){
        FastReader reader = resOptions.getReader();

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
                    case "remote" -> {
                        switch(key){
                            case "host" -> setRemoteHost(value);
                        }
                    }
                    case "player" -> {
                        switch(key){
                            case "name" -> setPlayerName(value);
                            case "firstPersonModel" -> setFirstPersonModel(Boolean.parseBoolean(value));
                        }
                    }
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
        PrintStream out = resOptions.getWriter();
        
        out.println("remote.host : " + host);
        out.println("player.name : " + playerName);
        out.println("player.firstPersonModel : " + firstPersonModel);
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
    
    
    public String getPlayerName(){
        return playerName;
    }
    
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    
    
    public String getHost(){
        return host;
    }
    
    public void setRemoteHost(String host){
        this.host = host;
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
        
        final GameCamera camera = session.getGame().getCamera();
        if(camera != null)
            camera.setFov(fov);
    }


    public int getRenderDistance(){
        return renderDistance;
    }

    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
        
        final GameCamera camera = session.getGame().getCamera();
        if(camera != null)
            camera.setDistance(renderDistance);
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
        session.getController().getPlayerController().getRotationController().setSensitivity(mouseSensitivity);
    }
    
    
    public boolean isFirstPersonModel(){
        return firstPersonModel;
    }
    
    public void setFirstPersonModel(boolean firstPersonModel){
        this.firstPersonModel = firstPersonModel;
    }

}
