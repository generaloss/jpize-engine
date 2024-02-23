package test;

import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.sdl.input.Key;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpTest extends JpizeApplication{

    Client client;

    public UdpTest(){
        try{
            Server server = new Server();
            server.runServer();
            client = new Client();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void render(){
        Gl.clearColorBuffer();
        if(Key.ESCAPE.isDown())
            System.out.println(client.sendEcho("end"));
        if(Key.S.isDown())
            System.out.println(client.sendEcho("Message 54"));
    }

    public static class Server{

        private final DatagramSocket socket;
        private boolean running;
        private final byte[] buf;

        public Server(){
            try{
                socket = new DatagramSocket(4445);
                buf = new byte[256];
            }catch(SocketException e){
                throw new RuntimeException(e);
            }
        }

        public void runServer(){
            running = true;

            Thread thread = new Thread(() -> {
                while(running){
                    try{
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        socket.receive(packet);

                        packet = new DatagramPacket(buf, packet.getLength(), packet.getAddress(), packet.getPort());
                        String received = new String(packet.getData(), 0, packet.getLength());

                        System.out.println("Received: " + received);
                        if(received.equals("end")){
                            System.out.println("STOP");
                            running = false;
                            continue;
                        }
                        socket.send(packet);
                    }catch(IOException e){
                        e.printStackTrace();
                        socket.close();
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

    }

    public static class Client{

        private final DatagramSocket socket;

        public Client(){
            try{
                this.socket = new DatagramSocket();
            }catch(SocketException e){
                throw new RuntimeException(e);
            }
        }

        public String sendEcho(String msg){
            try{
                final byte[] buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 4445);
                socket.send(packet);
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                return received;
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }

        public void close(){
            socket.close();
        }

    }

}
