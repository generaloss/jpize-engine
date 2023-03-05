package glit.tests.minecraft.client.net.packets;

public class PacketExample implements INetPacket<IServerPacketHandler>{

    private int someData;

    public PacketExample(int someData){
        this.someData = someData;
    }

    @Override
    public void readPacketData(PacketData data){
        someData = data.read();
    }

    @Override
    public void writePacketData(PacketData data){
        data.put(someData);
    }

    @Override
    public void processPacket(IServerPacketHandler handler){
        handler.onServerEventExample();
    }

}
