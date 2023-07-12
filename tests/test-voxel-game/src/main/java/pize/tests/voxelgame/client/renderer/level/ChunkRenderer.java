package pize.tests.voxelgame.client.renderer.level;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.graphics.util.color.Color;
import pize.math.vecmath.matrix.Matrix4f;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkCustomMesh;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshStack;
import pize.tests.voxelgame.client.chunk.mesh.ChunkSolidMesh;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;

import java.util.Map;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.SIZE;

public class ChunkRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader solidShader, customShader;
    private final Texture atlasTexture;
    private final Matrix4f translateMatrix;
    private int renderedChunks;
    
    public ChunkRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        Gl.depthFunc(DepthFunc.LEQUAL);
        atlasTexture = new Texture("texture/block/voxelgame.png");
        
        solidShader =  new Shader(new Resource("shader/level/chunk/solid-blocks.vert"), new Resource("shader/level/chunk/solid-blocks.frag"));
        customShader = new Shader(new Resource("shader/level/chunk/custom-blocks.vert"), new Resource("shader/level/chunk/custom-blocks.frag"));
        translateMatrix = new Matrix4f();
        
        Gl.polygonOffset(1, 1); // For BlockSelector line rendering
    }
    
    
    public void render(GameCamera camera){
        setupShaders(camera);
        Gl.enable(Target.POLYGON_OFFSET_FILL);
        renderMeshes(camera);
        Gl.disable(Target.POLYGON_OFFSET_FILL);
    }
    
    
    private void renderMeshes(GameCamera camera){
        renderedChunks = 0;
        
        final ClientLevel level = levelRenderer.getGameRenderer().getSession().getGame().getLevel();
        if(level == null)
            return;
        
        final Map<ClientChunk, ChunkMeshStack> meshes = level.getChunkManager().getMeshes();
        for(ClientChunk chunk: meshes.keySet()){
            
            // Frustum culling
            if(!camera.isChunkSeen(chunk))
                continue;
            
            renderedChunks++;
            
            // Get meshes
            final ChunkMeshStack mesh = meshes.get(chunk);
            if(mesh == null)
                continue;
            
            // Set translation matrix
            final ChunkPos chunkPos = chunk.getPosition();
            translateMatrix.toTranslated(
                chunkPos.globalX() - camera.getX(),
                0,
                chunkPos.globalZ() - camera.getZ()
            );
            
            // Render solid blocks
            final ChunkSolidMesh solidMesh = mesh.getSolid();
            if(solidMesh != null){
                solidShader.bind();
                solidShader.setUniform("u_model", translateMatrix);
                solidMesh.render();
            }
            
            // Render custom blocks
            final ChunkCustomMesh customMesh = mesh.getCustom();
            if(customMesh != null){
                Gl.disable(Target.CULL_FACE);
                customShader.bind();
                customShader.setUniform("u_model", translateMatrix);
                customMesh.render();
                Gl.enable(Target.CULL_FACE);
            }
        }
    }
    
    private void setupShaders(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        final Color fogColor = levelRenderer.getSkyRenderer().getFogColor();
        
        // Solid
        solidShader.bind();
        solidShader.setUniform("u_projection", camera.getProjection());
        solidShader.setUniform("u_view", camera.getView());
        solidShader.setUniform("u_atlas", atlasTexture);
        
        solidShader.setUniform("u_renderDistanceBlocks", options.getRenderDistance() * (SIZE));
        solidShader.setUniform("u_fogColor", fogColor);
        solidShader.setUniform("u_brightness", options.getBrightness());
        
        // Custom
        customShader.bind();
        customShader.setUniform("u_projection", camera.getProjection());
        customShader.setUniform("u_view", camera.getView());
        customShader.setUniform("u_atlas", atlasTexture);
        
        customShader.setUniform("u_renderDistanceBlocks", options.getRenderDistance() * (SIZE));
        customShader.setUniform("u_fogColor", fogColor);
        customShader.setUniform("u_brightness", options.getBrightness());
    }
    
    public int getRenderedChunks(){
        return renderedChunks;
    }
    
    @Override
    public void dispose(){
        atlasTexture.dispose();
        solidShader.dispose();
        customShader.dispose();
    }
    
}
