package megalul.projectvostok.world.render;

import glit.Pize;
import glit.context.Disposable;
import glit.files.FileHandle;
import glit.graphics.texture.Texture;
import glit.graphics.util.Shader;
import glit.io.glfw.Key;
import glit.math.vecmath.matrix.Matrix4f;
import megalul.projectvostok.Main;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.data.ChunkPos;
import megalul.projectvostok.chunk.mesh.ChunkMesh;

import java.util.Map;

public class WorldRenderer implements Disposable{

    private final Main session;
    private final Shader chunkShader;
    private final Texture atlasTexture;
    private final Matrix4f chunkMatrix;


    public WorldRenderer(Main session){
        this.session = session;
        
        chunkShader = new Shader(new FileHandle("shader/chunk.vert"), new FileHandle("shader/chunk.frag"));
        chunkMatrix = new Matrix4f();
        
        atlasTexture = new Texture("texture/stone.png");
    }

    public void render(){
        if(Pize.isDown(Key.NUM_1))
            atlasTexture.regenerateMipmapLevels(1);
        if(Pize.isDown(Key.NUM_4))
            atlasTexture.regenerateMipmapLevels(4);
        if(Pize.isDown(Key.NUM_8))
            atlasTexture.regenerateMipmapLevels(8);
        
        chunkShader.bind();
        chunkShader.setUniform("u_projection", session.getCamera().getProjection());
        chunkShader.setUniform("u_view", session.getCamera().getView());
        chunkShader.setUniform("u_atlas", atlasTexture);

        final Map<Chunk, ChunkMesh> meshes = session.getWorld().getChunks().getMeshes();
    
        for(Chunk chunk: meshes.keySet()){
            if(!session.getCamera().isChunkSeen(chunk))
                continue;
            
            ChunkMesh mesh = meshes.get(chunk);
            if(mesh == null)
                continue;
            
            ChunkPos chunkPos = chunk.getPos();
            chunkShader.setUniform("u_model", chunkMatrix.toTranslated(chunkPos.globalX(), 0, chunkPos.globalZ()));
            mesh.render();
        }
    }

    @Override
    public void dispose(){
        atlasTexture.dispose();
        chunkShader.dispose();
    }

}
