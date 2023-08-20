package pize.tests.minecraftosp.main.registry;

import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.resources.GameResources;

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
