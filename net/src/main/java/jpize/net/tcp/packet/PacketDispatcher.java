package jpize.net.tcp.packet;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class PacketDispatcher{

    private final HashMap<Short, Class<? extends IPacket<? extends PacketHandler>>> packetClassMap;

    public PacketDispatcher(){
        packetClassMap = new HashMap<>();
    }

    public void register(int packetID, Class<? extends IPacket<? extends PacketHandler>> packetClass){
        packetClassMap.put((short) packetID, packetClass);
    }


    public boolean handlePacket(byte[] bytes, PacketHandler handler){
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return false;

        final Class <? extends IPacket<? extends PacketHandler>> packetClass = packetClassMap.get(packetInfo.getPacketID());
        if(packetClass == null)
            return false;

        try{
            final Constructor <? extends IPacket<? extends PacketHandler>> constructor = packetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            final IPacket packetInstance = constructor.newInstance();

            packetInfo.readPacket(packetInstance);
            packetInstance.handle(handler);

            return true;

        }catch(Exception ignored){
            return false;
        }
    }

}