package pize.tests.minecraft.client.net.packets;

public interface INetPacketHandler{

    void onDisconnect();

    NetManager getNetworkManager();

}