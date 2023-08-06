package pize.tests.minecraftosp.client.audio;

public enum MusicGroup{

    NONE(),
    MENU("mutation"),
    GAME("wet_hands", "clark", "haggstorm");

    private final String[] list;

    MusicGroup(String... list){
        this.list = list;
    }

    public String[] getList(){
        return list;
    }

}
