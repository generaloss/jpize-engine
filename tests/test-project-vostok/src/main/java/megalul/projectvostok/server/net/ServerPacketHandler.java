package megalul.projectvostok.server.net;

import megalul.projectvostok.server.Server;
import pize.net.security.KeyRSA;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpListener;

public class ServerPacketHandler implements TcpListener{
    
    private final Server serverOF;
    private final KeyRSA encryptInitKey;
    
    public ServerPacketHandler(Server serverOF){
        this.serverOF = serverOF;
        
        encryptInitKey = new KeyRSA(1024);
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    
    @Override
    public void received(byte[] data, TcpByteChannel sender){
        /*
        PlayerList playerList = serverOF.getPlayerList();
        
        switch(packet.getClass().getSimpleName().substring(6)){
            case "RenderDistance" ->{
                PacketRenderDistance renderDistance = (PacketRenderDistance) packet;
                playerList.getOnlinePlayer(renderDistance.playerName).setRenderDistance(renderDistance.renderDistance);
            }
            
            case "Login" ->{
                PacketLogin login = (PacketLogin) packet;
                
                if(login.clientVersionID != serverOF.getConfiguration().getVersion().getID()){
                    sender.send(new PacketDisconnect("Сервер не доступен на данной версии"));
                    break;
                }
                
                if(!PlayerProfile.isNameValid(login.profileName)){
                    sender.send(new PacketDisconnect("Недопустимое имя"));
                    break;
                }
                
                if(playerList.isPlayerOnline(login.profileName)){
                    sender.send(new PacketDisconnect("Этот игрок уже играет на сервере"));
                    break;
                }
                
                playerList.connectOnlinePlayer(login.profileName);
                
                sender.send(new PacketEncryptStart(encryptInitKey.getPublic().getKey()));
                
                System.out.println("[SERVER]: Player '" + login.profileName + "' logged (version ID: " + login.clientVersionID + ")");
            }
            
            case "EncryptEnd" ->{
                PacketEncryptEnd encryptEnd = (PacketEncryptEnd) packet;
                
                KeyAES decryptedClientKey = new KeyAES();
                decryptedClientKey.set(encryptInitKey.decrypt(encryptEnd.encryptedClientKey));
                
                OnlinePlayer player = playerList.getOnlinePlayer(encryptEnd.profileName);
                player.setClientKey(decryptedClientKey);
                
                // * шифрование *
                
                // * включить режим ожидания аутентификации для данного клиента *
                
                System.out.println("[SERVER]: Player '" + encryptEnd.profileName + "' encrypted");
            }
            
            case "Auth" ->{
                PacketAuth auth = (PacketAuth) packet;
                
                if(!"54_54-iWantPizza-54_54".equals(auth.accountSessionToken)){
                    sender.send(new PacketDisconnect("Недействительная сессия"));
                    break;
                }
                
                System.out.println("[SERVER]: Player '" + auth.profileName + "' is licensed");
                
                //     * выключить режим ожидания аутентификации для данного клиента *
            }
            
            case "Ping" ->{
                sender.send(packet);
            }
        }
        */
    }
    
    @Override
    public void connected(TcpByteChannel channel){ }
    
    @Override
    public void disconnected(TcpByteChannel channel){ }
    
}
