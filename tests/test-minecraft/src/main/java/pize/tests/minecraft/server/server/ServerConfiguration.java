package pize.tests.minecraft.server.server;

public class ServerConfiguration implements IServerConfiguration{

    private final ServerAddress address;
    private final String motd;
    private final int maxViewDistance;
    private final int maxPlayers;

    public ServerConfiguration(String filePath){
        address = new ServerAddress("ip load from config file");
        motd = "load from config file";
        maxViewDistance = 27;
        maxPlayers = 54;
    }


    @Override
    public ServerAddress getAddress(){
        return address;
    }

    @Override
    public String getMotd(){
        return motd;
    }

    @Override
    public int getMaxViewDistance(){
        return maxViewDistance;
    }

    @Override
    public int getMaxPlayers(){
        return maxPlayers;
    }

}
