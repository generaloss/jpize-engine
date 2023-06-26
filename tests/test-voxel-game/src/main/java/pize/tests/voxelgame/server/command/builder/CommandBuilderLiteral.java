package pize.tests.voxelgame.server.command.builder;

import pize.tests.voxelgame.server.command.Command;
import pize.tests.voxelgame.server.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.node.CommandNodeArg;
import pize.tests.voxelgame.server.command.node.CommandNodeLiteral;

public class CommandBuilderLiteral{
    
    private final CommandNodeLiteral commandRoot;
    
    public CommandBuilderLiteral(String command){
        commandRoot = new CommandNodeLiteral(command);
    }
    
    
    public CommandBuilderLiteral then(String literal, CommandArg argumentType){
        commandRoot.addChild(new CommandNodeArg(literal, argumentType));
        return this;
    }
    
    public CommandBuilderLiteral executes(Command command){
        return this;
    }
    
    
    public CommandNodeLiteral buildNode(){
        return commandRoot;
    }

}
