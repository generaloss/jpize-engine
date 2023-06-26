package pize.tests.voxelgame.client.level.render;

import pize.Pize;
import pize.app.Disposable;
import pize.app.Resizable;
import pize.files.Resource;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.graphics.texture.Texture;
import pize.graphics.util.*;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMesh;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.PerspectiveType;
import pize.tests.voxelgame.client.entity.RemotePlayer;
import pize.tests.voxelgame.client.entity.model.PlayerModel;
import pize.tests.voxelgame.base.chunk.storage.ChunkPos;
import pize.tests.voxelgame.base.entity.Entity;

import java.util.Map;

import static pize.tests.voxelgame.base.chunk.ChunkUtils.SIZE;

public class LevelRenderer implements Disposable, Resizable{

    private final VoxelGame session;
    
    private final TextureBatch batch;
    private final SkyBox skyBox;
    private final Texture atlasTexture, cursorTexture, vignetteTexture;
    private final Shader chunkShader, postShader;
    protected final Shader lineShader;
    private final Matrix4f chunkMatrix, skyViewMatrix;
    private final BlockSelector blockSelector;
    private final ChunkBorder chunkBorder;
    private boolean showChunkBorder;
    private final Color fogColor;
    
    private final Framebuffer3D framebuffer;
    private final Framebuffer2D batchFramebuffer;
    
    public LevelRenderer(VoxelGame session){
        this.session = session;
        // Batch for Cursor
        batch = new TextureBatch();
        batchFramebuffer = new Framebuffer2D();
        
        // Sky
        skyBox = new SkyBox("texture/skybox/skybox_positive_x.png", "texture/skybox/skybox_negative_x.png", "texture/skybox/skybox_positive_y.png", "texture/skybox/skybox_negative_y.png", "texture/skybox/skybox_positive_z.png", "texture/skybox/skybox_negative_z.png");
        skyViewMatrix = new Matrix4f();
        
        // Chunk
        Gl.depthFunc(DepthFunc.LEQUAL);
        atlasTexture = new Texture("texture/block/minecraft.png");
        chunkShader = new Shader(new Resource("shader/chunk.vert"), new Resource("shader/chunk.frag"));
        chunkMatrix = new Matrix4f();
        
        // Block Selector
        lineShader = new Shader(new Resource("shader/line.vert"), new Resource("shader/line.frag"));
        
        blockSelector = new BlockSelector(this);
        Gl.lineWidth(2);
        Gl.polygonOffset(1, 1);
        
        // Chunk Border (F3 + G)
        chunkBorder = new ChunkBorder(this);
        
        // Cursor & Vignette
        cursorTexture = new Texture("texture/cursor.png");
        vignetteTexture = new Texture("texture/vignette.png");
        
        // Post
        postShader = new Shader(new Resource("shader/post.vert"), new Resource("shader/post.frag"));
        framebuffer = new Framebuffer3D();
        
        fogColor = new Color(0, 0.01, 0.05, 1);
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    public void render(){
        final GameCamera camera = session.getGame().getCamera();
        if(camera == null)
            return;
        
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
            chunkShader.setUniform("u_fogColor", fogColor);
            chunkShader.setUniform("u_renderDistanceBlocks", session.getOptions().getRenderDistance() * SIZE);
            
            chunkShader.setUniform("u_projection", camera.getProjection());
            chunkShader.setUniform("u_view", camera.getView());
            chunkShader.setUniform("u_atlas", atlasTexture);
            chunkShader.setUniform("u_brightness", session.getOptions().getBrightness());
            renderAllChunkMeshes();
            Gl.disable(Target.POLYGON_OFFSET_FILL);
            
            // Player
            final PlayerModel playerModel = session.getGame().getPlayer().getModel();
            if(playerModel != null){
                playerModel.animate();
                if(camera.getPerspective() != PerspectiveType.FIRST_PERSON)
                    playerModel.render(camera);
            }

            // Entities
            for(Entity entity : session.getGame().getLevel().getEntities()){
                if(entity.getClass() == RemotePlayer.class){
                    final RemotePlayer remotePlayer = (RemotePlayer) entity;
                    remotePlayer.tick();
                    final PlayerModel model = remotePlayer.getModel();
                    if(model != null){
                        model.animate();
                        model.render(camera);
                    }
                }

            }
            
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
        
        // Render Vignette
        batch.begin();
        
        float vignette = 0;
        final Vec3f playerPosition = session.getGame().getPlayer().getPosition();
        final float light = session.getGame().getLevel().getLight(playerPosition.xf(), playerPosition.yf(), playerPosition.zf());
        vignette = 1 - light / 15F;
        
        batch.setAlpha(vignette);
        batch.draw(vignetteTexture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.resetColor();
        batch.end();
    }
    
    private void renderAllChunkMeshes(){
        if(session.getGame().getLevel() == null)
            return;
        
        final GameCamera camera = session.getGame().getCamera();
        final Map<ClientChunk, ChunkMesh> meshes = session.getGame().getLevel().getChunkManager().getMeshes();
        
        for(ClientChunk chunk: meshes.keySet()){
            if(!camera.isChunkSeen(chunk))
                continue;
        
            final ChunkMesh mesh = meshes.get(chunk);
            if(mesh == null)
                continue;
        
            ChunkPos chunkPos = chunk.getPosition();
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
    
    public BlockSelector getBlockSelector(){
        return blockSelector;
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
        vignetteTexture.dispose();
        chunkShader.dispose();
        skyBox.dispose();
        blockSelector.dispose();
        framebuffer.dispose();
        batchFramebuffer.dispose();
    }

}
