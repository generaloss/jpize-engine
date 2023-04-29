package pize.net.tcp;

import pize.net.NetConnection;
import pize.util.MTArrayList;
import pize.util.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpConnection implements NetConnection<TcpPacket>{

    private final Socket socket;
    private final Thread thread;
    private final ObjectOutputStream outputStream;
    private final MTArrayList<TcpPacket> received;
    private boolean closed;

    public TcpConnection(Socket socket) throws IOException{
        this.socket = socket;

        received = new MTArrayList<>(false);
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        thread = new Thread(()->{
            try{
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                while(!Thread.interrupted()){

                    if(inputStream.read() == 0)
                        received.add((TcpPacket) inputStream.readObject());
                    else{
                        closed = true;
                        socket.close();
                        return;
                    }

                    Thread.yield();
                }
            }catch(Exception ignored){
            }
        });

        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void send(TcpPacket packet){
        if(closed)
            return;

        try{
            outputStream.write(0);
            outputStream.writeObject(packet);
            outputStream.flush();
        }catch(IOException ignored){
        }
    }

    @Override
    public int available(){
        return received.size();
    }

    @Override
    public TcpPacket next(){
        TcpPacket packet = received.getLast();
        received.remove(packet);
        return packet;
    }

    @Override
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        thread.interrupt();
        Utils.close(socket);
        closed = true;
    }

}
