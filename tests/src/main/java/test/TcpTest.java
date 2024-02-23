package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.font.FontLoader;
import jpize.net.tcp.TcpClient;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.TcpServer;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.net.tcp.packet.PacketHandler;
import jpize.sdl.input.Key;
import jpize.util.Utils;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import jpize.util.math.Maths;

import java.io.IOException;
import java.util.Stack;

public class TcpTest extends JpizeApplication{

    public static final String DDOS_MSG = "DDOS-";

    private final TcpServer server;
    private final TcpClient client_1;
    private final TcpClient client_2;
    private final Stack<MsgPacket> packets;

    private int tx, rx;

    public TcpTest(){
        this.packets = new Stack<>();
        this.server = createServer(((packet) -> {
            packets.add(packet);
            rx++;
        }));
        this.client_1 = createClient(((packet) -> { }));
        this.client_2 = createClient(((packet) -> { }));
        new Thread(() -> {
            while(Jpize.context().isAlive()){
                if(!packets.isEmpty()){
                    final MsgPacket packet = packets.pop();
                    Utils.delayElapsedMillis(500);
                    System.out.println(packet.msg + " available: " + packets.size());
                }else
                    Thread.onSpinWait();
            }
        }).start();

        System.out.println(this.client_1.getConnection().getLocalPort());
    }

    @Override
    public void update(){
        if(Key.ESCAPE.isDown()) Jpize.exit();
        if(Key.E.isDown()){
            new Thread(() -> client_1.send(new MsgPacket(DDOS_MSG, 100000000)));
            tx++;
        }
        if(Key.S.isDown()){
            client_2.send(new MsgPacket("" + Maths.random(0, 9), 1));
            tx++;
        }
    }

    @Override
    public void render(){
        Gl.clearColorBuffer();
        FontLoader.getDefaultBold().drawText("Tx / Rx / Balance: " + tx + " / " + rx + " / " + (tx - rx), 100, 100);
    }


    private static class MsgPacket extends IPacket<MsgHandler> {
        public String msg;
        private int num;
        public MsgPacket(){ }
        public MsgPacket(String msg, int num){
            this.msg = msg;
            this.num = num;
        }

        public void write(JpizeOutputStream stream) throws IOException{
            stream.writeInt(num);
            for(int i = 0; i < num; i++)
                stream.writeUTF(msg);
        }
        public void read(JpizeInputStream stream) throws IOException{
            num = stream.readInt();
            for(int i = 0; i < num; i++)
                msg = stream.readUTF();
        }
        public void handle(MsgHandler handler){
            handler.handleMessage(this);
        }
    }

    private interface MsgHandler extends PacketHandler{
        void handleMessage(MsgPacket packet);
    }

    private TcpServer createServer(MsgHandler handler){
        PacketDispatcher pd = new PacketDispatcher();
        pd.register(MsgPacket.class);

        TcpServer server = new TcpServer(new TcpListener(){
            public void received(byte[] bytes, TcpConnection sender){
                pd.handlePacket(bytes, handler);
            }
            public void connected(TcpConnection connection){ }
            public void disconnected(TcpConnection connection){ }
        });
        server.run(9159);
        return server;
    }

    private TcpClient createClient(MsgHandler handler){
        PacketDispatcher pd = new PacketDispatcher();
        pd.register(MsgPacket.class);

        TcpClient client = new TcpClient(new TcpListener(){
            public void received(byte[] bytes, TcpConnection sender){
                pd.handlePacket(bytes, handler);
            }
            public void connected(TcpConnection connection){ }
            public void disconnected(TcpConnection connection){ }
        });
        client.connect("localhost", 9159);
        return client;
    }

}
