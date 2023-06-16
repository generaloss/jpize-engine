package pize.tests.minecraft.run;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2i;

import java.util.ArrayList;

public class GreedyMeshTest extends AppAdapter{

    private TextureBatch batch;
    private Texture voxelTexture;

    private final int WIDTH = 5;
    private final int HEIGHT = 5;
    private final int SIZE = 140;

    private IColor[] colors;
    private int[][] map;
    private Vec2i[][] sizes;

    public ArrayList<Quad> mesh;


    @Override
    public void init(){
        batch = new TextureBatch(5000);

        Pixmap voxelPixmap = new Pixmap(16, 16);
        voxelPixmap.fill(0, 0, 15, 15, 1, 1, 1, 1F);

        voxelPixmap.fill(0, 0, 0, 15, 0, 0, 0, 1F);
        voxelPixmap.fill(0, 0, 15, 0, 0, 0, 0, 1F);

        voxelPixmap.fill(15, 0, 15, 15, 0.5F, 0.5F, 0.5F, 1F);
        voxelPixmap.fill(0, 15, 14, 15, 0.5F, 0.5F, 0.5F, 1F);
        voxelTexture = new Texture(voxelPixmap);

        map = new int[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++)
                map[i][j] = Maths.random(2);

        sizes = new Vec2i[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++)
                sizes[i][j] = new Vec2i(1);

        colors = new IColor[3];
        colors[0] = new Color(1, 0, 0, 1F);
        colors[1] = new Color(0, 1, 0, 1F);
        colors[2] = new Color(0, 0, 1, 1F);

        mesh = new ArrayList<>();

        buildMesh();
    }

    public void buildMesh(){
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++)
                sizes[i][j].set(1);

        mesh.clear();

        for(int j = 0; j < HEIGHT; j++)
            for(int i = 0; i < WIDTH - 1; i++)
                if(map[i][j] == map[i + 1][j]){
                    int offset = Math.min(sizes[i][j].x, 0);
                    Vec2i size = sizes[i + offset][j];

                    size.x += sizes[i + 1][j].x;
                    sizes[i + 1][j].x = offset - 1;
                }

        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT - 1; j++)
                if(map[i][j] == map[i][j + 1]){
                    if(sizes[i][j].y < 0 || sizes[i][j].x < 0)
                        continue;

                    int offsetX = Math.min(sizes[i][j].y, 0);

                    System.out.println(offsetX);

                    if(sizes[i][j].x == sizes[i][j + 1].x){
                        int offsetY = Math.min(sizes[i][j].y, 0);
                        Vec2i size = sizes[i][j + offsetY];

                        size.y += sizes[i][j + 1].y;
                        sizes[i][j + 1].y = offsetY - 1;

                        if(i + offsetY < WIDTH - 1)
                            i += offsetY;
                    }
                }

        for(int j = 0; j < HEIGHT; j++)
            for(int i = 0; i < WIDTH; i++){
                if(sizes[i][j].y < 0 || sizes[i][j].x < 0)
                    continue;

                Vec2i size = sizes[i][j];
                mesh.add(new Quad(i, j, size.x, size.y, map[i][j]));
            }
    }

    @Override
    public void render(){
        Pize.window().setTitle("Minecraft (fps: " + Pize.getFPS() + ")");

        if(Key.ESCAPE.isDown())
            Pize.exit();
        if(Key.F11.isDown())
            Pize.window().toggleFullscreen();
        if(Key.V.isDown())
            Pize.window().toggleVsync();

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.6, 1);
        batch.begin();

        if(Key.S.isPressed())
            for(int i = 0; i < WIDTH; i++)
                for(int j = 0; j < HEIGHT; j++){
                    batch.setColor(colors[map[i][j]]);
                    batch.draw(voxelTexture, i * SIZE, j * SIZE, SIZE, SIZE);
                }
        else{
            for(Quad quad: mesh){
                batch.setColor(colors[quad.color]);
                batch.draw(voxelTexture, quad.x * SIZE, quad.y * SIZE, quad.width * SIZE, quad.height * SIZE);
            }
        }

        if(Key.S.isDown() || Key.S.isReleased())
            System.out.println(batch.size());

        if(Pize.isTouched()){
            final int color = Pize.mouse().isLeftPressed() ? 1 : 0;

            int x = Pize.getX() / SIZE;
            int y = Pize.getY() / SIZE;

            if(map[x][y] != color){
                map[x][y] = color;
                buildMesh();
            }
        }

        batch.end();
    }
    
    @Override
    public void dispose(){
        voxelTexture.dispose();
        batch.dispose();
    }

    record Quad(int x, int y, int width, int height, int color){ }

}
