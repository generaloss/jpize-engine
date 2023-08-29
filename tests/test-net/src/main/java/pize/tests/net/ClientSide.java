package pize.tests.net;

import pize.Jize;
import pize.gl.Gl;
import pize.glfw.key.Key;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.io.context.ContextAdapter;
import pize.io.context.ContextBuilder;
import pize.math.vecmath.vector.Vec2f;
import pize.net.security.KeyAES;
import pize.net.security.PublicRSA;
import pize.net.tcp.TcpClient;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;
import pize.tests.net.packet.EncodePacket;
import pize.tests.net.packet.MessagePacket;
import pize.tests.net.packet.PingPacket;
import pize.util.io.TextProcessor;

import java.util.StringJoiner;

public class ClientSide extends ContextAdapter implements TcpListener{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Net - client")
                .size(1280, 720)
                .create()
                .init(new ClientSide());

        Jize.runContexts();
    }
    
    private TcpClient client;
    private KeyAES key;
    
    private TextureBatch batch;
    private BitmapFont font;
    private TextProcessor text;
    
    @Override
    public void init(){
        batch = new TextureBatch();
        font = FontLoader.loadFnt("default.fnt");
        font.setScale(3);
        text = new TextProcessor(false);
        
        key = new KeyAES(256);
        client = new TcpClient(this);
        client.connect("localhost", 5454);
        
        client.getConnection().setTcpNoDelay(true);
    }
    
    @Override
    public void render(){
        if(Key.ENTER.isDown()){
            new MessagePacket(text.getString()).write(client.getConnection());
            text.removeLine();
        }
        
        if(Key.LEFT_CONTROL.isPressed() && Key.P.isDown())
            new PingPacket(System.nanoTime()).write(client.getConnection());
        
        if(Key.ESCAPE.isDown())
            Jize.exit();
        
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
    
    
    @Override
    public void received(byte[] bytes, TcpConnection sender){
        try{
            final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
            if(packetInfo == null){
                System.out.println("   received not packet");
                return;
            }
            
            switch(packetInfo.getPacketID()){
                case MessagePacket.PACKET_TYPE_ID -> {
                    final MessagePacket packet = packetInfo.readPacket(new MessagePacket());
                    System.out.println("   message: " + packet.getMessage());
                }
                
                case PingPacket.PACKET_TYPE_ID -> {
                    final PingPacket packet = packetInfo.readPacket(new PingPacket());
                    System.out.println("   ping: " + ((System.nanoTime() - packet.getTime()) / 1000000F) + " ms");
                }
                
                case EncodePacket.PACKET_TYPE_ID -> {
                    final EncodePacket packet = packetInfo.readPacket(new EncodePacket());
                    
                    final PublicRSA serverKey = new PublicRSA(packet.getKey());
                    System.out.println("   server's public key received");
                    
                    new EncodePacket(serverKey.encrypt(key.getKey().getEncoded())).write(sender);
                    System.out.println("   send encrypted key");
                    
                    sender.encode(key);
                    System.out.println("   encoded with key (hash): " + key.getKey().hashCode());
                    
                    new PingPacket(System.nanoTime()).write(client.getConnection());
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(TcpConnection connection){
        System.out.println("Connected {");
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        System.out.println("   Client disconnected\n}");
    }
    
}
