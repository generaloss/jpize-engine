package jpize.tests.minecraftose.main.command.builder;

import jpize.tests.minecraftose.main.command.argument.CommandArg;
import jpize.tests.minecraftose.main.command.Command;
import jpize.tests.minecraftose.main.command.node.CommandNodeArg;

public class CommandBuilderArg{
    
    private final CommandNodeArg commandRoot;
    
    public CommandBuilderArg(String name, CommandArg argument){
        commandRoot = new CommandNodeArg(name, argument);
    }
    
    
    public CommandBuilderArg then(String literal, CommandArg argument){
        commandRoot.addChild(new CommandNodeArg(literal, argument));
        return this;
    }
    
    public CommandBuilderArg executes(Command command){
        return this;
    }
    
    
    public CommandNodeArg buildArg(){
        return commandRoot;
    }
    
}
