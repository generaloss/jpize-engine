package pize.tests.voxelgame.server;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketDisconnect;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketTeleportPlayer;
import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.ServerConnectionManager;
import pize.tests.voxelgame.server.player.ServerPlayer;
import pize.tests.voxelgame.server.player.PlayerList;

import java.util.StringJoiner;

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
    
    
    public void executeCommand(String command, String[] args, ServerPlayer sender){
        switch(command){
            case "help" -> {
                sender.sendMessage("/help          - that command");
                sender.sendMessage("/tp            - teleport [/tp PlayerName OR /tp X Y Z OR /tp PlayerName X Y Z]");
                sender.sendMessage("/playerlist    - online players list");
                sender.sendMessage("/kick          - kick player [/kick PlayerName Reason]");
                sender.sendMessage("/tell          - tell [/tell PlayerName Message]");
                sender.sendMessage("/setworldspawn - set spawn in the world");
                sender.sendMessage("/ban           - ban player");
                sender.sendMessage("/seed          - get world seed");
                sender.sendMessage("/level         - load / unload world");
            }
            
            case "tp" -> {
                if(args.length == 1){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else{
                        sender.teleport(targetPlayer);
                        sender.sendPacket(new CBPacketTeleportPlayer(targetPlayer.getLevel().getName(), targetPlayer.getPosition(), targetPlayer.getRotation()));
                        sender.sendMessage("You teleported to player " + targetPlayer.getName());
                        targetPlayer.sendMessage("Player " + targetPlayer.getName() + " teleported to you");
                    }
                }else if(args.length == 3){
                    final Vec3f position = new Vec3f(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
                    sender.teleport(position);
                    
                    sender.sendPacket(new CBPacketTeleportPlayer(sender.getLevel().getName(), position, sender.getRotation()));
                    sender.sendMessage("You teleported to " + position);
                    
                }else if(args.length == 4){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else{
                        final Vec3f position = new Vec3f(Float.parseFloat(args[1]), Float.parseFloat(args[2]), Float.parseFloat(args[3]));
                        sender.teleport(position);
                        
                        sender.sendMessage(targetPlayer.getName() + " teleported to " + position);
                        
                        targetPlayer.sendPacket(new CBPacketTeleportPlayer(sender.getLevel().getName(), position, sender.getRotation()));
                        targetPlayer.sendMessage("You teleported to " + position);
                    }
                }
            }
            
            case "playerlist" -> {
                final StringJoiner joiner = new StringJoiner(", ");
                for(ServerPlayer player: getPlayerList().getPlayers())
                    joiner.add(player.getName());
                sender.sendMessage("Players online: " + joiner);
            }
            
            case "kick" -> {
                if(args.length == 2){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer != null){
                        targetPlayer.sendPacket(new CBPacketDisconnect("You been kicked by " + sender.getName() + ", reason: " + args[1]));
                        playerList.broadcastMessage(targetPlayer.getName() + " has been kicked by " + sender.getName() + ", reason: " + args[1]);
                    }else
                        sender.sendMessage("Cannot find player " + args[0]);
                }else
                    sender.sendMessage("Invalid command arguments");
            }
            
            case "tell" -> {
                if(args.length > 1){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer != null){
                        final StringJoiner joiner = new StringJoiner(" ");
                        joiner.add("[" + sender.getName() + "] tells you:");
                        
                        for(int i = 1; i < args.length; i++)
                            joiner.add(args[i]);
                        
                        targetPlayer.sendMessage(joiner.toString());
                    }else
                        sender.sendMessage("Cannot find player " + args[0]);
                }else
                    sender.sendMessage("Invalid command arguments");
            }
            
            case "setworldspawn" -> {
                final Vec3f position = sender.getPosition();
                ((ServerLevel) sender.getLevel()).getConfiguration().setWorldSpawn(position.x, position.z);
                playerList.broadcastMessage("World spawn set in: " + position);
            }
            
            case "ban" -> {
                sender.sendMessage("<GeneralPashon> нет такой команды))))");
            }
            
            case "seed" -> {
                sender.sendMessage("World seed: " + ((ServerLevel) sender.getLevel()).getConfiguration().getGenerator().getSeed());
            }
            
            case "level" -> {
                sender.sendMessage("World seed: " + ((ServerLevel) sender.getLevel()).getConfiguration().getGenerator().getSeed());
            }
            
            default -> sender.sendMessage("Invalid command " + command);
        }
        
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
