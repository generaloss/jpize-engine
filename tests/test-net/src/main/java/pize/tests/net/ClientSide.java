package pize.tests.net;

import pize.Pize;
import pize.activity.ActivityListener;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.Gl;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.vecmath.tuple.Tuple2f;
import pize.net.security.KeyAES;
import pize.net.security.PublicRSA;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpClient;
import pize.net.tcp.TcpListener;
import pize.tests.net.packet.EncodePacket;
import pize.tests.net.packet.MessagePacket;
import pize.tests.net.packet.PingPacket;
import pize.tests.net.packet.Utils;
import pize.util.io.TextProcessor;

import java.io.IOException;
import java.util.StringJoiner;

public class ClientSide implements TcpListener, ActivityListener{
    
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
        text = new TextProcessor(true);
        
        key = new KeyAES(256);
        client = new TcpClient(this);
        client.connect("localhost", 5454);
    }
    
    @Override
    public void render(){
        if(Key.LEFT_CONTROL.isPressed() && Key.Y.isDown())
            text.removeLine();
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();
        
        // Draw background
        Tuple2f bounds = font.getBounds(text.toString());
        batch.setColor(0.1F, 0.15F, 0.2F, 1);
        batch.draw(TextureUtils.quadTexture(), 50, 10, bounds.x, bounds.y);
        batch.setColor(0.3F, 0.45F, 0.5F, 1);
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
    public void resize(int width, int height){
    
    }
    
    @Override
    public void dispose(){
        text.dispose();
        batch.dispose();
        font.dispose();
    }
    
    
    @Override
    public void received(byte[] data, TcpByteChannel sender){
        
        try{
            Utils.receive(data);
            switch(Utils.packetTypeID){
                
                case MessagePacket.PACKET_TYPE_ID -> {
                    final MessagePacket packet = new MessagePacket();
                    packet.read(Utils.stream);
                    System.out.println("   message: " + packet.getMessage());
                }
                
                case PingPacket.PACKET_TYPE_ID -> {
                    final PingPacket packet = new PingPacket();
                    packet.read(Utils.stream);
                    
                    System.out.println("   ping: " + ((System.nanoTime() - packet.getTime()) / 1000000F) + " ms");
                    
                    pize.util.Utils.delayElapsed(1000);
                    final PingPacket ping = new PingPacket(System.nanoTime());
                    ping.write(sender);
                    System.out.println("   1 time: " + String.valueOf(System.currentTimeMillis()).substring(9) );
                }
                
                case EncodePacket.PACKET_TYPE_ID -> {
                    final EncodePacket packet = new EncodePacket();
                    packet.read(Utils.stream);
                    
                    final PublicRSA serverKey = new PublicRSA(packet.getKey());
                    System.out.println("   server's public key received");
                    
                    new EncodePacket(serverKey.encrypt(key.getKey().getEncoded())).write(sender);
                    System.out.println("   send encrypted key");
                    
                    sender.encode(key);
                    System.out.println("   encoded with key (hash): " + key.getKey().hashCode());
                    
                    pize.util.Utils.delayElapsed(100);
                    new PingPacket(System.nanoTime()).write(client.getChannel());
                }
                
                default -> System.out.println("   received: ?Unknown Packet Type? (" + Utils.packetTypeID + ")");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void connected(TcpByteChannel channel){
        System.out.println("Connected {");
    }
    
    @Override
    public void disconnected(TcpByteChannel channel){
        System.out.println("   Client disconnected\n}");
    }
}
