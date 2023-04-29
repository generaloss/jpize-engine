package pize.tests.minecraft.client.game.lang;

import pize.files.FileHandle;
import pize.files.MapConfig;

import java.util.HashMap;
import java.util.List;

public class LanguageManager{

    private final HashMap<String, LanguageInfo> languages;
    private ClientLanguage currentLanguage;

    public LanguageManager(){
        languages = new HashMap<>();
    }


    public void selectLanguage(String languageCode){
        currentLanguage = ClientLanguage.loadFrom(new FileHandle("vanilla/lang/" + languageCode + ".txt"));
    }


    public void updateAvailableLanguages(){
        languages.clear();

        FileHandle langDir = new FileHandle("vanilla/lang/");

        FileHandle[] langList = langDir.list();
        if(langList == null)
            return;

        for(FileHandle langFile: langList){
            MapConfig langMap = new MapConfig(langFile);
            langMap.load();

            String code = langMap.get("language.code");
            if(code != null)
                languages.put(code, new LanguageInfo(
                    code,
                    langMap.get("language.name"),
                    langMap.get("language.region")
                ));

            langMap.clear();
        }
    }

    public List<LanguageInfo> getAvailableLanguages(){
        return languages.values().stream().toList();
    }

    public ClientLanguage getLanguage(){
        return currentLanguage;
    }

    public LanguageInfo getLanguageInfo(String code){
        return languages.get(code);
    }

}
