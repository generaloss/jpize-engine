package jpize.tests.minecraftosp.client.audio;

public enum MusicGroup{

    NONE(),
    MENU("mutation"),
    GAME(
        // "clark", "danny", "dry_hands", "haggstorm",
        // "living_mice", "mice_on_venus", "minecraft",
        // "subwoofer_lullaby", "sweden", "wet_hands"
    );

    private final String[] list;

    MusicGroup(String... list){
        this.list = list;
    }

    public String[] getList(){
        return list;
    }

}
