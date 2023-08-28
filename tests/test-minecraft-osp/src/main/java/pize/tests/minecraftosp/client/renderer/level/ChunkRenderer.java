package pize.tests.minecraftosp.client.renderer.level;

import pize.util.Disposable;
import pize.files.Resource;
import pize.gl.glenum.GlDepthFunc;
import pize.gl.Gl;
import pize.gl.glenum.GlTarget;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.graphics.util.color.Color;
import pize.tests.minecraftosp.client.chunk.ClientChunk;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.level.ClientLevel;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.main.time.GameTime;

import java.util.Collection;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.SIZE;

public class ChunkRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private int renderedChunks;
    
    public ChunkRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        Gl.depthFunc(GlDepthFunc.LEQUAL);

        shader = new Shader(new Resource("shader/level/chunk/custom-blocks.vert"), new Resource("shader/level/chunk/custom-blocks.frag"));
        
        Gl.polygonOffset(1, 1); // For BlockSelector line rendering
    }
    
    
    public void render(GameCamera camera){
        setupShaders(camera);
        Gl.enable(GlTarget.POLYGON_OFFSET_FILL);
        renderMeshes(camera);
        Gl.disable(GlTarget.POLYGON_OFFSET_FILL);
    }
    
    
    private void renderMeshes(GameCamera camera){
        // Level
        final ClientLevel level = levelRenderer.getGameRenderer().getSession().getGame().getLevel();
        if(level == null)
            return;

        // Chunks
        final Collection<ClientChunk> chunks =
            level.getChunkManager().getAllChunks() // Get all chunks
            .stream().filter(camera::isChunkSeen).toList(); // Frustum culling

        // Rendered chunks for the info panel
        renderedChunks = chunks.size();

        // Atlas
        final Texture blockAtlas = levelRenderer.getGameRenderer().getSession().getResources().getBlocks();

        // Update translation matrix
        for(ClientChunk chunk: chunks)
            chunk.updateTranslationMatrix(camera);

        // Render custom blocks
        Gl.disable(GlTarget.CULL_FACE);
        shader.bind();
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getCustom().render();
        }
        Gl.enable(GlTarget.CULL_FACE);

        // Render solid blocks
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getSolid().render();
        }

        // Render translucent blocks
        Gl.disable(GlTarget.CULL_FACE);
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getTranslucent().render();
        }
        Gl.enable(GlTarget.CULL_FACE);
    }
    
    private void setupShaders(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        final Color fogColor = levelRenderer.getSkyRenderer().getFogColor();
        final float fogStart = levelRenderer.getSkyRenderer().getFogStart();
        final float skyBrightness = levelRenderer.getSkyRenderer().getSkyBrightness();
        final Texture blockAtlas = levelRenderer.getGameRenderer().getSession().getResources().getBlocks();
        final GameTime gameTime = levelRenderer.getGameRenderer().getSession().getGame().getTime();

        // Shader
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        shader.setUniform("u_atlas", blockAtlas);
        
        shader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        shader.setUniform("u_fogColor", fogColor);
        shader.setUniform("u_fogStart", fogStart);
        shader.setUniform("u_brightness", options.getBrightness());
        shader.setUniform("u_skyBrightness", skyBrightness);
    }
    
    public int getRenderedChunks(){
        return renderedChunks;
    }
    
    @Override
    public void dispose(){
        shader.dispose();
    }
    
}
