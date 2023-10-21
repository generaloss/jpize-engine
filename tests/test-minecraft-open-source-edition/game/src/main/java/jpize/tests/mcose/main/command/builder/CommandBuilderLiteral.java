package jpize.tests.mcose.main.command.builder;

import jpize.tests.mcose.main.command.argument.CommandArg;
import jpize.tests.mcose.main.command.Command;
import jpize.tests.mcose.main.command.node.CommandNodeArg;
import jpize.tests.mcose.main.command.node.CommandNodeLiteral;

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