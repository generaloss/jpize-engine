package pize.tests.voxelgame.main.modification.loader;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

public class ModLoader{
    
    private final Map<String, Modification> loadedMods;
    
    public ModLoader(){
        loadedMods = new HashMap<>();
    }
    
    
    public void loadMods(String modsPath){
        // Находим моды в папке с модами
        final String[] mods = new File(modsPath).list(
            (file, name)->name.substring(name.lastIndexOf('.')).equals(".jar")
        );
        
        if(mods == null)
            return;
        
        // Загружаем
        for(String modPath: mods)
            this.loadMod(modsPath + modPath);
    }
    
    public void loadMod(String modPath){
        try{
            final JarFile jarFile = new JarFile(modPath);
            
            // Try to read file 'mod-properties.json'
            final InputStream modPropertiesStream = jarFile.getInputStream(jarFile.getEntry("mod-properties.json"));
            if(modPropertiesStream == null)
                throw new RuntimeException("[ModLoader]: Not found file 'mod-properties.json'");
            
            final JSONObject modProperties = new JSONObject(new String(modPropertiesStream.readAllBytes()));
            
            // Read properties
            final String name = modProperties.getString("name");
            final String id = modProperties.getString("id");
            final String version = modProperties.getString("version");
            final String description = modProperties.getString("description");
            final String icon = modProperties.getString("icon");
            
            // Class loader
            final URL[] urls = new URL[]{ new File(modPath).toURI().toURL() };
            final URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
            
            // Read entry points
            final Map<ModEntryPointType, Class<?>> entryPointMap = new HashMap<>();
            final JSONObject entryPoints = modProperties.getJSONObject("entrypoints");
            
            // Итерируемся по всем типам (main, client, server)
            for(ModEntryPointType type: ModEntryPointType.values()){
                // Если содержится один из них
                if(entryPoints.has(type.jsonKey)){
                    // Загружаем класс
                    
                    final String className = entryPoints.getString(type.jsonKey);
                    try{
                        final Class<?> entryPointClass = classLoader.loadClass(className);
                        // Проверяем, реализует ли он соответствующий интерфейс (ModLoader, ClientModLoader, DedicatedServerModLoader)
                        if(Arrays.stream(entryPointClass.getInterfaces()).anyMatch(pointClass->pointClass == type.initializerClass))
                            // Добавляем в список точек входа
                            entryPointMap.put(type, entryPointClass);
                    }catch(Exception e){
                        System.err.println("Failed to load Entry point '" + className + "'");
                    }
                }
            }
            
            // Add mod to list
            final Modification mod = new Modification(classLoader, entryPointMap, name, id, version, description, icon);
            loadedMods.put(id, mod);
            
            jarFile.close();
        }catch(Exception e){
            System.err.println("Failed to load Mod '" + modPath + "' (" + e.getMessage() + ")");
        }
    }
    
    
    public void initializeMods(ModEntryPointType entryPointType){
        try{
            for(Modification modification: loadedMods.values())
                modification.initialize(entryPointType);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void invokeMethod(ModEntryPointType entryPointType, String name){
        try{
            for(Modification modification: loadedMods.values())
                modification.invokeMethod(entryPointType, name);
        }catch(Exception ignored){ }
    }
    
    
    public Collection<Modification> getLoadedMods(){
        return loadedMods.values();
    }
    
}
