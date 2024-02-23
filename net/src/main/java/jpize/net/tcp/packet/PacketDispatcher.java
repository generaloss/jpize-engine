package jpize.net.tcp.packet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PacketDispatcher{

    private final HashMap<Integer, Class<? extends IPacket<? extends PacketHandler>>> packetClassMap;

    public PacketDispatcher(){
        packetClassMap = new HashMap<>();
    }

    public void register(Class<? extends IPacket<? extends PacketHandler>> packetClass){
        packetClassMap.put(packetClass.getSimpleName().hashCode(), packetClass);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean handlePacket(byte[] bytes, PacketHandler handler){
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return false;

        final Class <? extends IPacket<? extends PacketHandler>> packetClass = packetClassMap.get(packetInfo.getPacketID());
        if(packetClass == null)
            return false;

        try{
            final Constructor<? extends IPacket<? extends PacketHandler>> constructor = packetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            final IPacket packetInstance = constructor.newInstance();

            packetInfo.readPacket(packetInstance);
            packetInstance.handle(handler);
            return true;

        }catch(IllegalAccessException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": Constructor access");
        }catch(IllegalArgumentException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": Illegal constructor arguments");
        }catch(InstantiationException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": Instantiation error");
        }catch(NoSuchMethodException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": No such constructor (packet class is not static or constructor is not exists)");
        }catch(SecurityException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": Constructor security error");
        }catch(InvocationTargetException e){
            throw new RuntimeException(packetClass.getSimpleName() + ": Invocation constructor exception (" + e.getCause() + ")");
        }
    }

}