package jpize.tests.mcose.client.renderer.level;

import jpize.files.Resource;
import jpize.gl.Gl;
import jpize.gl.texture.GlWrap;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.mesh.QuadMesh;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.Shader;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.client.control.camera.GameCamera;
import jpize.tests.mcose.client.entity.LocalPlayer;
import jpize.tests.mcose.client.options.Options;
import jpize.tests.mcose.client.time.ClientGameTime;
import jpize.tests.mcose.main.chunk.ChunkUtils;
import jpize.util.Disposable;

import static jpize.tests.mcose.main.chunk.ChunkUtils.SIZE;

public class CloudsRenderer implements Disposable{

    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final QuadMesh mesh;
    private final QuadMesh mesh2;
    private final Texture cloudsTexture;
    private final int renderDistance;

    public CloudsRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        cloudsTexture = new Texture("texture/environment/clouds.png");
        cloudsTexture.getParameters().setWrap(GlWrap.REPEAT);
        cloudsTexture.update();

        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        renderDistance = options.getRenderDistance() + 1;

        this.shader = new Shader(
                new Resource("shader/level/sky/clouds.vert"),
                new Resource("shader/level/sky/clouds.frag")
        );

        final int size = (renderDistance * 2 + 1) * ChunkUtils.SIZE;
        final int start = -size / 2;
        float maxUv = renderDistance * 2F / cloudsTexture.getWidth();

        this.mesh = new QuadMesh(1, new GlVertexAttr(3, GlType.FLOAT), new GlVertexAttr(2, GlType.FLOAT));
        this.mesh.getBuffer().setData(new float[]{
                start       , 194, start + size,  0    , 0    ,
                start       , 194, start       ,  0    , maxUv,
                start + size, 194, start       ,  maxUv, maxUv,
                start + size, 194, start + size,  maxUv, 0    ,
        });

        this.mesh2 = new QuadMesh(1, new GlVertexAttr(3, GlType.FLOAT), new GlVertexAttr(2, GlType.FLOAT));
        this.mesh2.getBuffer().setData(new float[]{
                start       , 312, start + size,  0        , 0        ,
                start       , 312, start       ,  0        , maxUv * 2,
                start + size, 312, start       ,  maxUv * 2, maxUv * 2,
                start + size, 312, start + size,  maxUv * 2, 0        ,
        });
    }

    public void render(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        final SkyRenderer skyRenderer = levelRenderer.getSkyRenderer();
        final Vec2f position = getCloudsPosition(0.1);

        Gl.disableCullFace();
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        shader.setUniform("u_clouds", cloudsTexture);
        shader.setUniform("u_position", position);

        shader.setUniform("u_renderDistanceBlocks", (renderDistance - 1) * SIZE);
        shader.setUniform("u_fogEnabled", options.isFogEnabled());
        shader.setUniform("u_fogColor", skyRenderer.getFogColor());
        shader.setUniform("u_fogStart", skyRenderer.getFogStart());
        shader.setUniform("u_skyBrightness", skyRenderer.getSkyBrightness());

        mesh.render();

        position.set(getCloudsPosition(1));
        shader.setUniform("u_position", position);
        mesh2.render();

        Gl.enableCullFace();
    }

    private Vec2f getCloudsPosition(double speed){
        final Minecraft session = levelRenderer.getGameRenderer().getSession();
        final ClientGameTime gameTime = session.getGame().getTime();
        final float time = gameTime.getLerpSeconds();

        final LocalPlayer player = session.getGame().getPlayer();
        final Vec3f playerPos = player.getLerpPosition();

        final float pixelUv = 1F / cloudsTexture.getWidth();
        return new Vec2f(
                ( playerPos.x / ChunkUtils.SIZE + time * speed) * pixelUv,
                (-playerPos.z / ChunkUtils.SIZE               ) * pixelUv
        );
    }

    @Override
    public void dispose(){
        mesh.dispose();
    }

}
