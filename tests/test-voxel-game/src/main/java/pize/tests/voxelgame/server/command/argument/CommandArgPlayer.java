package pize.tests.voxelgame.server.command.argument;

import pize.tests.voxelgame.base.net.PlayerProfile;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.command.source.CommandSource;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandArgPlayer extends CommandArg{
    
    // Результат парсинга
    private ServerPlayer player;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (имя игрока), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // Находим игрока
        final String playerName = args[0];
        if(!PlayerProfile.isNameValid(playerName))
            return 0;
        
        player = server.getPlayerList().getPlayer(playerName);
        if(player == null)
            return 0;
        
        return playerName.length();
    }
    
    public ServerPlayer getPlayer(){
        return player;
    }
    
}
