package pize.tests.voxelgame.client.resources;

public class VanillaMusic{

    private static final String SOUND_DIR = "music/";
    private static final String SOUND_GAME_DIR = SOUND_DIR + "game/";
    private static final String SOUND_MENU_DIR = SOUND_DIR + "menu/";

    public static void register(GameResources resources){
        System.out.println("[Resources]: Load Music");

        // Menu //
        resources.registerSound(SOUND_MENU_DIR, "mutation");
        // Game //
        resources.registerSound(SOUND_GAME_DIR, "wet_hands");
    }

}
