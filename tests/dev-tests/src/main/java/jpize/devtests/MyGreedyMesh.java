package jpize.devtests;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.glfw.key.Key;
import jpize.graphics.camera.OrthographicCamera;
import jpize.graphics.mesh.QuadMesh;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.Maths;

public class MyGreedyMesh extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(1440, 1280, "My Greedy Meshing")
                .resizable(false)
                .register().setAdapter(new MyGreedyMesh());

        Jpize.runContexts();
    }

    int selectedTile = 1;
    int gridSize = 32;
    int[][] tiles;
    int size, x, y;

    QuadMesh mesh;
    BaseShader shader;
    OrthographicCamera camera;
    TextureBatch batch;
    Color[] tileColors = new Color[]{ new Color(0.7, 1, 0.7), new Color(0, 0.7, 0.7) };

    public void init(){
        batch = new TextureBatch(32);

        tiles = new int[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                tiles[i][j] = Maths.random(1, 2);

        size = (Jpize.getHeight() - 100) / gridSize;
        x = Jpize.getWidth() / 2 - size * gridSize / 2;
        y = 50;

        camera = new OrthographicCamera();
        shader = BaseShader.getPos2Color();
        mesh = new QuadMesh(
                gridSize * gridSize,
                new GlVertexAttr(2, GlType.FLOAT), // position
                new GlVertexAttr(4, GlType.FLOAT)  // color
        );

        //greedy();
    }

    private void greedy(){
        quads = 0;
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                int tile = tiles[i][j];
                Color color = tileColors[tile - 1];
                quad(x + i * size, y + j * size, size, size, color);
            }
        }
    }

    int quads = 0;

    private void quad(int x, int y, int width, int height, Color color){
        int offset = quads * (2 + 4) * 4 * GlType.FLOAT.getSize();
        float[] quadVertices = {
                x        , y + height, color.r(), color.g(), color.b(), color.a(),
                x        , y         , color.r(), color.g(), color.b(), color.a(),
                x + width, y         , color.r(), color.g(), color.b(), color.a(),
                x + width, y + height, color.r(), color.g(), color.b(), color.a()
        };
        mesh.getBuffer().setSubData(offset, quadVertices);
        quads++;
    }


    public void update(){
        // Exit
        if(Key.ESCAPE.isDown()) Jpize.exit();
        // Select
        if(Key.NUM_1.isDown()) selectedTile = 1;
        if(Key.NUM_2.isDown()) selectedTile = 2;
        // Clear
        if(Key.C.isDown())
            for(int i = 0; i < gridSize; i++)
                for(int j = 0; j < gridSize; j++)
                    tiles[i][j] = 2;

        camera.update();
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.25);

        shader.bind();
        shader.setMatrices(camera);
        mesh.render();

        // batch.begin();
        // if(Jpize.mouse().isInBounds(x, y, size * gridSize, size * gridSize)){
        //     int i = Maths.floor((Jpize.getX() - x) / size);
        //     int j = Maths.floor((Jpize.getY() - y) / size);

        //     if(Jpize.isTouched())
        //         tiles[i][j] = selectedTile;

        //     batch.drawQuad(1, 1, 1, 0.3, x + i * size, y + j * size, size, size);
        // }
        // batch.end();
    }


    public void dispose(){
        batch.dispose();
        shader.dispose();
        mesh.dispose();
    }

}
