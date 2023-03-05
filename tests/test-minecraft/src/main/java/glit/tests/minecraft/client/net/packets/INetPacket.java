package glit.tests.minecraft.client.net.packets;

public interface INetPacket<T extends INetPacketHandler>{

    void readPacketData(PacketData data);

    void writePacketData(PacketData data);

    void processPacket(T handler);

}
