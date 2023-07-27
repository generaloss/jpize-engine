package pize.tests.minecraftosp;

import pize.tests.minecraftosp.server.Server;

public class ServerMain{
    
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
