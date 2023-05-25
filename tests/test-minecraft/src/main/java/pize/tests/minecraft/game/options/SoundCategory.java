package pize.tests.minecraft.game.options;

public enum SoundCategory{

    MASTER(1F, "audioSettings.master"), // Общая Громкость
    MUSIC(0.1F, "audioSettings.music"), // Музыка
    AMBIENT(1F, "audioSettings.ambient"), // Окружение
    PLAYERS(1F, "audioSettings.players"), // Игроки
    BLOCKS(1F, "audioSettings.blocks"), // Блоки
    WEATHER(0.1F, "audioSettings.weather"); // Погода


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
