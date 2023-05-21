package pize.tests.minecraft.server.server;

import pize.tests.minecraft.utils.log.Logger;
import pize.tests.minecraft.client.game.Session;

public class IntegratedServer extends Server{

    private final IntegratedServerConfiguration configuration;

    public IntegratedServer(Session sessionOf){
        String SERVER_DEFAULT_WORLD_NAME = "World 54";
        final String motd = sessionOf.getClientProfile().name() + " - " + SERVER_DEFAULT_WORLD_NAME;
        configuration = new IntegratedServerConfiguration(motd);
    }


    public void open(int port){
        Logger.instance().info("Open");

        configuration.getAddress().setPort(port);
    }

    @Override
    public IServerConfiguration getConfiguration(){
        return configuration;
    }

}
