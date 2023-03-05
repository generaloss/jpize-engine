package glit.files;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class MapConfig{

    private FileHandle file;
    private final HashMap<String, String> map;
    private String separator;

    public MapConfig(FileHandle file){
        setFile(file);
        map = new HashMap<>();
        separator = " : ";
    }

    public MapConfig(File file){
        this(new FileHandle(file));
    }


    public void load(){
        map.clear();

        String[] lines = file.readString().split("\n");
        for(String line: lines){
            String[] entry = line.split(separator);

            if(entry.length == 2)
                map.put(entry[0], entry[1]);
        }
    }

    public void save(){
        PrintStream writer = file.writer();
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

    public void setFile(FileHandle file){
        this.file = file;
    }

    public void setFile(File file){
        this.file = new FileHandle(file);
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
