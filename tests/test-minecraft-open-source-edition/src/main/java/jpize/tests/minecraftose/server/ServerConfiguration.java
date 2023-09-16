package jpize.tests.minecraftose.server;

import jpize.tests.minecraftose.main.Version;

public class ServerConfiguration{
    
    private String address;
    private int port;
    private Version version;
    private String defaultLevelName;
    private int maxRenderDistance;
    
    public void loadDefaults(){
        port = 22854;
        address = "0.0.0.0";
        version = new Version();
        defaultLevelName = "overworld";
        maxRenderDistance = 6;
    }
    
    public String getAddress(){
        return address;
    }
    
    public int getPort(){
        return port;
    }
    
    public Version getVersion(){
        return version;
    }
    
    public String getDefaultLevelName(){
        return defaultLevelName;
    }
    
    public int getMaxRenderDistance(){
        return maxRenderDistance;
    }
    
}
