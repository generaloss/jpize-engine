package pize.tests.net;

import pize.net.tcp.TcpByteChannel;

public class ClientConnection{
    
    private TcpByteChannel channel;
    private String name;
    
    public ClientConnection(TcpByteChannel channel){
        this.channel = channel;
    }
    
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
}
