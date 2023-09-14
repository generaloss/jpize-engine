package jpize.devtests;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.gl.texture.GlSizedFormat;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.camera.OrthographicCamera;
import jpize.graphics.mesh.QuadMesh;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.color.Color;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;

import java.util.Arrays;

public class MyGreedyMesh3D extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(1440, 720, "My Greedy Meshing")
                .resizable(false)
                .register().setAdapter(new MyGreedyMesh3D());

        Jpize.runContexts();
    }


    // VOXELS


    final int GRID_SIZE = 16;
    final int[] masks = new int[GRID_SIZE * GRID_SIZE * GRID_SIZE];
    final int[] voxels = new int[GRID_SIZE * GRID_SIZE * GRID_SIZE];

    public int getIndex(int x, int y, int z){
        return x + y * GRID_SIZE + z * GRID_SIZE * GRID_SIZE;
    }

    public int getVoxel(int x, int y, int z){
        return voxels[getIndex(x, y, z)];
    }

    public void setVoxel(int x, int y, int z, int block){
        voxels[getIndex(x, y, z)] = block;
    }

    public int getMask(int x, int y, int z){
        return voxels[getIndex(x, y, z)];
    }

    public void setMask(int x, int y, int z, int mask){
        voxels[getIndex(x, y, z)] = mask;
    }


    final Color[] BLOCK_COLORS = new Color[]{ new Color(0.2, 0.4, 0.2), new Color(0.6, 0.8, 0.6) };

    public Color getVoxelColor(int block){
        return BLOCK_COLORS[block];
    }


    // INIT


    private final QuadMesh mesh;
    private final BaseShader shader;
    private final OrthographicCamera camera;


    public MyGreedyMesh3D(){
        camera = new OrthographicCamera();
        shader = BaseShader.getPos3Color();
        mesh = new QuadMesh(
                GRID_SIZE * GRID_SIZE * GRID_SIZE * 6,
                new GlVertexAttr(3, GlType.FLOAT), // position
                new GlVertexAttr(4, GlType.FLOAT)  // color
        );
        mesh.getBuffer().allocateData(GRID_SIZE * GRID_SIZE * GRID_SIZE * 6 * 4 * Float.SIZE);

        Gl.polygonMode(GlPolygonMode.LINE);

        greedy();
    }


    // QUADS


    private int offset = 0;

    private void resetMesh(){
        offset = 0;
        mesh.getBuffer().clearData(GlSizedFormat.RGBA8, GlType.FLOAT, new float[]{ 0, 0, 0,  0, 0, 0, 0 });
        Arrays.fill(masks, 0);
    }

    private void putQuad(int x1, int y1, int z1,
                         int x2, int y2, int z2,
                         int x3, int y3, int z3,
                         int x4, int y4, int z4,
                         Color color){

        Jpize.execSync(()->{
            final float[] quadVertices = {
                    x1, y1, z1,  color.r(), color.g(), color.b(), color.a(),
                    x2, y2, z2,  color.r(), color.g(), color.b(), color.a(),
                    x3, y3, z3,  color.r(), color.g(), color.b(), color.a(),
                    x4, y4, z4,  color.r(), color.g(), color.b(), color.a()
            };
            mesh.getBuffer().setSubData(offset, quadVertices);
            offset += (2 + 4) * 4 * GlType.FLOAT.getSize();
        });
    }

    private void putQuadNx(int x, int y, int width, int height, Color color){
        putQuad(
                0, 1, 1,
                0, 0, 1,
                0, 0, 0,
                0, 1, 0,
                color
        );
    }

    private void putQuadPx(int x, int y, int width, int height, Color color){
        putQuad(
                1, 1, 0,
                1, 0, 0,
                1, 0, 1,
                1, 1, 1,
                color
        );
    }

    private void putQuadNy(int x, int y, int width, int height, Color color){
        putQuad(
                1, 0, 1,
                1, 0, 0,
                0, 0, 0,
                0, 0, 1,
                color
        );
    }

    private void putQuadPy(int x, int y, int width, int height, Color color){
        putQuad(
                1, 1, 0,
                1, 1, 1,
                0, 1, 1,
                0, 1, 0,
                color
        );
    }

    private void putQuadNz(int x, int y, int width, int height, Color color){
        putQuad(
                0, 1, 0,
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                color
        );
    }

    private void putQuadPz(int x, int y, int width, int height, Color color){
        putQuad(
                1, 1, 1,
                1, 0, 1,
                0, 0, 1,
                0, 1, 1,
                color
        );
    }



    // ALGORITHM


    private void greedy(){
        resetMesh();

        // ...
    }
/*
    private void greedySlice(){
        mainFor: for(int j = 0; j < GRID_SIZE;){
            for(int i = 0; i < GRID_SIZE;){

                // Init tile
                final int tile = tiles[i][j];
                int width = 1;
                int height = 1;

                // Set mask for tile
                masks[i][j] = 1;

                // Merge X
                for(int x = i + 1; x < GRID_SIZE; x++){
                    // Try to merge line
                    final int neighborTile = tiles[x][j];
                    final int neighborMask = masks[x][j];
                    if(neighborTile != tile || neighborMask == 1)
                        break;

                    // Merge and set mask
                    width++;
                    masks[x][j] = 1;
                }

                // Merge Y
                mergeY: for(int y = j + 1; y < GRID_SIZE; y++){
                    // Try to merge quad
                    for(int x = i; x < i + width; x++){
                        final int neighborTile = tiles[x][y];
                        final int neighborMask = masks[x][y];
                        if(neighborTile != tile || neighborMask == 1)
                            break mergeY;
                    }

                    // Merge and set masks
                    height++;
                    for(int x = i; x < i + width; x++)
                        masks[x][y] = 1;
                }

                putQuad(i * quadSize, j * quadSize, width * quadSize, height * quadSize, BLOCK_COLORS[tile]);

                // Calc next X
                i += width - 1;
                // Calc next Y
                if(width == GRID_SIZE)
                    j += height - 1;
                else if(i == GRID_SIZE)
                    j++;
                if(j >= GRID_SIZE)
                    break;

                // Search correct X though 1 mask values
                while(masks[i][j] == 1){
                    i++;
                    if(i >= GRID_SIZE){
                        i = 0;
                        j++;
                    }
                    if(j == GRID_SIZE)
                        break mainFor;
                }
            }
        }

    }
*/

}
