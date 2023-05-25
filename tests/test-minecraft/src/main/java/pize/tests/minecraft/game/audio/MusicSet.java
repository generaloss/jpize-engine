package pize.tests.minecraft.game.audio;

public enum MusicSet{

    MAIN_MENU("Beginning 2", "Moog City 2", "Floating Trees", "Mutation");


    private final String[] set;

    MusicSet(String... set){
        this.set = set;
    }

    public String get(int index){
        return set[index];
    }

    public int size(){
        return set.length;
    }

}
