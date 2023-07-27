package pize.tests.minecraftosp.main.command.builder;

import pize.tests.minecraftosp.main.command.argument.CommandArg;
import pize.tests.minecraftosp.main.command.node.CommandNodeArg;
import pize.tests.minecraftosp.main.command.node.CommandNodeLiteral;

public class Commands{
    
    public static CommandNodeLiteral literal(String literal){
        return new CommandBuilderLiteral(literal).buildNode();
    }
    
    public static CommandNodeArg argument(String name, CommandArg type){
        return new CommandBuilderArg(name, type).buildArg();
    }
    
    
    
}
