package pize.tests.minecraft.server.server;

public interface IServerConfiguration{

    ServerAddress getAddress();

    String getMotd();

    int getMaxViewDistance();

    int getMaxPlayers();

}
