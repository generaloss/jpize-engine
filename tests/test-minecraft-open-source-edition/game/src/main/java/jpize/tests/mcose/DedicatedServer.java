package jpize.tests.mcose;

import jpize.tests.mcose.server.Server;

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
