package glit.tests.minecraft.server.server;

public class IntegratedServerConfiguration implements IServerConfiguration{

    private final ServerAddress address;
    private final String motd;
    private final int maxViewDistance;
    private final int maxPlayers;

    public IntegratedServerConfiguration(String motd){
        address = new ServerAddress("192.168.0.101",22854);
        this.motd = motd;
        maxViewDistance = 10;
        maxPlayers = 20;
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
