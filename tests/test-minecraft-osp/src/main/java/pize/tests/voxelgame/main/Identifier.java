package pize.tests.voxelgame.main;

public class Identifier{

    public static final String DEFAULT_NAMESPACE = "minecraft";


    public static String namespaceID(String namespace, String id){
        return namespace + ":" + id;
    }

    public static String namespaceID(String id){
        return namespaceID(DEFAULT_NAMESPACE, id);
    }

}
