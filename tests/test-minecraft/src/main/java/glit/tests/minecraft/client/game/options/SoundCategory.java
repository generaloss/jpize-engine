package glit.tests.minecraft.client.game.options;

public enum SoundCategory{

    MASTER(1F, "audioOptions.master"), // Общая Громкость
    MUSIC(0.1F, "audioOptions.music"), // Музыка
    AMBIENT(1F, "audioOptions.ambient"), // Окружение
    PLAYERS(1F, "audioOptions.players"), // Игроки
    BLOCKS(1F, "audioOptions.blocks"), // Блоки
    WEATHER(0.1F, "audioOptions.weather"); // Погода


    private final float defaultVolume;
    private final String guiText;

    SoundCategory(float defaultVolume, String guiText){
        this.defaultVolume = defaultVolume;
        this.guiText = guiText;
    }

    public float getDefaultVolume(){
        return defaultVolume;
    }

    public String getTranslateKey(){
        return guiText;
    }

}
