package pize.tests.voxelgame.server.command.node;

import pize.tests.voxelgame.server.command.Command;
import pize.tests.voxelgame.server.command.argument.CommandArg;

public class CommandNodeArg extends CommandNodeLiteral{
    
    private final CommandArg argument;
    
    public CommandNodeArg(String literal, CommandArg argument){
        super(literal);
        this.argument = argument;
    }
    
    public CommandArg getArgument(){
        return argument;
    }
    
    
    public CommandNodeArg then(CommandNodeLiteral node){
        super.then(node);
        return this;
    }
    
    public CommandNodeArg executes(Command command){
        super.executes(command);
        return this;
    }
    
}
