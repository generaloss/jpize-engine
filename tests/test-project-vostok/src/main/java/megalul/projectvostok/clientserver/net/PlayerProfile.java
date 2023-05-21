package megalul.projectvostok.clientserver.net;

import pize.util.StringUtils;

import java.util.UUID;

public class PlayerProfile{
    
    private final String name;
    private final UUID uuid;
    
    public PlayerProfile(String name){
        this.name = name;
        uuid = UUID.randomUUID();
    }
    
    public String getName(){
        return name;
    }
    
    public UUID getUUID(){
        return uuid;
    }
    
    
    public static boolean isNameValid(String name){
        return !StringUtils.isBlank(name);
    }
    
}
