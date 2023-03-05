package glit.tests.minecraft.client.game.lang;

import glit.files.FileHandle;
import glit.files.MapConfig;

import java.util.HashMap;

public class ClientLanguage{

    private HashMap<String, String> storage;

    public static ClientLanguage loadFrom(FileHandle file){
        ClientLanguage language = new ClientLanguage();
        MapConfig map = new MapConfig(file);
        map.load();
        language.storage = map.getMap();

        return language;
    }

    public String getOrDefault(String key){
        return storage.getOrDefault(key, key);
    }

}
