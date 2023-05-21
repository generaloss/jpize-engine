package pize.tests.minecraft.client.game.lang;

import pize.files.Resource;
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
        currentLanguage = ClientLanguage.loadFrom(new Resource("vanilla/lang/" + languageCode + ".txt"));
    }


    public void updateAvailableLanguages(){
        languages.clear();

        Resource langDir = new Resource("vanilla/lang/");

        Resource[] langList = langDir.list();
        if(langList == null)
            return;

        for(Resource resLang: langList){
            MapConfig langMap = new MapConfig(resLang);
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
