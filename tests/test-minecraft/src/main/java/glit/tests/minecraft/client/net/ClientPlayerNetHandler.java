package glit.tests.minecraft.client.net;

import glit.net.tcp.TcpClient;
import glit.tests.minecraft.client.game.ClientGame;
import glit.tests.minecraft.server.server.ServerAddress;
import glit.tests.minecraft.utils.log.Logger;

public class ClientPlayerNetHandler{

    private final ClientGame worldHolderOf;
    private TcpClient netClient;

    public ClientPlayerNetHandler(ClientGame worldHolderOf){
        this.worldHolderOf = worldHolderOf;
    }

    public void join(ServerAddress address){
        Logger.instance().info("Joining to server {}",address);

        netClient = new TcpClient( packet->{
            //if(packet.getClass() == Packet.getClass()){
            //    worldHolderOf.getWorld().getWorldInfo().getSpawnPosition().set(packet);
            //}
        });

        worldHolderOf.createWorldObject();
    }

    public void disconnect(){
        netClient.disconnect();
    }

}
