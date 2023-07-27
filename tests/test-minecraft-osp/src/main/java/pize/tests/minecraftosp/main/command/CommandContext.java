package pize.tests.minecraftosp.main.command;

import pize.tests.minecraftosp.main.command.argument.CommandArg;
import pize.tests.minecraftosp.main.command.source.CommandSource;
import pize.tests.minecraftosp.server.Server;

import java.util.List;

public class CommandContext{
    
    private final Server server;
    private final CommandSource source;
    private final String command;
    private final List<CommandArg> arguments;
    
    public CommandContext(Server server, CommandSource source, String command, List<CommandArg> arguments){
        this.server = server;
        this.source = source;
        this.command = command;
        this.arguments = arguments;
    }
    
    public Server getServer(){
        return server;
    }
    
    public CommandSource getSource(){
        return source;
    }
    
    public String getCommand(){
        return command;
    }
    
    public CommandArg getArg(int index){
        return arguments.get(index);
    }
    
}
