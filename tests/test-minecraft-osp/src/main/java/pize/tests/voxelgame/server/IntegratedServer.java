package pize.tests.voxelgame.server;

import pize.tests.voxelgame.Minecraft;

public class IntegratedServer extends Server{
    
    private final Minecraft session;
    
    public IntegratedServer(Minecraft session){
        this.session = session;
        getConfiguration().loadDefaults(); // Load server configuration
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    public void run(){
        super.run();
        System.out.println("[Server]: Integrated server listening on " + getConfiguration().getAddress() + ":" + getConfiguration().getPort());
    }
    
}
