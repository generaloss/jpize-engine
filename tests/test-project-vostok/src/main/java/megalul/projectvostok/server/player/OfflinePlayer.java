package megalul.projectvostok.server.player;

public class OfflinePlayer{
    
    private final String name;
    private final String worldIN;
    
    public OfflinePlayer(String name, String worldIN){
        this.name = name;
        this.worldIN = worldIN;
    }
    
    
    public String getName(){
        return name;
    }
    
    public String getWorldIn(){
        return worldIN;
    }
    
}
