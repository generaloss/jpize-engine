package jpize.tests.net;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.vecmath.vector.Vec2f;
import jpize.net.security.KeyAES;
import jpize.net.security.PublicRSA;
import jpize.net.tcp.TcpClient;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.tests.net.handler.MyPacketHandler;
import jpize.tests.net.packet.EncodePacket;
import jpize.tests.net.packet.MessagePacket;
import jpize.tests.net.packet.PingPacket;
import jpize.util.io.TextProcessor;

import java.util.StringJoiner;

public class ClientSide extends JpizeApplication implements TcpListener{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Net - client")
                .size(1280, 720)
                .register()
                .setAdapter(new ClientSide());

        Jpize.runContexts();
    }

    private TcpClient client;
    private KeyAES key;
    private MyPacketHandler handler;
    private PacketDispatcher dispatcher;

    private TextureBatch batch;
    private BitmapFont font;
    private TextProcessor text;
    
    @Override
    public void init(){
        batch = new TextureBatch();
        font = FontLoader.loadFnt("default.fnt");
        font.setScale(3);
        text = new TextProcessor(false);

        // Create handler with methods for handle packets (EncodePacket, MessagePacket, PingPacket)
        handler = new MyPacketHandler(){
            public void encode(EncodePacket packet){
                final PublicRSA serverKey = new PublicRSA(packet.getKey());
                System.out.println("   server's public key received");

                client.getConnection().send(new EncodePacket(serverKey.encrypt(key.getKey().getEncoded())));
                System.out.println("   send encrypted key");

                client.getConnection().encode(key);
                System.out.println("   encoded with key (hash): " + key.getKey().hashCode());

                client.getConnection().send(new PingPacket(System.nanoTime()));
            }

            public void message(MessagePacket packet){
                System.out.println("   message: " + packet.getMessage());
            }

            public void ping(PingPacket packet){
                System.out.println("   ping: " + ((System.nanoTime() - packet.getTime()) / 1000000F) + " ms");
            }
        };

        // Register packets
        dispatcher = new PacketDispatcher();
        dispatcher.register(EncodePacket .PACKET_ID, EncodePacket .class);
        dispatcher.register(MessagePacket.PACKET_ID, MessagePacket.class);
        dispatcher.register(PingPacket   .PACKET_ID, PingPacket   .class);

        // Key for encoding
        key = new KeyAES(256);

        // Connect to the Server
        client = new TcpClient(this);
        client.connect("localhost", 5454);
        client.getConnection().setTcpNoDelay(true);
    }

    @Override
    public void received(byte[] bytes, TcpConnection sender){
        // Handle packet with handler
        dispatcher.handlePacket(bytes, handler);
    }

    @Override
    public void connected(TcpConnection connection){
        System.out.println("Connected {");
    }

    @Override
    public void disconnected(TcpConnection connection){
        System.out.println("   Client disconnected\n}");
    }
    
    @Override
    public void render(){
        final TcpConnection connection = client.getConnection();

        if(Key.ENTER.isDown()){
            connection.send(new MessagePacket(text.getString()));
            text.removeLine();
        }
        
        if(Key.LEFT_CONTROL.isPressed() && Key.P.isDown())
            connection.send(new PingPacket(System.nanoTime()));
        
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();
        
        // Draw background
        final Vec2f bounds = font.getBounds(text.getString());
        batch.drawQuad(0.1, 0.15, 0.2, 1,  50, 10, bounds.x, bounds.y);
        batch.drawQuad(0.3, 0.45, 0.5, 1,  0, 10, 50, bounds.y);
        
        // Draw line numbers
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        for(int i = 0; i < text.getLines().size(); i++)
            lineNumbersJoiner.add(String.valueOf(i + 1));
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);
        
        // Draw text
        font.drawText(batch, text.getString(), 50, 10);
        
        // Draw cursor
        if(text.isCursorRender()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = font.getBounds(text.getString()).y - (text.getCursorY() + 1) * font.getLineAdvanceScaled();
            final float cursorX = font.getLineWidth(currentLine.substring(0, text.getCursorX()));
            batch.drawQuad(1, 1, 1, 1,  50 + cursorX, 10 + cursorY, 2, font.getLineAdvanceScaled());
        }
        
        batch.end();
    }
    
    @Override
    public void dispose(){
        text.dispose();
        batch.dispose();
        font.dispose();
    }
    
}
