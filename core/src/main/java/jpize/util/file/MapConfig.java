package jpize.util.file;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class MapConfig{

    private ResourceExt resource;
    private final HashMap<String, String> map;
    private String separator;

    public MapConfig(ResourceExt resource){
        this.map = new HashMap<>();
        this.setResource(resource);
        this.separator = " : ";
    }

    public MapConfig(File file){
        this(Resource.external(file));
    }


    public void load(){
        map.clear();

        final String[] lines = resource.readString().split("\n");
        for(String line: lines){
            final String[] entry = line.split(separator);

            if(entry.length == 2)
                map.put(entry[0], entry[1]);
        }
    }

    public void save(){
        final PrintStream writer = resource.asExternal().writer();
        for(Map.Entry<String, String> entry: map.entrySet())
            writer.println(entry.getKey() + separator + entry.getValue());

        writer.close();
    }


    public void clear(){
        map.clear();
    }


    public void put(String key, String value){
        map.put(key, value);
    }

    public String get(String key){
        return map.get(key);
    }

    public String getOrDefault(String key, String defaultValue){
        return map.getOrDefault(key, defaultValue);
    }

    public Resource getResource(){
        return resource;
    }

    public void setResource(ResourceExt resource){
        this.resource = resource;
    }

    public void setFile(File file){
        this.resource = Resource.external(file);
    }

    public String getSeparator(){
        return separator;
    }

    public void setSeparator(String separator){
        this.separator = separator;
    }


    public HashMap<String, String> getMap(){
        return map;
    }

}
