package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.util.BaseShader;
import jpize.io.context.ContextBuilder;
import jpize.math.vecmath.vector.Vec3f;
import jpize.sdl.input.Key;
import test.devs.MeshInstance;
import test.devs.Scene3D;

/**
 This is a Java greedy meshing implementation based on the javascript implementation
 written by Mikola Lysenko and described in this blog post:
 <p>
 <a href="http://0fps.wordpress.com/2012/06/30/meshing-in-a-minecraft-game/">...</a>
 <p>
 The principal changes are:
 <p>
 - Porting to Java
 - Modification in order to compare *voxel faces*, rather than voxels themselves
 - Modification to provide for comparison based on multiple attributes simultaneously
 <p>
 This class is ready to be used on the JMonkey platform - but the algorithm should be
 usable in any case. (I approve, I used Jpize)

 @author Rob O'Leary */
public class GreedyMesh extends Scene3D{

    private static final int VOXEL_SIZE = 1;

    private static final int CHUNK_WIDTH = 3;
    private static final int CHUNK_HEIGHT = 3;

    private final int[][][] voxels = new int[CHUNK_WIDTH][CHUNK_HEIGHT][CHUNK_WIDTH];

    private static final int SOUTH = 0;
    private static final int NORTH = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int TOP = 4;
    private static final int BOTTOM = 5;

    static class VoxelFace{
        public boolean transparent;
        public int type;
        public int side;

        public boolean equals(final VoxelFace face){
            return face.transparent == this.transparent && face.type == this.type;
        }
    }

    public static void main(String[] args){
        ContextBuilder.newContext(1280, 720, "Greedy Mesh").register().setAdapter(new GreedyMesh());

        Gl.enable(GlTarget.DEPTH_TEST);
        Gl.polygonMode(GlPolygonMode.LINE);

        Jpize.runContexts();
    }

    @Override
    public void init(){
        for(int i = 0; i < CHUNK_WIDTH; i++){
            for(int j = 0; j < CHUNK_HEIGHT; j++){
                for(int k = 0; k < CHUNK_HEIGHT; k++){
                    if(i > CHUNK_WIDTH / 2 && j > CHUNK_HEIGHT / 2 && k > CHUNK_HEIGHT / 2)
                        voxels[i][j][k] = 1;
                    else if(i == 0)
                        voxels[i][j][k] = 2;
                    else
                        voxels[i][j][k] = 3;
                }
            }
        }

        greedy();
    }


