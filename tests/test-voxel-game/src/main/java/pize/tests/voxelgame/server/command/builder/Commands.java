package pize.tests.voxelgame.server.command.builder;

import pize.tests.voxelgame.server.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.node.CommandNodeArg;
import pize.tests.voxelgame.server.command.node.CommandNodeLiteral;

public class Commands{
    
    public static CommandNodeLiteral literal(String literal){
        return new CommandBuilderLiteral(literal).buildNode();
    }
    
    public static CommandNodeArg argument(String name, CommandArg type){
        return new CommandBuilderArg(name, type).buildArg();
    }
    
    
    
}
