package megalul.projectvostok.client.net;

import megalul.projectvostok.client.NetClientGame;
import pize.net.NetChannel;
import pize.net.tcp.TcpListener;

public class ClientPacketHandler implements TcpListener<byte[]>{ //} implements NetListener<byte[]>{
    
    private final NetClientGame gameOF;
    
    public ClientPacketHandler(NetClientGame gameOF){
        this.gameOF = gameOF;
    }
    
    public NetClientGame getGameOf(){
        return gameOF;
    }
    
    
    @Override
    public void received(byte[] data, NetChannel<byte[]> sender){
        /*
        PlayerProfile profile = gameOF.getSessionOf().getProfile();
        
        switch(packet.getClass().getSimpleName().substring(6)){
            case "Disconnect" ->{
                PacketDisconnect disconnect = (PacketDisconnect) packet;
                
                gameOF.disconnect();
                System.out.println("[CLIENT]: соединение прервано: " + disconnect.reasonComponent);
            }
            
            case "EncryptStart" ->{
                PacketEncryptStart encryptStart = (PacketEncryptStart) packet;
                
                PublicRSA publicServerKey = new PublicRSA();
                publicServerKey.set(encryptStart.publicServerKey);
                
                byte[] encryptedClientKey = publicServerKey.encrypt(gameOF.getEncryptKey().getKey().getEncoded());
                gameOF.sendPacket(new PacketEncryptEnd(profile.getName(), encryptedClientKey));

                // * шифрование *
                
                gameOF.sendPacket(new PacketAuth(profile.getName(), gameOF.getSessionOf().getSessionToken()));
            }
            
            case "Ping" ->{
                PacketPing ping = (PacketPing) packet;
                
                long delay = System.currentTimeMillis() - ping.timeMillis;
                System.out.println("[CLIENT]: Server ping: " + delay);
            }
        }
        */
    }
    
    @Override
    public void connected(NetChannel<byte[]> channel){
    
    }
    
    @Override
    public void disconnected(NetChannel<byte[]> channel){
    
    }
    
}
