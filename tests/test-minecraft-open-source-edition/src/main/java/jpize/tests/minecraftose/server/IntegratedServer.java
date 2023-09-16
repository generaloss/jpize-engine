package jpize.tests.minecraftose.server;

import jpize.tests.minecraftose.Minecraft;

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
