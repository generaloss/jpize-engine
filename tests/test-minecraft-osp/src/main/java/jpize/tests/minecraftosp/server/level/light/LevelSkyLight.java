package jpize.tests.minecraftosp.server.level.light;

import jpize.math.vecmath.vector.Vec2i;
import jpize.math.vecmath.vector.Vec3i;
import jpize.tests.minecraftosp.client.block.BlockProps;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.Dir;
import jpize.tests.minecraftosp.main.chunk.LevelChunk;
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
    public void increase(LevelChunk chunk, int lx, int y, int lz, int level){
        if(chunk.getSkyLight(lx, y, lz) >= level)
            return;

        addIncreaseWithLightSet(chunk, lx, y, lz, level);
        propagateIncrease();
    }

    private void addIncrease(LevelChunk chunk, int lx, int y, int lz, int level){
        bfsIncreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private void addIncreaseWithLightSet(LevelChunk chunk, int lx, int y, int lz, int level){
        chunk.setSkyLight(lx, y, lz, level);
        addIncrease(chunk, lx, y, lz, level);
    }

    /** Алгоритм распространения света */
    private void propagateIncrease(){
        LevelChunk neighborChunk;
        int neighborLX, neighborY, neighborLZ;
        int targetLevel;

        // Итерируемся по нодам в очереди
        while(!bfsIncreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsIncreaseQueue.poll();

            final LevelChunk chunk = lightEntry.chunk;
            final byte lx = lightEntry.lx;
            final short y = lightEntry.y;
            final byte lz = lightEntry.lz;
            final byte level = lightEntry.level;

            // Проверка каждого из 6 блоков вокруг текущего[x, y, z]
            for(int i = 0; i < 6; i++){
                // Находим нормаль
                final Vec3i normal = Dir.getNormal3(i);

                // Координаты соседнего блока
                neighborLX = lx + normal.x;
                neighborLZ = lz + normal.z;

                // Находим чанк соседнего блока
                if(neighborLX > SIZE_IDX || neighborLZ > SIZE_IDX || neighborLX < 0 || neighborLZ < 0){
                    // Если координаты выходят за границы чанка - найти соответствующий чанк
                    neighborChunk = chunk.getNeighborChunk(normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    // Нормализуем координаты для найденного чанка
                    neighborLX = getLocalCoord(neighborLX);
                    neighborLZ = getLocalCoord(neighborLZ);
                }else
                    // Если нет - выбрать данный чанк
                    neighborChunk = chunk;

                // Координата Y соседнего блока
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                // Узнать уровень освещенности соседнего блока
                final int neighborLevel = neighborChunk.getSkyLight(neighborLX, neighborY, neighborLZ);

                // Если соседний уровень равен данному, или же больше - его увеличивать не нужно, так как этот свет уже исходит от другого источника
                if(neighborLevel >= level - 1)
                    continue;

                // Находим уровень освещенности который должен быть у соседнего, учитывая непрозрачность блока
                final BlockProps neighborProperties = neighborChunk.getBlockProps(neighborLX, neighborY, neighborLZ);
                targetLevel = level - Math.max(1, neighborProperties.getOpacity());

                // Если имеет смысл - распространяем свет уже от соседнего блока
                if(targetLevel > neighborLevel)
                    addIncreaseWithLightSet(neighborChunk, neighborLX, neighborY, neighborLZ, targetLevel);
            }
        }
    }


    /** Удаление света */
    public void decrease(LevelChunk chunk, int lx, int y, int lz){
        final int level = chunk.getSkyLight(lx, y, lz);
        if(level == 0)
            return;

        addDecreaseWithLightSet(chunk, lx, y, lz, level);
        propagateDecrease();
    }

    private void addDecrease(LevelChunk chunk, int lx, int y, int lz, int level){
        bfsDecreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private void addDecreaseWithLightSet(LevelChunk chunk, int lx, int y, int lz, int level){
        chunk.setSkyLight(lx, y, lz, 0);
        addDecrease(chunk, lx, y, lz, level);
    }

    /** Алгоритм удаления света **/
    private void propagateDecrease(){
        LevelChunk neighborChunk;
        int neighborLX, neighborY, neighborLZ;

        // Итерируемся по нодам в очереди
        while(!bfsDecreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsDecreaseQueue.poll();

            final LevelChunk chunk = lightEntry.chunk;
            final byte lx = lightEntry.lx;
            final short y = lightEntry.y;
            final byte lz = lightEntry.lz;
            final byte level = lightEntry.level;

            // Проверка каждого из 6 блоков вокруг текущего[x, y, z]
            for(int i = 0; i < 6; i++){
                // Находим нормаль
                final Vec3i normal = Dir.getNormal3(i);

                // Локальные координаты соседнего блока
                neighborLX = lx + normal.x;
                neighborLZ = lz + normal.z;

                // Находим чанк соседнего блока
                if(neighborLX > SIZE_IDX || neighborLZ > SIZE_IDX || neighborLX < 0 || neighborLZ < 0){
                    // Если координаты выходят за границы чанка - найти соответствующий чанк
                    neighborChunk = chunk.getNeighborChunk(normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    // Нормализуем координаты для найденного чанка
                    neighborLX = getLocalCoord(neighborLX);
                    neighborLZ = getLocalCoord(neighborLZ);
                }else
                    // Если нет - выбрать данный чанк
                    neighborChunk = chunk;

                // Координата Y соседнего блока
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                // Узнать уровень освещенности соседнего блока
                final int neighborLevel = neighborChunk.getSkyLight(neighborLX, neighborY, neighborLZ);
                // Если он равен 0 - уменьшать дальше нечего
                // Если соседний уровень освещенности меньше данного - зануляем его и уменьшаем освещение с его позиции
                if(neighborLevel != 0 && neighborLevel < level){
                    final BlockProps neighborBlock = neighborChunk.getBlockProps(neighborLX, neighborY, neighborLZ);
                    // Находим уровень света, учитывая непрозрачность блока
                    // (всегда будет на 0-15 больше чем уровень освещенности в данном блоке)
                    final int decreaseLevel = Math.max(level, neighborLevel + neighborBlock.getOpacity());
                    // Math.max(0, neighborLevel - neighborBlock.getOpacity())
                    addDecreaseWithLightSet(neighborChunk, neighborLX, neighborY, neighborLZ, decreaseLevel);
                }else if(neighborLevel >= level)
                    // Если соседний уровень равен данному, или же больше - его уменьшать нельзя, так как этот свет уже исходит от другого источника
                    // Просто увеличиваем от него свет, так как до этого все зануляли
                    addIncrease(neighborChunk, neighborLX, neighborY, neighborLZ, neighborLevel);

            }
        }

        propagateIncrease();
    }


    /**            --------- UPDATE ---------            **/

    private void updateSideSkyLight(LevelChunk chunk, int lx, int lz){
        final ChunkPos chunkPos = chunk.getPosition();

        // Находим высоту
        final Heightmap heightmap = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
        // Сразу выбираем высоту над блоком
        final int height = heightmap.getHeight(lx, lz) + 1;

        // Алгоритм для каждой из 4 сторон
        for(int i = 0; i < 4; i++){
            // Находим нормаль
            final Vec2i dirNormal = Dir.getNormal2(i);

            // Глобальные координаты соседнего блока
            final int sideGlobalX = chunkPos.globalX() + lx + dirNormal.x;
            final int sideGlobalZ = chunkPos.globalZ() + lz + dirNormal.y;
            // Находим чанк соседнего блока
            final LevelChunk sideChunk = level.getBlockChunk(sideGlobalX, sideGlobalZ);
            if(sideChunk == null)
                continue;

            // Находим локальные координаты
            final int sideLocalX = getLocalCoord(sideGlobalX);
            final int sideLocalZ = getLocalCoord(sideGlobalZ);

            // Находим высоту соседнего блока
            final Heightmap sideHeightmap = sideChunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
            final int sideHeight = sideHeightmap.getHeight(sideLocalX, sideLocalZ);
            // Сравниваем высоту в середине с высотой соседних блоков
            if(sideHeight <= height)
                continue;

            // Debug
            sideChunk.setBlock(sideLocalX, height, sideLocalZ, Blocks.GLASS);
            sideChunk.setBlock(sideLocalX, sideHeight - 1, sideLocalZ, Blocks.GLASS);

            // Заполняем пробел светом (места под деревьями, открытые пещеры, и др.)
            for(int checkY = height; checkY < sideHeight; checkY++){
                // Избегаем непрозрачных блоков со сторон на протяжении всего "столбообразного" участка
                final BlockProps neighborBlock = chunk.getBlockProps(sideLocalX, checkY, sideLocalZ);
                if(!neighborBlock.isLightTranslucent())
                    continue;

                // Свет
                addIncrease(sideChunk, sideLocalX, checkY, sideLocalZ, MAX_LIGHT_LEVEL - 1);
            }
        }

        propagateIncrease();
    }

    public void updateSkyLight(ServerChunk chunk){
        // Освещаем столбы с неба
        final Heightmap heightmapLight = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                final int height = heightmapLight.getHeight(lx, lz) + 1;

                addIncrease(chunk, lx, height, lz, MAX_LIGHT_LEVEL);

                for(int y = height; y < HEIGHT; y++)
                    chunk.setSkyLight(lx, y, lz, MAX_LIGHT_LEVEL);
            }
        }

        propagateIncrease();

        // Освещаем области под деревьями и другие пробелы
        for(int lx = 0; lx < SIZE; lx++)
            for(int lz = 0; lz < SIZE; lz++)
                updateSideSkyLight(chunk, lx, lz);
    }



    public void updateSkyLight(ServerChunk chunk, int lx, int lz){
        final Heightmap heightmapLight = chunk.getHeightMap(HeightmapType.LIGHT_SURFACE);
        final int height = heightmapLight.getHeight(lx, lz) + 1;

        for(int y = height; y < HEIGHT; y++)
            addIncrease(chunk, lx, y, lz, MAX_LIGHT_LEVEL);
        propagateIncrease();

        // updateSideSkyLight(chunk, lx, lz);
    }


    public void placeBlockUpdate(ServerChunk chunk, int oldHeight, int lx, int y, int lz){
        System.out.println("y: " + y + ", oldY: " + oldHeight);
        for(int i = y; i > oldHeight; i--)
            decrease(chunk, lx, i, lz);
    }

    public void destroyBlockUpdate(ServerChunk chunk, int newHeight, int lx, int y, int lz){
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
