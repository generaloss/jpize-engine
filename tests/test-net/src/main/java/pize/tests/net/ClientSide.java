package pize.tests.net;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.Gl;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.vecmath.tuple.Tuple2f;
import pize.net.security.KeyAES;
import pize.net.security.PublicRSA;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.TcpClient;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;
import pize.tests.net.packet.EncodePacket;
import pize.tests.net.packet.MessagePacket;
import pize.tests.net.packet.PingPacket;
import pize.util.io.TextProcessor;

import java.util.StringJoiner;

public class ClientSide extends AppAdapter implements TcpListener{
    
    public static void main(String[] args){
        Pize.create("Net - client", 1280, 720);
        Pize.run(new ClientSide());
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
        
        client.getChannel().setTcpNoDelay(true);
    }
    
    @Override
    public void render(){
        if(Key.ENTER.isDown()){
            new MessagePacket(text.toString()).write(client.getChannel());
            text.removeLine();
        }
        
        if(Key.LEFT_CONTROL.isPressed() && Key.P.isDown())
            new PingPacket(System.nanoTime()).write(client.getChannel());
        
        if(Key.ESCAPE.isDown())
            Pize.exit();
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();
        
        // Draw background
        Tuple2f bounds = font.getBounds(text.toString());
        batch.setColor(0.1, 0.15, 0.2, 1);
        batch.draw(TextureUtils.quadTexture(), 50, 10, bounds.x, bounds.y);
        batch.setColor(0.3, 0.45, 0.5, 1);
        batch.draw(TextureUtils.quadTexture(), 0, 10, 50, bounds.y);
        batch.resetColor();
        
        // Draw line numbers
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        for(int i = 0; i < text.getLines().size(); i++)
            lineNumbersJoiner.add(String.valueOf(i + 1));
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);
        
        // Draw text
        font.drawText(batch, text.toString(), 50, 10);
        
        // Draw cursor
        if(System.currentTimeMillis() / 500 % 2 == 0 && text.isActive()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = font.getBounds(text.toString()).y - (text.getCursorY() + 1) * font.getScaledLineHeight();
            final float cursorX = font.getLineWidth(currentLine.substring(0, text.getCursorX()));
            batch.draw(TextureUtils.quadTexture(), 50 + cursorX, 10 + cursorY, 2, font.getScaledLineHeight());
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
    public void received(byte[] bytes, TcpChannel sender){
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
                    
                    new PingPacket(System.nanoTime()).write(client.getChannel());
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(TcpChannel channel){
        System.out.println("Connected {");
    }
    
    @Override
    public void disconnected(TcpChannel channel){
        System.out.println("   Client disconnected\n}");
    }
    
}
