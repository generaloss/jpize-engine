package pize.tests.minecraft.client.net;

import pize.net.tcp.TcpClient;
import pize.tests.minecraft.client.game.ClientGame;
import pize.tests.minecraft.server.server.ServerAddress;
import pize.tests.minecraft.utils.log.Logger;

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
