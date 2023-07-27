package pize.tests.voxelgame.server.command;

import pize.tests.voxelgame.main.command.Command;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.text.TextColor;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.vanilla.*;
import pize.tests.voxelgame.main.command.node.CommandNodeArg;
import pize.tests.voxelgame.main.command.node.CommandNodeLiteral;
import pize.tests.voxelgame.main.command.source.CommandSource;
import pize.tests.voxelgame.main.command.source.CommandSourcePlayer;

import java.util.*;

public class CommandDispatcher{

    private final Server server;
    private final Map<String, CommandNodeLiteral> commands;
    
    public CommandDispatcher(Server server){
        this.server = server;
        this.commands = new HashMap<>();
        
        // Register commands
        CommandHelp.registerTo(this);
        CommandSpawn.registerTo(this);
        CommandTeleport.registerTo(this);
        CommandFly.registerTo(this);
        CommandList.registerTo(this);
        CommandSeed.registerTo(this);
        CommandSetWorldSpawn.registerTo(this);
        CommandTell.registerTo(this);
        CommandKick.registerTo(this);
        CommandLevel.registerTo(this);
        CommandTime.registerTo(this);
        CommandShutdown.registerTo(this);
    }
    
    public Server getServer(){
        return server;
    }
    
    
    public Collection<CommandNodeLiteral> getCommands(){
        return commands.values();
    }
    
    public void newCommand(CommandNodeLiteral node){
        commands.put(node.getLiteral(), node);
    }
    
    public void executeCommand(String commandMessage, CommandSource source){
        // Уведомление об отправке команды игроком
        if(source instanceof CommandSourcePlayer playerSource)
            System.out.println("[Server]: Player " + playerSource.getPlayer().getName() + " execute command: " + commandMessage);
        
        // Разделяем комманду на аргументы
        final String[] splitCommand = commandMessage.split(" ");
        
        // Проверяем существует ли такая команда в корневом списке
        CommandNodeLiteral targetCommandNode = commands.get(splitCommand[0]);
        if(targetCommandNode == null){
            source.sendMessage(new Component().color(TextColor.RED).text("Command /" + splitCommand[0] + " is not exists"));
            return;
        }else if(!targetCommandNode.requirementsTest(source)){
            source.sendMessage(new Component().color(TextColor.RED).text("You don't have permission"));
            return;
        }
        
        
        // Аргументы
        final String joinedArguments = commandMessage.substring(splitCommand[0].length());
        final List<CommandArg> argumentList = new ArrayList<>();
        
        // Ищем ноду по дереву
        // Проходимся по всей строке с аргументами
        int index = joinedArguments.startsWith(" ") ? 1 : 0;
        while(index < joinedArguments.length()){
            // Оставшиеся символы
            final String remainingChars = joinedArguments.substring(index);
            final int nextSpace = remainingChars.indexOf(" ");
            
            final List<CommandNodeLiteral> children = new ArrayList<>(targetCommandNode.getChildren());
            // Проходимся по каждому child-node
            
            final int oldIndex = index;
            for(int j = 0; j < children.size(); j++){
                final CommandNodeLiteral child = children.get(j);
                
                // Если он является агрументом - пытаемся парсить его
                if(child instanceof CommandNodeArg argumentNode){
                    final CommandArg arg = argumentNode.getArgument();
                    final int parsedChars = arg.parse(remainingChars, source, server);
                    // [DEBUG]: System.out.println("Parsed " + remainingChars + " by "+ arg.getClass().getSimpleName() + ": " + parsedChars);
                    
                    // Если не удалось разобрать аргумент
                    if(parsedChars == 0){
                        // И если этот аргумент оставался последним вариантом
                        if(j + 1 == children.size()){
                            // Указать на ошибку
                            
                            final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                            source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                            return;
                        }else
                            continue;
                    }
                    
                    index += parsedChars + 1; // 1 - пробел
                    argumentList.add(arg);
                    targetCommandNode = argumentNode;
                    
                    break;
                    
                // Если это литерал - просто проверяем на равенство
                }else{
                    // Находим текущий аргумент
                    final String currentArgument = nextSpace == -1 ? remainingChars : remainingChars.substring(0, nextSpace);
                    
                    // [DEBUG]: System.out.println("Check literal: " + currentArgument + " by " + child.getClass().getSimpleName() + "(" + child.getLiteral() + ")");
                    
                    // Проверяем
                    if(currentArgument.equalsIgnoreCase(child.getLiteral())){
                        targetCommandNode = child;
                        index += currentArgument.length() + 1; // 1 - пробел
                        break;
                    // Если не удалось проверить
                    }else{
                        // И если этот аргумент оставался последним вариантом
                        if(j + 1 == children.size()){
                            final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                            source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                            return;
                        }
                    }
                }
            }
            
            // Если не один нод в списке children не смог парсить joinedArguments.substring(index) (индекс остался тем же)
            if(oldIndex == index){
                // Выбрасываем ошибку
                final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                return;
            }
        }
        
        // Check permissions
        if(!targetCommandNode.requirementsTest(source)){
            source.sendMessage(new Component().color(TextColor.RED).text("You don't have permission"));
            return;
        }
        
        // Последняя нода должна содержать интерфейс 'Command' для исполнения команды
        final Command command = targetCommandNode.getCommand();
        if(command == null){
            source.sendMessage(new Component().color(TextColor.RED).text("Wrong arguments"));
            return;
        }
        
        // Исполняем команду
        final CommandContext commandContext = new CommandContext(server, source, commandMessage, argumentList);
        command.run(commandContext);
    }
    
}
