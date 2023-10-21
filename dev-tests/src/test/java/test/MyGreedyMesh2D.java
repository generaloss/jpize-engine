package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.gl.texture.GlSizedFormat;
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

import java.util.Arrays;

public class MyGreedyMesh2D extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(720, 720, "My Greedy Meshing")
                .resizable(false)
                .register().setAdapter(new MyGreedyMesh2D());

        Jpize.runContexts();
    }

    final int gridSize = 16;
    final int[][] tiles = new int[gridSize][gridSize];
    int quadSize;

    QuadMesh mesh;
    BaseShader shader;
    OrthographicCamera camera;
    Color[] tileColors = new Color[]{ new Color(0.2, 0.4, 0.2), new Color(0.6, 0.8, 0.6) };

    TextureBatch batch;

    public void init(){
        batch = new TextureBatch(32);

        quadSize = Jpize.getHeight() / gridSize;

        camera = new OrthographicCamera();
        shader = BaseShader.getPos2Color();
        mesh = new QuadMesh(
                gridSize * gridSize,
                new GlVertexAttr(2, GlType.FLOAT), // position
                new GlVertexAttr(4, GlType.FLOAT)  // color
        );
        mesh.getBuffer().allocateData(gridSize * gridSize * 4 * Float.SIZE);

        Gl.polygonMode(GlPolygonMode.LINE);

        greedy();
    }

    volatile boolean greedy = false;
    final boolean[][] masks = new boolean[gridSize][gridSize];

    private void greedy(){
        //if(greedy)
        //    return;
        //greedy = true;

        // Clear buffers
        offset = 0;
        mesh.getBuffer().clearData(GlSizedFormat.RGBA8, GlType.FLOAT, new float[]{ 0, 0,  0, 0, 0, 0 });
        for(int i = 0; i < gridSize; i++)
            Arrays.fill(masks[i], false);

        //new Thread(()->{

        mainFor: for(int j = 0; j < gridSize;){
            for(int i = 0; i < gridSize;){

                // Init tile
                final int tile = tiles[i][j];
                int width = 1;
                int height = 1;

                // Set mask for tile
                masks[i][j] = true;

                // Merge X
                for(int x = i + 1; x < gridSize; x++){
                    // Try to merge line
                    final int neighborTile = tiles[x][j];
                    final boolean neighborMask = masks[x][j];
                    if(neighborTile != tile || neighborMask)
                        break;

                    // Merge and set mask
                    width++;
                    masks[x][j] = true;
                }

                // Merge Y
                mergeY: for(int y = j + 1; y < gridSize; y++){
                    // Try to merge quad
                    for(int x = i; x < i + width; x++){
                        final int neighborTile = tiles[x][y];
                        final boolean neighborMask = masks[x][y];
                        if(neighborTile != tile || neighborMask)
                            break mergeY;
                    }

                    // Merge and set masks
                    height++;
                    for(int x = i; x < i + width; x++)
                        masks[x][y] = true;
                }

                putQuad(i * quadSize, j * quadSize, width * quadSize, height * quadSize, tileColors[tile]);

                // Calc next X
                i += width - 1;
                // Calc next Y
                if(width == gridSize)
                    j += height - 1;
                else if(i == gridSize)
                    j++;
                if(j >= gridSize)
                    break;

                // Search correct X though 1 mask values
                while(masks[i][j]){
                    i++;
                    if(i >= gridSize){
                        i = 0;
                        j++;
                    }
                    if(j == gridSize)
                        break mainFor;
                }

                //Utils.delayElapsed(5);
            }

        }

        //greedy = false;
        //}).start();

    }

    int offset;
    private void putQuad(int x, int y, int width, int height, Color color){
        Jpize.execSync(()->{
            final float[] quadVertices = {
                    x        , y + height, color.r(), color.g(), color.b(), color.a(),
                    x        , y         , color.r(), color.g(), color.b(), color.a(),
                    x + width, y         , color.r(), color.g(), color.b(), color.a(),
                    x + width, y + height, color.r(), color.g(), color.b(), color.a()
            };
            mesh.getBuffer().setSubData(offset, quadVertices);
            offset += (2 + 4) * 4 * GlType.FLOAT.getSize();
        });
    }


    public void update(){
        // Exit
        if(Key.ESCAPE.isDown()) Jpize.exit();
        // Mode
        if(Key.NUM_1.isDown()) Gl.polygonMode(GlPolygonMode.FILL);
        if(Key.NUM_2.isDown()) Gl.polygonMode(GlPolygonMode.LINE);
        // Clear
        if(Key.C.isDown()){
            for(int i = 0; i < gridSize; i++)
                for(int j = 0; j < gridSize; j++)
                    tiles[i][j] = 0;
            greedy();
        }

        // Randomize
        if(Key.R.isDown())
            randomize();

        if(Key.E.isDown()){
            searchWhile: while(true){
                randomize();

                while(greedy)
                    Thread.onSpinWait();

                for(int i = 0; i < gridSize; i++)
                    for(int j = 0; j < gridSize; j++)
                        if(!masks[i][j])
                            break searchWhile;

                System.out.println("Next");
            }
        }

        camera.update();
    }

    private void randomize(){
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                tiles[i][j] = Maths.random(0, 1);
        greedy();
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.25);

        shader.bind();
        shader.setMatrices(camera);
        mesh.render();

        if(!Jpize.mouse().isInBounds(0, 0, 720, 720))
            return;

        batch.begin();
        int i = Maths.floor(Jpize.getX() / quadSize);
        int j = Maths.floor(Jpize.getY() / quadSize);

        if(Jpize.mouse().isLeftPressed()){
            tiles[i][j] = 1;
            greedy();
        }

        if(Jpize.mouse().isRightPressed()){
            tiles[i][j] = 0;
            greedy();
        }
        batch.drawQuad(1, 1, 1, 0.5, i * quadSize, j * quadSize, quadSize, quadSize);
        batch.end();
    }


    public void dispose(){
        batch.dispose();
        shader.dispose();
        mesh.dispose();
    }

}
