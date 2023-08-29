package pize.tests.terraria;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.glfw.key.Key;
import pize.io.context.ContextBuilder;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.terraria.entity.Player;
import pize.tests.terraria.graphics.GameRenderer;
import pize.tests.terraria.map.MapTile;
import pize.tests.terraria.world.World;

import static pize.tests.terraria.tile.TileType.AIR;
import static pize.tests.terraria.tile.TileType.DIRT;

public class Main extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Terraria")
                .size(1280, 720)
                .icon("icon.png")
                .create()
                .init(new Main());

        Jize.runContexts();
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

        font.drawText(batch, "fps: " + Jize.getFPS(), 10, 10);
        batch.end();

        if(Key.ESCAPE.isDown())
            Jize.exit();
        if(Key.F11.isDown())
            Jize.window().toggleFullscreen();
    }

    private void ctrl(){
        final float scroll = Jize.mouse().getScroll();
        gameRenderer.getRenderInfo().mulScale(
            scroll < 0 ?
                1 / Math.pow(1.1, Maths.abs(scroll)) : scroll > 0 ?
                Math.pow(1.1, Maths.abs(scroll))
                : 1
        );

        gameRenderer.getCamera().getPosition().set( player.pos().copy().add(player.rect().getCenter()) );

        Vec2f touch = new Vec2f(Jize.getX(), Jize.getY())
            .sub(gameRenderer.getCamera().getWidth() / 2F, gameRenderer.getCamera().getHeight() / 2F)
            .div(gameRenderer.getRenderInfo().getCellSize() * gameRenderer.getRenderInfo().getScale())
            .add(gameRenderer.getCamera().getPosition());

        MapTile tile = world.getTileMap().getTile(touch.xf(), touch.yf());
        if(tile != null && Jize.isTouched())
            tile.setType(Jize.mouse().isLeftPressed() ? AIR : DIRT);

        if(player.pos().y < -100)
            player.pos().y = world.getTileMap().getHeight();
    }

    @Override
    public void resize(int width, int height){
        gameRenderer.getCamera().resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

}
