package jpize.tests.mcose;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.main.net.PlayerProfile;

import java.util.HashMap;
import java.util.Map;

public class Main{

    public static PlayerProfile profile;
    public static String sessionToken = "54_54-iWantPizza-54_54";
    public static String gameDir;

    public static void main(String[] args){
        // Parse arguments
        final Map<String, String> argsMap = mapArgs(args);

        profile = new PlayerProfile(argsMap.getOrDefault("username", PlayerProfile.genFunnyName()));
        sessionToken = argsMap.get("sessionToken");
        gameDir = argsMap.get("gameDir");

        final int width = Integer.parseInt(argsMap.getOrDefault("width", "1280"));
        final int height = Integer.parseInt(argsMap.getOrDefault("height", "720"));

        // Run app
        ContextBuilder.newContext("Minecraft Open Source Edition")
            .size(width, height)
            .register()
            .setAdapter(new Minecraft());

        Jpize.runContexts();
    }

    private static Map<String, String> mapArgs(String[] args){
        final Map<String, String> map = new HashMap<>();

        for(int i = 0; i < args.length; i += 2){
            if(i + 1 == args.length)
                break;

            final String key = args[i].substring(2);
            final String value = args[i + 1];

            map.put(key, value);
        }

        return map;
    }

}
