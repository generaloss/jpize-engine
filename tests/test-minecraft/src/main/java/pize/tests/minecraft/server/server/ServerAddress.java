package pize.tests.minecraft.server.server;

public class ServerAddress{

    private String hostname;
    private int port;

    public ServerAddress(String hostname,int port){
        setHostname(hostname);
        setPort(port);
    }

    public ServerAddress(String hostname){
        this(hostname,-1);
    }


    public String getHostname(){
        return hostname;
    }

    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }


    @Override
    public String toString(){
        return hostname + ":" + port;
    }

}
