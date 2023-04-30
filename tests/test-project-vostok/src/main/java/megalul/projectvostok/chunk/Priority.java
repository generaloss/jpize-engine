package megalul.projectvostok.chunk;

public enum Priority{
    
    UPDATE_LIGHT(4),
    SET_BLOCK(3),
    UPDATE_EDGE(2),
    NEW_CHUNK(1);
    
    public final int value;
    
    Priority(int value){
        this.value = value;
    }
    
}