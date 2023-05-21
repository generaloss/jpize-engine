package pize.tests.minecraft.client.world.world;

public class GameRule<ValueType>{

    private final String name;
    private ValueType value;

    public GameRule(String name,ValueType value){
        this.name = name;
        setValue(value);
    }


    public String getName(){
        return name;
    }

    public ValueType getValue(){
        return value;
    }

    public void setValue(ValueType value){
        this.value = value;
    }

}
