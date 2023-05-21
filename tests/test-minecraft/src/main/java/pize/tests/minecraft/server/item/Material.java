package pize.tests.minecraft.server.item;

public enum Material{

    /** BASE **/
    AIR(0,0),
    STONE(1),
    DIRT(2),
    GRASS_BLOCK(3),
    BEDROCK(4),
    /** DEFAULT **/
    COBBLESTONE(100),
    OAK_PLANKS(101),
    /** TREES **/
    OAK_LOG(200),
    OAK_LEAVES(201),
    BIRTH_LOG(202),
    BIRTH_LEAVES(103),
    /** FLORA **/
    GRASS(300),
    POPPY(301),
    DANDELION(302),
    /** LIGHT SOURCES **/
    GLOWSTONE(400),
    TORCH(401),
    /** LIQUIDS **/
    WATER(500),
    LAVA(501),
    ;


    private final int id, maxStack, maxDurability;


    Material(int id){
        this(id,64);
    }

    Material(int id,int maxStack){
        this(id,maxStack,0);
    }

    Material(int id,int maxStack,int maxDurability){
        this.id = id;
        this.maxStack = maxStack;
        this.maxDurability = maxDurability;
    }


    public boolean isEmpty(){
        return this == AIR;
    }


    public int getId(){
        return id;
    }

    public int getMaxStack(){
        return maxStack;
    }

    public int getMaxDurability(){
        return maxDurability;
    }

}
