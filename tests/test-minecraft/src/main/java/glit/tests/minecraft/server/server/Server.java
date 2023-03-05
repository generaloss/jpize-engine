package glit.tests.minecraft.server.server;

import glit.Glit;
import glit.io.glfw.Key;
import glit.tests.minecraft.server.world.world.ServerWorld;
import glit.tests.minecraft.utils.log.Logger;
import glit.util.time.GlitRunnable;
import glit.util.time.FpsCounter;

import java.util.HashMap;
import java.util.Map;

public abstract class Server{

    private final Map<String, ServerWorld> worlds;
    private final PlayerList playerList;
    private final GameTime gameTime;
    private ServerStatus status;
    private Thread serverThread;
    private final FpsCounter deltaTimeCounter;

    public Server(){
        worlds = new HashMap<>();
        playerList = new PlayerList(this);
        gameTime = new GameTime();
        status = ServerStatus.STOPPED;

        deltaTimeCounter = new FpsCounter();
    }


    public void start(){
        Logger.instance().info("Starting Server");
        serverThread = new Thread(this::run, "Server Thread");
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private void run(){
        try{
            GlitRunnable tickRunnable = new GlitRunnable(){
                public void run(){

                    deltaTimeCounter.update();
                    tick();
                }
            };

            status = ServerStatus.RUNNING;
            tickRunnable.runTimer(0,1000/750);
        }catch(Throwable e){
            Logger.instance().error("Crush Report: {}", e);
        }finally{
            stop();
        }
    }

    public void stop(){
        if(status == ServerStatus.STOPPED)
            return;
        status = ServerStatus.STOPPED;

        Logger.instance().info("Stopping Server");
        serverThread.interrupt();
    }

    private void tick(){
        // throw new Error(" I WANT pizza ");
        if(Glit.isPressed(Key.F))
            Logger.instance().debug("F IS PRESSED!!!!!");
    }


    public int getTps(){
        return deltaTimeCounter.get();
    }

    public Iterable<ServerWorld> getWorlds(){
        return worlds.values();
    }

    public ServerWorld getWorld(String name){
        return worlds.get(name);
    }

    abstract public IServerConfiguration getConfiguration();

    public ServerStatus getStatus(){
        return status;
    }

    public GameTime getGameTime(){
        return gameTime;
    }

    public PlayerList getPlayerList(){
        return playerList;
    }
}
