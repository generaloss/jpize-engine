package jpize.tests.minecraftose.main.registry;

import jpize.tests.minecraftose.Minecraft;
import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.resources.GameResources;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry{

    private final Map<Byte, Block> blocks;

    public BlockRegistry(){
        blocks = new HashMap<>();
    }

    public void loadBlocks(Minecraft session){
        System.out.println("[Resources] Load Blocks");
        final GameResources resources = session.getRenderer().getSession().getResources();
        for(Block block: blocks.values())
            block.load(resources);
    }

    public Block get(byte ID){
        return blocks.get(ID);
    }

    public void register(Block properties){
        blocks.put(properties.getID(), properties);
    }

}
