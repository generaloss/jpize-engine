package jpize.tests.mcose.main.modification.loader;

import jpize.tests.mcose.main.modification.api.ClientModInitializer;
import jpize.tests.mcose.main.modification.api.DedicatedServerModInitializer;
import jpize.tests.mcose.main.modification.api.ModInitializer;

public enum ModEntryPointType{
    
    MAIN             ("main",                  ModInitializer.class),
    CLIENT           ("client",          ClientModInitializer.class),
    DEDICATED_SERVER ("server", DedicatedServerModInitializer.class);
    
    
    public final String jsonKey;
    public final Class<?> initializerClass;
    public final String initMethodName;
    
    ModEntryPointType(String jsonKey, Class<?> initializerClass){
        this.jsonKey = jsonKey;
        this.initializerClass = initializerClass;
        this.initMethodName = initializerClass.getDeclaredMethods()[0].getName();
    }
    
}