package pize.tests.voxelgame.server;

import pize.tests.voxelgame.clientserver.net.packet.CBPacketTeleportPlayer;
import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.net.ServerConnectionManager;
import pize.tests.voxelgame.server.player.Entity;
import pize.tests.voxelgame.server.player.PlayerList;

public abstract class Server{

    private final ServerConfiguration configuration;
    private final PlayerList playerList;
    private final LevelManager levelManager;
    private final ServerConnectionManager connectionManager;
    
    public Server(){
        configuration = new ServerConfiguration();
        playerList = new PlayerList(this);
        levelManager = new LevelManager(this);
        connectionManager = new ServerConnectionManager(this);
    }
    
    
    public void executeCommand(String command, String[] args, Entity sender){
        if(command.equals("tp")){
            
            if(args.length == 1){
                final Entity targetPlayer = playerList.getPlayer(args[0]);
                if(targetPlayer == null)
                    sender.sendMessage("Cannot find player " + args[0]);
                else{
                    sender.teleport(targetPlayer);
                    sender.sendPacket(new CBPacketTeleportPlayer(
                        targetPlayer.getLevel().getName(), targetPlayer.getPosition(), targetPlayer.getRotation()));
                    sender.sendMessage("You teleported to player " + targetPlayer.getName());
                }
            }
            
        }else
            sender.sendMessage("Invalid command " + command);
    }
    
    
    public ServerConfiguration getConfiguration(){
        return configuration;
    }
    
    public PlayerList getPlayerList(){
        return playerList;
    }
    
    public LevelManager getLevelManager(){
        return levelManager;
    }
    
    public ServerConnectionManager getConnectionManager(){
        return connectionManager;
    }
    
}
