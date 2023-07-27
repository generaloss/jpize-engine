package pize.tests.minecraftosp.main.command.builder;

import pize.tests.minecraftosp.main.command.argument.CommandArg;
import pize.tests.minecraftosp.main.command.Command;
import pize.tests.minecraftosp.main.command.node.CommandNodeArg;
import pize.tests.minecraftosp.main.command.node.CommandNodeLiteral;

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
