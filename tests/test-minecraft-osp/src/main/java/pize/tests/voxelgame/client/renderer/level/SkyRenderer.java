package pize.tests.voxelgame.client.renderer.level;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.gl.*;
import pize.graphics.util.BufferBuilder;
import pize.graphics.util.Shader;
import pize.graphics.util.SkyBox;
import pize.graphics.util.color.Color;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexAttr;
import pize.graphics.vertex.VertexBuffer;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.main.time.GameTime;

public class SkyRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    
    private final SkyBox skyBox;
    private final Matrix4f skyViewMatrix;
    
    private final Shader sunriseShader, skydiscShader;
    private final VertexBuffer sunriseVbo, lightSkyVbo, darkSkyVbo;
    private final VertexArray sunriseVao, lightSkyVao, darkSkyVao;
    
    public SkyRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.skyBox = new SkyBox(
            "texture/skybox/1/skybox_positive_x.png",
            "texture/skybox/1/skybox_negative_x.png",
            "texture/skybox/1/skybox_positive_y.png",
            "texture/skybox/1/skybox_negative_y.png",
            "texture/skybox/1/skybox_positive_z.png",
            "texture/skybox/1/skybox_negative_z.png"
        );
        
        this.skyViewMatrix = new Matrix4f();
        
        this.sunriseShader = new Shader(new Resource("shader/level/sky/sunrise.vert"), new Resource("shader/level/sky/sunrise.frag"));
        this.skydiscShader = new Shader(new Resource("shader/level/sky/skydisc.vert"), new Resource("shader/level/sky/skydisc.frag"));
        
        this.sunriseVao = new VertexArray();
        this.sunriseVbo = new VertexBuffer();
        this.sunriseVbo.enableAttributes(new VertexAttr(3, Type.FLOAT), new VertexAttr(4, Type.FLOAT)); // pos3, col4
        
        this.lightSkyVao = new VertexArray();
        this.lightSkyVbo = new VertexBuffer();
        this.lightSkyVbo.enableAttributes(new VertexAttr(3, Type.FLOAT)); // pos3
        
        this.darkSkyVao = new VertexArray();
        this.darkSkyVbo = new VertexBuffer();
        this.darkSkyVbo.enableAttributes(new VertexAttr(3, Type.FLOAT)); // pos3
        
        buildSkyDisc(16).end(lightSkyVbo);
        buildSkyDisc(-16).end(darkSkyVbo);
        
        
        // final BufferBuilder builder = new BufferBuilder();
        // builder.vertex( 448, 24, -384);
        //
        // builder.vertex(-384, 24,  448);
        // builder.vertex(-384, 24, -384);
        //
        // builder.vertex( 448, 24,  448);
        // builder.end(lightSkyVbo);
    }
    
    
    private BufferBuilder buildSkyDisc(float radius){
        final BufferBuilder builder = new BufferBuilder();
        
        builder.vertex(0.0, radius, 0.0);
        
        for(int i = 0; i <= 360; i += 120){
            final float angle = i * Maths.ToRad;
        
            builder.vertex(
                radius * Math.cos(angle),
                0,
                radius * Math.sin(angle)
            );
        }
        
        return builder;
    }
    
    
    public void render(GameCamera camera){
        // Skybox
        skyViewMatrix.set(camera.getView());
        skyViewMatrix.cullPosition();
        // skyBox.render(camera.getProjection(), skyViewMatrix);
        
        final GameTime time = levelRenderer.getGameRenderer().getSession().getGame().getTime();
        final Color skyColor = getSkyColor();
        final Color fogColor = getFogColor();
        
        Gl.depthMask(false);
        Gl.clearColor(fogColor);
        Gl.cullFace(Face.FRONT);
        
        skydiscShader.bind();
        skydiscShader.setUniform("u_projection", camera.getProjection());
        skydiscShader.setUniform("u_view", skyViewMatrix);
        skydiscShader.setUniform("u_skyColor", skyColor);
        lightSkyVao.drawArrays(lightSkyVbo.getVerticesNum(), Primitive.TRIANGLE_FAN);
        
        Gl.cullFace(Face.BACK);
        Gl.depthMask(true);
        
        
        
        if(true)
            return;
        
        Gl.depthMask(false);
        Gl.cullFace(Face.FRONT);
        
        // Render light sky
        
        skydiscShader.bind();
        skydiscShader.setUniform("u_projection", camera.getProjection());
        skydiscShader.setUniform("u_view", skyViewMatrix);
        skydiscShader.setUniform("u_skyColor", skyColor);
        lightSkyVao.drawArrays(lightSkyVbo.getVerticesNum(), Primitive.TRIANGLE_FAN);
        
        // Render sunrise
        float[] sunriseColor = getSunriseColor(Maths.frac(time.getDays()));
        if(sunriseColor != null){
            final BufferBuilder bufferbuilder = new BufferBuilder();
            
            bufferbuilder.vertex(0, 100, 0)
                .color(sunriseColor[0], sunriseColor[1], sunriseColor[2]);
            
            int i = 16;
            for(int j = 0; j <= i; ++j){
                float angle = 2 * Maths.PI * j / i;
                float sin = Mathc.sin(angle);
                float cos = Mathc.cos(angle);
                bufferbuilder.vertex(sin * 120.0F, cos * 120.0F, -cos * 40.0F * sunriseColor[3]).color(sunriseColor[0], sunriseColor[1], sunriseColor[2], 0);
            }
            
            bufferbuilder.end(sunriseVbo);
            
            sunriseShader.bind();
            sunriseShader.setUniform("u_projection", camera.getProjection());
            sunriseShader.setUniform("u_view", skyViewMatrix);
            sunriseVao.drawArrays(sunriseVbo.getVerticesNum(), Primitive.TRIANGLE_FAN);
            
            System.out.println(sunriseVbo.getVerticesNum());
            
        }
        
        // Render dark sky
        if(camera.getY() < 0){
            skydiscShader.bind();
            skydiscShader.setUniform("u_projection", camera.getProjection());
            skydiscShader.setUniform("u_view", skyViewMatrix);
            skydiscShader.setUniform("u_skyColor", new Color(0, 0, 0, 1));
            darkSkyVao.drawArrays(darkSkyVbo.getVerticesNum(), Primitive.TRIANGLE_FAN);
        }
        
        Gl.depthMask(true);
        Gl.cullFace(Face.BACK);
    }
    
    
    public Color getFogColor(){
        final GameTime time = levelRenderer.getGameRenderer().getSession().getGame().getTime();

        return new Color(0.6, 0.75, 0.9, 0.95)
            .mul(Maths.map(Mathc.pow(Maths.sinDeg(time.getDays() * 360), 0.5), -Maths.Sqrt2, Maths.Sqrt2, 0, 1));
    }
    
    public Color getSkyColor(){
        final GameTime time = levelRenderer.getGameRenderer().getSession().getGame().getTime();

        return new Color(0.35, 0.6, 1, 1)
            .mul(Maths.map(Mathc.pow(Maths.sinDeg(time.getDays() * 360), 0.5), -Maths.Sqrt2, Maths.Sqrt2, 0, 1));
    }

    public float getFogStart(){
        return 0.6F;
    }
    
    public float[] getSunriseColor(float dayTime){
        final float[] sunriseCol = new float[4];
        
        final float cos = Mathc.cos(dayTime * Math.PI * 2);
        if(cos >= -0.4F && cos <= 0.4F) {
            
            final float a = cos / 0.4F * 0.5F + 0.5F;
            
            float b = 1 - (1 - Mathc.sin(a * (float)Math.PI)) * 0.99F;
            b *= b;
            
            sunriseCol[0] = a * 0.3F + 0.7F;
            sunriseCol[1] = a * a * 0.7F + 0.2F;
            sunriseCol[2] = a * a * 0 + 0.2F;
            sunriseCol[3] = b;
            
            return sunriseCol;
        }else
            return null;
    }
    
    
    @Override
    public void dispose(){
        skyBox.dispose();
        
        sunriseShader.dispose();
        sunriseVbo.dispose();
        sunriseVao.dispose();
        
        skydiscShader.dispose();
        lightSkyVbo.dispose();
        lightSkyVao.dispose();
    }
    
}
