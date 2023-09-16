package jpize.tests.minecraftose;

import jpize.tests.minecraftose.server.Server;

public class DedicatedServer{
    
    public static void main(String[] args){
        final Server server = new Server(){
            @Override
            public void run(){
                super.run();
            }
        };
        
        server.run();
        
        while(!Thread.interrupted());
    }
    
}
