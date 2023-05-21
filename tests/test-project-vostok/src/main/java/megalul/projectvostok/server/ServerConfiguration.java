package megalul.projectvostok.server;

import megalul.projectvostok.clientserver.Version;

public class ServerConfiguration{
    
    private String address;
    private int port;
    private Version version;
    private String defaultWorld;
    private int maxRenderDistance;
    
    public void loadDefaults(){
        port = 22854;
        address = "localhost";
        version = new Version();
        defaultWorld = "world1";
        maxRenderDistance = 512;
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
    
    public String getDefaultWorldName(){
        return defaultWorld;
    }
    
    public int getMaxRenderDistance(){
        return maxRenderDistance;
    }
    
}
