package pize.tests.voxelgame.server;

import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketDisconnect;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketEntityMove;
import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.ServerConnectionManager;
import pize.tests.voxelgame.server.player.PlayerList;
import pize.tests.voxelgame.server.player.ServerPlayer;

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
                sender.sendMessage("/tp            - teleport");
                sender.sendMessage("/list          - players list");
                sender.sendMessage("/kick          - kick player [/kick PlayerName Reason]");
                sender.sendMessage("/tell          - tell");
                sender.sendMessage("/setworldspawn - set spawn in the world");
                sender.sendMessage("/ban           - ban player");
                sender.sendMessage("/seed          - get world seed");
                sender.sendMessage("/level         - level op [level create/goto NAME SEED GENERATOR]");
                sender.sendMessage("/push          - add velocity [/vel PlayerName X Y Z]");
            }
            
            case "tp" -> {
                if(args.length == 1){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else{
                        sender.teleport(targetPlayer);
                        sender.sendMessage("You teleported to player " + targetPlayer.getName());
                        targetPlayer.sendMessage("Player " + targetPlayer.getName() + " teleported to you");
                    }
                    
                }else if(args.length == 2){
                    final ServerPlayer victimPlayer = playerList.getPlayer(args[0]);
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[1]);
                    if(victimPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[1]);
                    else{
                        victimPlayer.teleport(targetPlayer);
                        victimPlayer.sendMessage("You teleported to " + targetPlayer.getName());
                        targetPlayer.sendMessage("Player " + targetPlayer.getName() + " teleported to you");
                    }
                    
                }else if(args.length == 3){
                    final Vec3f position = new Vec3f(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
                    sender.teleport(position);
                    
                    sender.sendMessage("You teleported to " + position);
                    
                }else if(args.length == 4){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else{
                        final Vec3f position = new Vec3f(Float.parseFloat(args[1]), Float.parseFloat(args[2]), Float.parseFloat(args[3]));
                        targetPlayer.teleport(position);
                        
                        sender.sendMessage(targetPlayer.getName() + " teleported to " + position);
                        targetPlayer.sendMessage("You teleported to " + position);
                    }
                }
            }
            
            case "list" -> {
                final StringJoiner joiner = new StringJoiner(", ");
                for(ServerPlayer player: getPlayerList().getPlayers())
                    joiner.add(player.getName());
                sender.sendMessage("Players online: " + joiner);
            }
            
            case "kick" -> {
                if(args.length > 1){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer != null){
                        final String reason = CommandUtils.joinArgs(args, 1);
                        targetPlayer.sendPacket(new CBPacketDisconnect("You been kicked by " + sender.getName() + ", reason: " + reason));
                        playerList.broadcastMessage(targetPlayer.getName() + " has been kicked by " + sender.getName() + ", reason: " + reason);
                    }else
                        sender.sendMessage("Cannot find player " + args[0]);
                }else
                    sender.sendMessage("Invalid command arguments");
            }
            
            case "tell" -> {
                if(args.length > 1){
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer != null){
                        targetPlayer.sendMessage("<" + sender.getName() + "> tells you: \"" + CommandUtils.joinArgs(args, 1) + "\"");
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
                sender.sendMessage("World seed: " + ((ServerLevel) sender.getLevel()).getConfiguration().getSeed());
            }
            
            case "level" -> {
                if(args.length > 1){
                    final String levelName = args[1];
                    switch(args[0]){
                        case "create" -> {
                            if(args.length != 4)
                                sender.sendMessage("Invalid command arguments");
                            else if(levelManager.isLevelLoaded(levelName))
                                sender.sendMessage("Level " + levelName + " already loaded");
                            else{
                                levelManager.createLevel(levelName, Integer.parseInt(args[2]), ChunkGenerator.fromString(args[3]));
                                playerList.broadcastMessage("[Server]: Level " + levelName + " loaded");
                            }
                        }
                        case "goto" -> {
                            if(!levelManager.isLevelLoaded(levelName))
                                sender.sendMessage("Level " + levelName + " is not loaded");
                            else{
                                final ServerLevel level = levelManager.getLevel(levelName);
                                sender.teleport(level, level.getSpawnPosition());
                                sender.sendMessage("You teleported to level " + levelName);
                            }
                        }
                    }
                }
            }
            
            case "push" -> {
                if(args.length != 4)
                    sender.sendMessage("Invalid command arguments");
                else{
                    final ServerPlayer targetPlayer = playerList.getPlayer(args[0]);
                    if(targetPlayer == null)
                        sender.sendMessage("Cannot find player " + args[0]);
                    else{
                        final Vec3d motion = new Vec3d(
                            Double.parseDouble(args[1]),
                            Double.parseDouble(args[2]),
                            Double.parseDouble(args[3])
                        );
                        targetPlayer.getMotion().add(motion);
                        targetPlayer.sendPacket(new CBPacketEntityMove(targetPlayer));
                        
                        sender.sendMessage("You pushed player " + targetPlayer.getName());
                        if(targetPlayer != sender)
                            targetPlayer.sendMessage("You were pushed by player " + sender.getName());
                    }
                }
            }
            
            default -> sender.sendMessage("Invalid command " + command);
        }
        
        // PORTALS
        
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
