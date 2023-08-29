package jpize.tests.minecraftosp.server.level.light;

import jpize.math.vecmath.vector.Vec2i;
import jpize.math.vecmath.vector.Vec3i;
import jpize.tests.minecraftosp.client.block.BlockProps;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.Dir;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftosp.main.chunk.storage.Heightmap;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.main.net.packet.CBPacketLightUpdate;
import jpize.tests.minecraftosp.server.chunk.ServerChunk;
import jpize.tests.minecraftosp.server.level.ServerLevel;

import java.util.concurrent.ConcurrentLinkedQueue;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class LevelSkyLight{


    /**            --------- ALGORITHM ---------            **/


    private final ServerLevel level;
    private final ConcurrentLinkedQueue<LightNode> bfsIncreaseQueue, bfsDecreaseQueue;

    public LevelSkyLight(ServerLevel level){
        this.level = level;
        this.bfsIncreaseQueue = new ConcurrentLinkedQueue<>();
        this.bfsDecreaseQueue = new ConcurrentLinkedQueue<>();
    }


    /** Распространение света */
    public synchronized void increase(ServerChunk chunk, int lx, int y, int lz, int level){
        if(chunk.getSkyLight(lx, y, lz) >= level)
            return;

        addIncreaseWithLightSet(chunk, lx, y, lz, level);
        propagateIncrease();
    }

    private synchronized void addIncrease(ServerChunk chunk, int lx, int y, int lz, int level){
        bfsIncreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private synchronized void addIncreaseWithLightSet(ServerChunk chunk, int lx, int y, int lz, int level){
        chunk.setSkyLight(lx, y, lz, level);
        addIncrease(chunk, lx, y, lx, level);
    }

    /** Алгоритм распространения света */
    private synchronized void propagateIncrease(){
        ServerChunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        int targetLevel;

        // Итерируемся по нодам в очереди
        while(!bfsIncreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsIncreaseQueue.poll();

            final ServerChunk chunk = lightEntry.chunk;
            final byte x = lightEntry.lx;
            final short y = lightEntry.y;
            final byte z = lightEntry.lz;
            final byte level = lightEntry.level;

            // Проверка каждого из 6 блоков вокруг текущего[x, y, z]
            for(int i = 0; i < 6; i++){
                // Находим нормаль
                final Vec3i normal = Dir.values()[i].getNormal();

                // Координаты соседнего блока
                neighborX = x + normal.x;
                neighborZ = z + normal.z;

                // Находим чанк соседнего блока
                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    // Если координаты выходят за границы чанка - найти соответствующий чанк
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    // Нормализуем координаты для найденного чанка
                    neighborX = getLocalCoord(neighborX);
                    neighborZ = getLocalCoord(neighborZ);
                }else
                    // Если нет - выбрать данный чанк
                    neighborChunk = chunk;

                // Координата Y соседнего блока
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                // Узнать уровень освещенности соседнего блока
                final int neighborLevel = neighborChunk.getSkyLight(neighborX, neighborY, neighborZ);

                // Если соседний уровень равен данному, или же больше - его увеличивать не нужно, так как этот свет уже исходит от другого источника
                if(neighborLevel >= level)
                    continue;

                // Находим уровень освещенности который должен быть у соседнего, учитывая непрозрачность блока
                final BlockProps neighborProperties = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);
                targetLevel = level - Math.max(1, neighborProperties.getOpacity());

                // Если имеет смысл - распространяем свет уже от соседнего блока
                if(targetLevel > neighborLevel)
                    addIncreaseWithLightSet(neighborChunk, neighborX, neighborY, neighborZ, targetLevel);
            }
        }
    }


    /** Удаление света */
    public synchronized void decrease(ServerChunk chunk, int lx, int y, int lz){
        addDecreaseWithLightSet(chunk, lx, y, lz, chunk.getSkyLight(lx, y, lz));
        propagateDecrease();
    }

    private synchronized void addDecrease(ServerChunk chunk, int lx, int y, int lz, int level){
        bfsDecreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private synchronized void addDecreaseWithLightSet(ServerChunk chunk, int lx, int y, int lz, int level){
        chunk.setSkyLight(lx, y, lz, 0);
        addDecrease(chunk, lx, y, lz, level);
    }

    /** Алгоритм удаления света **/
    private synchronized void propagateDecrease(){
        ServerChunk neighborChunk;
        int neighborX, neighborY, neighborZ;

        // Итерируемся по нодам в очереди
        while(!bfsDecreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsDecreaseQueue.poll();

            final ServerChunk chunk = lightEntry.chunk;
            final byte x = lightEntry.lx;
            final short y = lightEntry.y;
            final byte z = lightEntry.lz;
            final byte level = lightEntry.level;

            // Проверка каждого из 6 блоков вокруг текущего[x, y, z]
            for(int i = 0; i < 6; i++){
                // Находим нормаль
                final Vec3i normal = Dir.normal3DFromIndex(i);

                // Координаты соседнего блока
                neighborX = x + normal.x;
                neighborZ = z + normal.z;

                // Находим чанк соседнего блока
                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    // Если координаты выходят за границы чанка - найти соответствующий чанк
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    // Нормализуем координаты для найденного чанка
                    neighborX = getLocalCoord(neighborX);
                    neighborZ = getLocalCoord(neighborZ);
                }else
                    // Если нет - выбрать данный чанк
                    neighborChunk = chunk;

                // Координата Y соседнего блока
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                // Узнать уровень освещенности соседнего блока
                final int neighborLevel = neighborChunk.getSkyLight(neighborX, neighborY, neighborZ);
                // Если он равен 0 - уменьшать дальше нечего
                if(neighborLevel != 0)
                    continue;

                // Если соседний уровень освещенности меньше данного - зануляем его и уменьшаем освещение с его позиции
                if(neighborLevel < level){
                    final BlockProps neighborBlock = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);
                    // Находим уровень света, учитывая непрозрачность блока
                    // (всегда будет на 0-15 больше чем уровень освещенности в данном блоке)
                    final int decreaseLevel = Math.max(0, neighborLevel - neighborBlock.getOpacity());
                    addDecreaseWithLightSet(neighborChunk, neighborX, neighborY, neighborZ, decreaseLevel);
                }else
                    // Если соседний уровень равен данному, или же больше - его уменьшать нельзя, так как этот свет уже исходит от другого источника
                    // Просто увеличиваем от него свет, так как до этого все зануляли
                    addIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);

            }
        }

        propagateIncrease();
    }


    /**            --------- UPDATE ---------            **/


    private synchronized void updateSideSkyLight(ServerChunk chunk, int lx, int lz){
        final ChunkPos chunkPos = chunk.getPosition();

        final Heightmap heightmapLight = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
        final int height = heightmapLight.getHeight(lx, lz);

        // Алгоритм для каждой из 4 сторон
        for(int i = 0; i < 4; i++){
            final Vec2i dirNor = Dir.normal2DFromIndex(i);
            final int x = chunkPos.globalX() + lx + dirNor.x;
            final int z = chunkPos.globalZ() + lz + dirNor.y;

            final ServerChunk sideChunk = level.getBlockChunk(x, z);
            if(sideChunk == null)
                continue;

            final Heightmap sideHeightmapLight = sideChunk.getHeightMap(HeightmapType.LIGHT_SURFACE);

            final int sideLx = getLocalCoord(x);
            final int sideLz = getLocalCoord(z);

            // Сравниваем высоту в середине с высотой на каждой стороне
            final int sideHeight = sideHeightmapLight.getHeight(sideLx, sideLz);
            if(sideHeight <= height)
                continue;

            int checkY = height + 1;
            if(checkY == sideHeight)
                continue;

            // Заполняем пробел светом (места под деревьями, открытые пещеры, и др.)
            for(; checkY < sideHeight; checkY++){
                if(chunk.getBlock(sideLx, checkY, sideLz) != Blocks.AIR)
                    continue;

                addIncrease(sideChunk, sideLx, checkY, sideLz, MAX_LIGHT_LEVEL);
            }
        }

        propagateIncrease();
    }

    public synchronized void updateSkyLight(ServerChunk chunk){
        final Heightmap heightmapLight = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);

        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                final int height = heightmapLight.getHeight(lx, lz) + 1;

                increase(chunk, lx, height, lz, MAX_LIGHT_LEVEL);
                updateSideSkyLight(chunk, lx, lz);

                for(int y = height; y < HEIGHT; y++)
                    chunk.setSkyLight(lx, y, lz, MAX_LIGHT_LEVEL);
            }
        }
    }

    public synchronized void updateSkyLight(ServerChunk chunk, int lx, int lz){
        final Heightmap heightmapLight = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
        final int height = heightmapLight.getHeight(lx, lz) + 1;

        for(int y = height; y < HEIGHT; y++)
            addIncrease(chunk, lx, y, lz, MAX_LIGHT_LEVEL);
        propagateIncrease();

        // updateSideSkyLight(chunk, lx, lz);
    }


    public synchronized void placeBlockUpdate(ServerChunk chunk, int oldHeight, int lx, int y, int lz){
        System.out.println("y: " + y + ", oldY: " + oldHeight);
        for(int i = y; i > oldHeight; i--)
            decrease(chunk, lx, i, lz);
    }

    public synchronized void destroyBlockUpdate(ServerChunk chunk, int newHeight, int lx, int y, int lz){
        for(int i = y; i > newHeight; i--)
            increase(chunk, lx, y, lz, MAX_LIGHT_LEVEL);

        // updateSkyLight(chunk, lx, lz);

        /*
        ServerChunk neighborChunk;
        int neighborX, neighborY, neighborZ;

        for(int i = 0; i < 6; i++){
            final Vec3i normal = Direction.normal3DFromIndex(i);

            neighborX = lx + normal.x;
            neighborZ = lz + normal.z;

            if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                if(neighborChunk == null)
                    continue;

                neighborX = getLocalCoord(neighborX);
                neighborZ = getLocalCoord(neighborZ);
            }else
                neighborChunk = chunk;

            neighborY = y + normal.y;
            if(neighborY < 0 || neighborY > HEIGHT_IDX)
                continue;

            final int neighborLevel = neighborChunk.getLight(neighborX, neighborY, neighborZ);
            if(neighborLevel > 1){
                addIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);
                propagateIncrease();
            }
        }
        */
    }


    /**            --------- NET ---------            **/


    public void sendSections(ServerChunk chunk, int y){
        if(y > SIZE_IDX && chunk.getBlockSection(y - 16) != null){
            level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getBlockSection(y - 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(-1, 0).getBlockSection(y - 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(1, 0).getBlockSection(y - 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0, -1).getBlockSection(y - 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0, 1).getBlockSection(y - 16)));
        }

        level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getBlockSection(y)));
        level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(-1, 0).getBlockSection(y)));
        level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk( 1, 0).getBlockSection(y)));
        level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0, -1).getBlockSection(y)));
        level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0,  1).getBlockSection(y)));

        if(y < HEIGHT - SIZE && chunk.getBlockSection(y + 16) != null){
            level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getBlockSection(y + 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(-1, 0).getBlockSection(y + 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(1, 0).getBlockSection(y + 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0, -1).getBlockSection(y + 16)));
            // level.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(chunk.getNeighborChunk(0, 1).getBlockSection(y + 16)));
        }
    }

}
