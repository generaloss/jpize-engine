package jpize.tests.net.handler;

import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.net.packet.EncodePacket;
import jpize.tests.net.packet.MessagePacket;
import jpize.tests.net.packet.PingPacket;

public interface MyPacketHandler extends PacketHandler{

    void encode(EncodePacket packet);

    void message(MessagePacket packet);

    void ping(PingPacket packet);

}
