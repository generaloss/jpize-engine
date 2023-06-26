package pize.tests.voxelgame.server;

import pize.tests.voxelgame.VoxelGame;

public class IntegratedServer extends Server{
    
    private final VoxelGame session;
    
    public IntegratedServer(VoxelGame session){
        this.session = session;
        getConfiguration().loadDefaults(); // Load server configuration
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    public void run(){
        super.run();
        System.out.println("[Server]: Integrated server listening on " + getConfiguration().getAddress() + ":" + getConfiguration().getPort());
    }
    
}
