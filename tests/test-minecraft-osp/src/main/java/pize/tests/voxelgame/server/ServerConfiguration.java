package pize.tests.voxelgame.server;

import pize.tests.voxelgame.main.Version;

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
        defaultLevelName = "world1";
        maxRenderDistance = 12;
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
