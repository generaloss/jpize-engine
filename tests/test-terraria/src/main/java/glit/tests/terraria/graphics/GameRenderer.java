package glit.tests.terraria.graphics;

import glit.graphics.camera.Camera2D;
import glit.graphics.camera.CenteredOrthographicCamera;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureRegion;
import glit.graphics.util.batch.TextureBatch;
import glit.math.Maths;
import glit.tests.terraria.entity.Entity;
import glit.tests.terraria.map.MapTile;
import glit.tests.terraria.map.WorldMap;

import static glit.tests.terraria.tile.TileType.AIR;

public class GameRenderer{

    private final MapRenderInfo renderInfo;
    private final TextureBatch mapBatch, entityBatch;
    private final CenteredOrthographicCamera camera;

    private final TextureRegion tileTexture;

    public GameRenderer(){
        renderInfo = new MapRenderInfo();

        mapBatch = new TextureBatch(100000);
        entityBatch = new TextureBatch(500);
        camera = new CenteredOrthographicCamera();

        tileTexture = new TextureRegion(new Texture("texture/Tiles_2.png"), 18 * 1, 18 * 1, 16, 16);
    }
    

    public void update(){
        renderInfo.update();

        camera.setScale(renderInfo.getCellSize() * renderInfo.getScale());
        camera.update();
    }

    public void renderMap(WorldMap map){
        float camHalfWidth = renderInfo.getCellsPerWidth() / 2 / renderInfo.getScale();
        float camHalfHeight = renderInfo.getCellsPerHeight() / 2 / renderInfo.getScale();

        int beginX = Maths.floor(camera.getX() - camHalfWidth);
        int beginY = Maths.floor(camera.getY() - camHalfHeight);
        int endX = Maths.ceil(camera.getX() + camHalfWidth);
        int endY = Maths.ceil(camera.getY() + camHalfHeight);

        beginX = Math.max(beginX, 0);
        beginY = Math.max(beginY, 0);
        endX = Math.min(endX, map.getWidth());
        endY = Math.min(endY, map.getHeight());

        mapBatch.begin(camera);
        for(int i = beginX; i < endX; i++)
            for(int j = beginY; j < endY; j++){
                MapTile tile = map.getTile(i, j);
                if(tile != null && tile.getType() != AIR)
                    mapBatch.draw(tileTexture, i, j, 1, 1);
            }
        mapBatch.end();
    }

    public void renderEntities(Iterable<Entity> entities){
        entityBatch.begin(camera);

        for(Entity entity: entities)
            entity.render(entityBatch);

        entityBatch.end();
    }

    public Camera2D getCamera(){
        return camera;
    }

    public MapRenderInfo getRenderInfo(){
        return renderInfo;
    }
    
}
