package pize.tests.minecraftosp.client.renderer.level;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
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
    private final Shader packedChunkShader, customShader;
    private int renderedChunks;
    
    public ChunkRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        Gl.depthFunc(DepthFunc.LEQUAL);

        packedChunkShader =  new Shader(new Resource("shader/level/chunk/solid-blocks-packed.vert"), new Resource("shader/level/chunk/solid-blocks-packed.frag"));
        customShader = new Shader(new Resource("shader/level/chunk/custom-blocks.vert"), new Resource("shader/level/chunk/custom-blocks.frag"));
        
        Gl.polygonOffset(1, 1); // For BlockSelector line rendering
    }
    
    
    public void render(GameCamera camera){
        setupShaders(camera);
        Gl.enable(Target.POLYGON_OFFSET_FILL);
        renderMeshes(camera);
        Gl.disable(Target.POLYGON_OFFSET_FILL);
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
        Gl.disable(Target.CULL_FACE);
        customShader.bind();
        for(ClientChunk chunk: chunks){
            customShader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getCustom().render();
        }
        Gl.enable(Target.CULL_FACE);

        // Render solid blocks
        packedChunkShader.bind();
        for(ClientChunk chunk: chunks){
            packedChunkShader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getPacked().render();
        }

        // Render translucent blocks
        Gl.disable(Target.CULL_FACE);
        for(ClientChunk chunk: chunks){
            packedChunkShader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getTranslucent().render();
        }
        Gl.enable(Target.CULL_FACE);
    }
    
    private void setupShaders(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        final Color fogColor = levelRenderer.getSkyRenderer().getFogColor();
        final float fogStart = levelRenderer.getSkyRenderer().getFogStart();
        final Texture blockAtlas = levelRenderer.getGameRenderer().getSession().getResources().getBlocks();
        final GameTime gameTime = levelRenderer.getGameRenderer().getSession().getGame().getTime();
        
        // Solid
        packedChunkShader.bind();
        packedChunkShader.setUniform("u_projection", camera.getProjection());
        packedChunkShader.setUniform("u_view", camera.getView());
        packedChunkShader.setUniform("u_atlas", blockAtlas);
        
        packedChunkShader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        packedChunkShader.setUniform("u_fogColor", fogColor);
        packedChunkShader.setUniform("u_fogStart", fogStart);
        packedChunkShader.setUniform("u_brightness", options.getBrightness());

        packedChunkShader.setUniform("u_gameTime", gameTime.getTicks());

        // Custom
        customShader.bind();
        customShader.setUniform("u_projection", camera.getProjection());
        customShader.setUniform("u_view", camera.getView());
        customShader.setUniform("u_atlas", blockAtlas);
        
        customShader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        customShader.setUniform("u_fogColor", fogColor);
        customShader.setUniform("u_fogStart", fogStart);
        customShader.setUniform("u_brightness", options.getBrightness());
    }
    
    public int getRenderedChunks(){
        return renderedChunks;
    }
    
    @Override
    public void dispose(){
        packedChunkShader.dispose();
        customShader.dispose();
    }
    
}
