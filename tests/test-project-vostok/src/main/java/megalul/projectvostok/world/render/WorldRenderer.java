package megalul.projectvostok.world.render;

import megalul.projectvostok.Main;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.mesh.ChunkMesh;
import megalul.projectvostok.chunk.storage.ChunkPos;
import megalul.projectvostok.control.GameCamera;
import pize.Pize;
import pize.context.Disposable;
import pize.context.Resizable;
import pize.files.FileHandle;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.graphics.texture.Texture;
import pize.graphics.util.*;
import pize.graphics.util.batch.TextureBatch;
import pize.math.vecmath.matrix.Matrix4f;

import java.util.Map;

public class WorldRenderer implements Disposable, Resizable{

    private final Main sessionOF;
    
    private final TextureBatch batch;
    private final SkyBox skyBox;
    private final Texture atlasTexture, cursorTexture;
    private final Shader chunkShader, postShader;
    protected final Shader lineShader;
    private final Matrix4f chunkMatrix, skyViewMatrix;
    private final BlockSelector blockSelector;
    private final ChunkBorder chunkBorder;
    private boolean showChunkBorder;
    
    private final Framebuffer3D framebuffer;
    private final Framebuffer2D batchFramebuffer;
    
    public WorldRenderer(Main sessionOF){
        this.sessionOF = sessionOF;
        // Batch for Cursor
        batch = new TextureBatch();
        batchFramebuffer = new Framebuffer2D();
        
        // Sky
        skyBox = new SkyBox(
            "texture/skybox_positive_x.png", "texture/skybox_negative_x.png",
            "texture/skybox_positive_y.png", "texture/skybox_negative_y.png",
            "texture/skybox_positive_z.png", "texture/skybox_negative_z.png"
        );
        skyViewMatrix = new Matrix4f();
        
        // Chunk
        atlasTexture = new Texture("texture/minecraft.png");
        chunkShader = new Shader(new FileHandle("shader/chunk.vert"), new FileHandle("shader/chunk.frag"));
        chunkMatrix = new Matrix4f();
        
        // Block Selector
        lineShader = new Shader(new FileHandle("shader/line.vert"), new FileHandle("shader/line.frag"));
        
        blockSelector = new BlockSelector(this);
        Gl.lineWidth(2);
        Gl.polygonOffset(1, 1);
        
        // Chunk Border (F3 + G)
        chunkBorder = new ChunkBorder(this);
        
        // Cursor
        cursorTexture = new Texture("texture/cursor.png");
        
        // Post
        postShader = new Shader(new FileHandle("shader/post.vert"), new FileHandle("shader/post.frag"));
        framebuffer = new Framebuffer3D();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void render(){
        final GameCamera camera = sessionOF.getCamera();
        
        framebuffer.begin();
        {
            // Sky
            skyViewMatrix.set(camera.getView());
            skyViewMatrix.cullPosition();
            skyBox.render(camera.getProjection(), skyViewMatrix);
            
            // World
            Gl.enable(Target.DEPTH_TEST);
            Gl.enable(Target.POLYGON_OFFSET_FILL);
            chunkShader.bind();
            chunkShader.setUniform("u_projection", camera.getProjection());
            chunkShader.setUniform("u_view", camera.getView());
            chunkShader.setUniform("u_atlas", atlasTexture);
            renderAllChunkMeshes();
            Gl.disable(Target.POLYGON_OFFSET_FILL);
            
            // Block Selector and Chunk Border
            lineShader.bind();
            lineShader.setUniform("u_projection", camera.getProjection());
            lineShader.setUniform("u_view", camera.getView());
            
            blockSelector.render(); // block selector
            
            if(showChunkBorder)
                chunkBorder.render(); // chunk border
            
            Gl.disable(Target.DEPTH_TEST);
        }
        framebuffer.end();
        
        // Render Cursor
        batchFramebuffer.begin();
        {
            batch.begin();
            final float cursorSize = Pize.getHeight() / 64F;
            batch.draw(cursorTexture, Pize.getWidth() / 2F - cursorSize / 2, Pize.getHeight() / 2F - cursorSize / 2, cursorSize, cursorSize);
            batch.end();
        }
        batchFramebuffer.end();
        
        // Render inv-color Cursor
        postShader.bind();
        postShader.setUniform("u_frame", framebuffer.getTexture());
        postShader.setUniform("u_batch", batchFramebuffer.getTexture());
        ScreenQuad.render();
    }
    
    private void renderAllChunkMeshes(){
        final GameCamera camera = sessionOF.getCamera();
        final Map<Chunk, ChunkMesh> meshes = sessionOF.getWorld().getProvider().getMeshes();
        
        for(Chunk chunk: meshes.keySet()){
            if(!camera.isChunkSeen(chunk))
                continue;
        
            final ChunkMesh mesh = meshes.get(chunk);
            if(mesh == null)
                continue;
        
            ChunkPos chunkPos = chunk.getPos();
            chunkShader.setUniform("u_model", chunkMatrix.toTranslated(
                chunkPos.globalX() - camera.getX(),
                0,
                chunkPos.globalZ() - camera.getZ()
            ));
            mesh.render();
        }
    }
    
    
    public boolean isShowChunkBorder(){
        return showChunkBorder;
    }
    
    public void showChunkBorder(boolean show){
        showChunkBorder = show;
    }
    
    public void toggleShowChunkBorder(){
        showChunkBorder(!showChunkBorder);
    }
    
    
    @Override
    public void resize(int width, int height){
        framebuffer.resize(width, height);
        batchFramebuffer.resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        atlasTexture.dispose();
        cursorTexture.dispose();
        chunkShader.dispose();
        skyBox.dispose();
        blockSelector.dispose();
        framebuffer.dispose();
        batchFramebuffer.dispose();
    }

}
