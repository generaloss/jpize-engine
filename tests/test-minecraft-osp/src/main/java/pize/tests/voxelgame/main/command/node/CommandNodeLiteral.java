package pize.tests.voxelgame.main.command.node;

import pize.tests.voxelgame.main.command.Command;
import pize.tests.voxelgame.main.command.source.CommandSource;
import pize.tests.voxelgame.main.command.source.CommandSourcePlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CommandNodeLiteral{
    
    private final String literal;
    protected final Map<String, CommandNodeLiteral> children;
    protected Command command;
    protected Predicate<CommandSource> requires;
    
    public CommandNodeLiteral(String literal){
        this.literal = literal;
        this.children = new HashMap<>();
    }
    
    public String getLiteral(){
        return literal;
    }
    
    
    public Collection<CommandNodeLiteral> getChildren(){
        return children.values();
    }
    
    public CommandNodeLiteral getChild(String literal){
        return children.get(literal);
    }
    
    public void addChild(CommandNodeLiteral node){
        children.put(node.getLiteral() ,node);
    }
    
    public CommandNodeLiteral then(CommandNodeLiteral node){
        addChild(node);
        return this;
    }
    
    
    public Command getCommand(){
        return command;
    }
    
    public CommandNodeLiteral executes(Command command){
        this.command = command;
        return this;
    }
    
    
    public CommandNodeLiteral requires(Predicate<CommandSource> requires){
        this.requires = requires;
        return this;
    }
    
    public CommandNodeLiteral requiresPlayer(){
        this.requires = source -> source instanceof CommandSourcePlayer;
        return this;
    }
    
    
    public boolean requirementsTest(CommandSource source){
        // Если требований нет - тест пройден
        if(requires == null)
            return true;
        
        return requires.test(source);
    }
    
}
