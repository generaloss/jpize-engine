package pize.tests.voxelgame;

import pize.tests.voxelgame.server.LocalServer;

public class ServerMain{
    
    public static void main(String[] args){
        new LocalServer().run();
        
        while(!Thread.interrupted());
    }
    
}