    private void greedy(){

        /*
         * These are just working variables for the algorithm - almost all taken
         * directly from Mikola Lysenko's javascript implementation.
         */
        int i, j, k, l, w, h, u, v, n, side;

        final int[] x = new int[]{ 0, 0, 0 };
        final int[] q = new int[]{ 0, 0, 0 };
        final int[] du = new int[]{ 0, 0, 0 };
        final int[] dv = new int[]{ 0, 0, 0 };

        /*
         * We create a mask - this will contain the groups of matching voxel faces
         * as we proceed through the chunk in 6 directions - once for each face.
         */
        final VoxelFace[] mask = new VoxelFace[CHUNK_WIDTH * CHUNK_HEIGHT];

        /*
         * These are just working variables to hold two faces during comparison.
         */
        VoxelFace voxelFace, voxelFace1;

        /**
         * We start with the lesser-spotted boolean for-loop (also known as the old flippy floppy). 
         *
         * The variable backFace will be TRUE on the first iteration and FALSE on the second - this allows 
         * us to track which direction the indices should run during creation of the quad.
         *
         * This loop runs twice, and the inner loop 3 times - totally 6 iterations - one for each 
         * voxel face.
         */
        for(boolean backFace = true, b = false; b != backFace; backFace = false, b = !b){
            /*
             * We sweep over the 3 dimensions - most of what follows is well described by Mikola Lysenko
             * in his post - and is ported from his Javascript implementation.  Where this implementation
             * diverges, I've added commentary.
             */
            for(int d = 0; d < 3; d++){

                u = (d + 1) % 3;
                v = (d + 2) % 3;

                x[0] = 0;
                x[1] = 0;
                x[2] = 0;

                q[0] = 0;
                q[1] = 0;
                q[2] = 0;
                q[d] = 1;

                /*
                 * Here we're keeping track of the side that we're meshing.
                 */
                if(d == 0)
                    side = backFace ? WEST : EAST;
                else if(d == 1)
                    side = backFace ? BOTTOM : TOP;
                else
                    side = backFace ? SOUTH : NORTH;

                /*
                 * We move through the dimension from front to back
                 */
                for(x[d] = -1; x[d] < CHUNK_WIDTH; ){

                    /*
                     * -------------------------------------------------------------------
                     *   We compute the mask
                     * -------------------------------------------------------------------
                     */
                    n = 0;

                    for(x[v] = 0; x[v] < CHUNK_HEIGHT; x[v]++){

                        for(x[u] = 0; x[u] < CHUNK_WIDTH; x[u]++){

                            /*
                             * Here we retrieve two voxel faces for comparison.
                             */
                            voxelFace = (x[d] >= 0) ? getVoxelFace(x[0], x[1], x[2], side) : null;
                            voxelFace1 = (x[d] < CHUNK_WIDTH - 1) ? getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side) : null;

                            /*
                             * Note that we're using the equals function in the voxel face class here, which lets the faces
                             * be compared based on any number of attributes.
                             *
                             * Also, we choose the face to add to the mask depending on whether we're moving through on a backface or not.
                             */
                            mask[n++] = (voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1)) ? null : backFace ? voxelFace1 : voxelFace;
                        }
                    }

                    x[d]++;

                    /*
                     * Now we generate the mesh for the mask
                     */
                    n = 0;

                    for(j = 0; j < CHUNK_HEIGHT; j++){

                        for(i = 0; i < CHUNK_WIDTH; ){

                            if(mask[n] != null){

                                /*
                                 * We compute the width
                                 */
                                for(w = 1;
                                    i + w < CHUNK_WIDTH && mask[n + w] != null && mask[n + w].equals(mask[n]);
                                    w++);

                                /*
                                 * Then we compute height
                                 */
                                heightFor: for(h = 1; j + h < CHUNK_HEIGHT; h++){
                                    for(k = 0; k < w; k++){

                                        final VoxelFace maskFace = mask[h * CHUNK_WIDTH + n + k];
                                        if(maskFace == null || !maskFace.equals(mask[n]))
                                            break heightFor;
                                    }
                                }

                                /*
                                 * Here we check the "transparent" attribute in the VoxelFace class to ensure that we don't mesh
                                 * any culled faces.
                                 */
                                if(!mask[n].transparent){
                                    /*
                                     * Add quad
                                     */
                                    x[u] = i;
                                    x[v] = j;

                                    du[0] = 0;
                                    du[1] = 0;
                                    du[2] = 0;
                                    du[u] = w;

                                    dv[0] = 0;
                                    dv[1] = 0;
                                    dv[2] = 0;
                                    dv[v] = h;

                                    /*
                                     * And here we call the quad function in order to render a merged quad in the scene.
                                     *
                                     * We pass mask[n] to the function, which is an instance of the VoxelFace class containing
                                     * all the attributes of the face - which allows for variables to be passed to shaders - for
                                     * example lighting values used to create ambient occlusion.
                                     */
                                    quad(new Vec3f(x[0], x[1], x[2]), new Vec3f(x[0] + du[0], x[1] + du[1], x[2] + du[2]), new Vec3f(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]), new Vec3f(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]), w, h, mask[n], backFace);
                                }

                                /*
                                 * We zero out the mask
                                 */
                                for(l = 0; l < h; ++l){

                                    for(k = 0; k < w; ++k){
                                        mask[n + k + l * CHUNK_WIDTH] = null;
                                    }
                                }

                                /*
                                 * And then finally increment the counters and continue
                                 */
                                i += w;
                                n += w;

                            }else{

                                i++;
                                n++;
                            }
                        }
                    }
                }
            }
        }
    }

    VoxelFace getVoxelFace(final int x, final int y, final int z, final int side){
        final VoxelFace voxelFace = new VoxelFace();
        voxelFace.type = voxels[x][y][z];
        voxelFace.side = side;
        return voxelFace;
    }

    void quad(final Vec3f bottomLeft, final Vec3f topLeft, final Vec3f topRight, final Vec3f bottomRight, final int width, final int height, final VoxelFace voxel, final boolean backFace){

        final Vec3f[] positions = new Vec3f[4];

        positions[2] = topLeft.mul(VOXEL_SIZE);
        positions[3] = topRight.mul(VOXEL_SIZE);
        positions[0] = bottomLeft.mul(VOXEL_SIZE);
        positions[1] = bottomRight.mul(VOXEL_SIZE);

        final int[] indexes = backFace ?
                new int[]{2, 3, 1, 1, 0, 2} :
                new int[]{2, 0, 1, 1, 3, 2};

        final float[] colorArray = new float[4 * 4];

        for(int i = 0; i < colorArray.length; i += 4){
            if(voxel.type == 1){

                colorArray[i] = 1.0f;
                colorArray[i + 1] = 0.0f;
                colorArray[i + 2] = 0.0f;
                colorArray[i + 3] = 1.0f;

            }else if(voxel.type == 2){

                colorArray[i] = 0.0f;
                colorArray[i + 1] = 1.0f;
                colorArray[i + 2] = 0.0f;
                colorArray[i + 3] = 1.0f;

            }else{

                colorArray[i] = 0.0f;
                colorArray[i + 1] = 0.0f;
                colorArray[i + 2] = 1.0f;
                colorArray[i + 3] = 1.0f;
            }
        }

        float[] vertices = new float[(3 + 4) * 4];
        for(int i = 0; i < positions.length; i++){
            int vertexOffset = i * (3 + 4);

            int c = i * 4;

            vertices[vertexOffset    ] = positions[i].x;
            vertices[vertexOffset + 1] = positions[i].y;
            vertices[vertexOffset + 2] = positions[i].z;

            vertices[vertexOffset + 3] = colorArray[c    ];
            vertices[vertexOffset + 4] = colorArray[c + 1];
            vertices[vertexOffset + 5] = colorArray[c + 2];
            vertices[vertexOffset + 6] = colorArray[c + 3];
        }

        IndexedMesh mesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT), new GlVertexAttr(4, GlType.FLOAT));
        mesh.getBuffer().setData(vertices);
        mesh.getIndexBuffer().setData(indexes);

        MeshInstance instance = new MeshInstance(mesh, BaseShader.getPos3Color());
        super.putMesh(instance);
    }

    @Override
    public void render(){
        super.render();

        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }

}