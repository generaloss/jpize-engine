package jpize.tests.minecraftosp.client.renderer.level;

import jpize.files.Resource;
import jpize.gl.Gl;
import jpize.gl.glenum.GlDepthFunc;
import jpize.gl.glenum.GlTarget;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.Shader;
import jpize.graphics.util.color.Color;
import jpize.tests.minecraftosp.client.chunk.ClientChunk;
import jpize.tests.minecraftosp.client.control.camera.GameCamera;
import jpize.tests.minecraftosp.client.level.ClientLevel;
import jpize.tests.minecraftosp.client.options.Options;
import jpize.tests.minecraftosp.main.time.GameTime;
import jpize.util.Disposable;

import java.util.Collection;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.SIZE;

public class ChunkRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader, packedShader;
    private int renderedChunks;
    
    public ChunkRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        Gl.depthFunc(GlDepthFunc.LEQUAL);

        this.shader = new Shader(
                new Resource("shader/level/chunk/custom-blocks.vert"),
                new Resource("shader/level/chunk/custom-blocks.frag")
        );
        this.packedShader = new Shader(
                new Resource("shader/level/chunk/packed-voxel.vert"),
                new Resource("shader/level/chunk/packed-voxel.frag")
        );

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

            if(chunk.getMeshStack().getCustom().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getCustom().renderPacked();
                shader.bind();
            }
        }
        Gl.enable(GlTarget.CULL_FACE);

        // Render solid blocks
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getSolid().render();

            if(chunk.getMeshStack().getSolid().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getSolid().renderPacked();
                shader.bind();
            }
        }

        // Render translucent blocks
        //Gl.disable(GlTarget.CULL_FACE);
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getTranslucent().render();

            if(chunk.getMeshStack().getTranslucent().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getTranslucent().renderPacked();
                shader.bind();
            }
        }
        //Gl.enable(GlTarget.CULL_FACE);
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
        shader.setUniform("u_fogEnabled", options.isFogEnabled());
        shader.setUniform("u_fogColor", fogColor);
        shader.setUniform("u_fogStart", fogStart);
        shader.setUniform("u_brightness", options.getBrightness());
        shader.setUniform("u_skyBrightness", skyBrightness);

        // Packed Shader
        packedShader.bind();
        packedShader.setUniform("u_projection", camera.getProjection());
        packedShader.setUniform("u_view", camera.getView());
        packedShader.setUniform("u_atlas", blockAtlas);

        packedShader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        packedShader.setUniform("u_fogEnabled", options.isFogEnabled());
        packedShader.setUniform("u_fogColor", fogColor);
        packedShader.setUniform("u_fogStart", fogStart);
        packedShader.setUniform("u_brightness", options.getBrightness());
        packedShader.setUniform("u_skyBrightness", skyBrightness);
    }
    
    public int getRenderedChunks(){
        return renderedChunks;
    }
    
    @Override
    public void dispose(){
        shader.dispose();
        packedShader.dispose();
    }
    
}
