package pize.tests.terraria;

import pize.Pize;
import pize.activity.ActivityListener;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.point.Point2f;
import pize.tests.terraria.entity.Player;
import pize.tests.terraria.graphics.GameRenderer;
import pize.tests.terraria.world.World;
import pize.tests.terraria.map.MapTile;

import static pize.tests.terraria.tile.TileType.AIR;
import static pize.tests.terraria.tile.TileType.DIRT;

public class Main implements ActivityListener{

    public static void main(String[] args){
        Pize.create("Terraria", 1280, 720);
        Pize.window().setIcon("icon.png");
        Pize.run(new Main());
    }


    private World world;
    private GameRenderer gameRenderer;

    private TextureBatch batch;
    private BitmapFont font;
    private Player player;
    
    @Override
    public void init(){
        gameRenderer = new GameRenderer();
        world = new World(300, 100);

        player = new Player();
        player.pos().set(world.getTileMap().getWidth() / 2F - 1.5F, world.getTileMap().getHeight());
        world.getEntities().add(player);

        batch = new TextureBatch();
        font = FontLoader.loadTrueType("font/andy_bold.ttf", 32);
    }

    @Override
    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.14, 0.43, 0.8);
        ctrl();

        batch.begin();
        gameRenderer.update();
        gameRenderer.renderMap(world.getTileMap());
        gameRenderer.renderEntities(world.getEntities());
        player.update(world);

        font.drawText(batch, "fps: " + Pize.getFPS(), 10, 10);
        batch.end();

        if(Pize.isDown(Key.ESCAPE))
            Pize.exit();
        if(Pize.isDown(Key.F11))
            Pize.window().toggleFullscreen();
        if(Pize.isDown(Key.V))
            Pize.window().toggleVsync();
    }

    private void ctrl(){
        int scroll = Pize.mouse().getScroll();
        gameRenderer.getRenderInfo().mulScale(
            scroll < 0 ?
                1 / Math.pow(1.1, Maths.abs(scroll)) : scroll > 0 ?
                Math.pow(1.1, Maths.abs(scroll))
                : 1
        );

        gameRenderer.getCamera().getPos().set( player.pos().clone().add(player.rect().getCenter()) );

        Point2f touch = new Point2f(Pize.getX(), Pize.getY())
            .sub(gameRenderer.getCamera().getWidth() / 2F, gameRenderer.getCamera().getHeight() / 2F)
            .div(gameRenderer.getRenderInfo().getCellSize() * gameRenderer.getRenderInfo().getScale())
            .add(gameRenderer.getCamera().getPos());

        MapTile tile = world.getTileMap().getTile(touch.xf(), touch.yf());
        if(tile != null && Pize.isTouched())
            tile.setType(Pize.mouse().isLeftPressed() ? AIR : DIRT);

        if(player.pos().y < -100)
            player.pos().y = world.getTileMap().getHeight();
    }

    @Override
    public void resize(int width, int height){
        gameRenderer.getCamera().resize(width, height);
    }

    @Override
    public void dispose(){

    }

}
