package jpize.tests.minecraftosp.main.command.builder;

import jpize.tests.minecraftosp.main.command.argument.CommandArg;
import jpize.tests.minecraftosp.main.command.node.CommandNodeArg;
import jpize.tests.minecraftosp.main.command.node.CommandNodeLiteral;

public class Commands{
    
    public static CommandNodeLiteral literal(String literal){
        return new CommandBuilderLiteral(literal).buildNode();
    }
    
    public static CommandNodeArg argument(String name, CommandArg type){
        return new CommandBuilderArg(name, type).buildArg();
    }
    
    
    
}
