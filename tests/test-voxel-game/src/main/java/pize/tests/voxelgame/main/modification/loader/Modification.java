package pize.tests.voxelgame.main.modification.loader;

import pize.files.Resource;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class Modification{
    
    private final URLClassLoader classLoader;
    private final Map<ModEntryPointType, Object> entryPoints;
    
    private final String name;
    private final String id;
    private final String version;
    private final String description;
    private final Resource icon;
    
    public Modification(URLClassLoader classLoader, Map<ModEntryPointType, Class<?>> entryPointMap, String name, String id, String version, String description, String iconPath) throws Exception{
        this.classLoader = classLoader;
        this.name = name;
        this.id = id;
        this.version = version;
        this.description = description;
        this.icon = new Resource(iconPath, classLoader.getClass());
        
        this.entryPoints = new HashMap<>();
        for(Map.Entry<ModEntryPointType, Class<?>> entryPoint: entryPointMap.entrySet())
            entryPoints.put(entryPoint.getKey(), entryPoint.getValue().getConstructor().newInstance());
    }
    
    
    public void initialize(ModEntryPointType entryPointType) throws Exception{
        invokeMethod(entryPointType, entryPointType.initMethodName);
    }
    
    public void invokeMethod(ModEntryPointType entryPointType, String name) throws Exception{
        if(!entryPoints.containsKey(entryPointType))
            return;
        
        final Object entryPoint = entryPoints.get(entryPointType);
        final Method method = entryPoint.getClass().getDeclaredMethod(name);
        method.invoke(entryPoint);
    }
    
    
    public String getName(){
        return name;
    }
    
    public String getID(){
        return id;
    }
    
    public String getVersion(){
        return version;
    }
    
    public String getDescription(){
        return description;
    }
    
    public Resource getIcon(){
        return icon;
    }
    
}
