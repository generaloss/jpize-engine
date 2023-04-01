package glit.graphics.postprocess.effects;

import glit.Pize;
import glit.files.FileHandle;
import glit.graphics.postprocess.FrameBufferObject;
import glit.graphics.postprocess.PostProcessEffect;
import glit.graphics.postprocess.RenderBufferObject;
import glit.graphics.util.ScreenQuad;
import glit.graphics.util.Shader;

import static org.lwjgl.opengl.GL33.*;

public class Bloom implements PostProcessEffect{

    private float brightness, radius, bloom, exposure, gamma;

    private final FrameBufferObject colorBuffer, fbo2, blurBuffer;
    private final RenderBufferObject depthBuffer;
    private final Shader brightShader, blurShader, combineShader;


    public Bloom(float brightness, float radius){
        this.brightness = brightness;
        this.radius = radius;

        bloom = 1F;
        exposure = 2F;
        gamma = 0.6F;

        int width = Pize.getWidth();
        int height = Pize.getHeight();

        // Frame Buffer 1 & Render Buffer
        colorBuffer = new FrameBufferObject(width, height);
        colorBuffer.create();
        colorBuffer.bind();
        depthBuffer = new RenderBufferObject(width, height);
        depthBuffer.create();
        FrameBufferObject.unbind();

        // Frame Buffers 2 & 3
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();
        blurBuffer = new FrameBufferObject(width, height);
        blurBuffer.create();

        // Shader
        FileHandle vertexShader = new FileHandle("shader/bloom/bloom.vert");

        brightShader = new Shader(vertexShader, new FileHandle("shader/bloom/bloom_bright.frag"));
        blurShader = new Shader(vertexShader, new FileHandle("shader/bloom/bloom_blur.frag"));
        combineShader = new Shader(vertexShader, new FileHandle("shader/bloom/bloom_combine.frag"));
    }


    public void setBrightness(float brightness){
        this.brightness = brightness;
    }

    public void setRadius(float radius){
        this.radius = radius;
    }

    public void setBloom(float bloom){
        this.bloom = bloom;
    }

    public void setExposure(float exposure){
        this.exposure = exposure;
    }

    public void setGamma(float gamma){
        this.gamma = gamma;
    }

    @Override
    public void begin(){
        // Draw Scene In FBO1

        depthBuffer.bind();
        colorBuffer.bind();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void end(){
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        fbo2.bind();
        glClear(GL_COLOR_BUFFER_BIT);
        {
            brightShader.bind();
            brightShader.setUniform("u_frame", colorBuffer.getTexture());
            brightShader.setUniform("u_brightness", brightness);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();

        blurBuffer.bind();
        glClear(GL_COLOR_BUFFER_BIT);
        {
            blurShader.bind();
            blurShader.setUniform("u_frame", fbo2.getTexture());
            blurShader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();

        combineShader.bind();
        combineShader.setUniform("u_frame1", colorBuffer.getTexture());
        combineShader.setUniform("u_frame2", blurBuffer.getTexture());
        combineShader.setUniform("u_bloom", bloom);
        combineShader.setUniform("u_exposure", exposure);
        combineShader.setUniform("u_gamma", gamma);
        ScreenQuad.render();
    }

    @Override
    public void end(PostProcessEffect target){}

    @Override
    public void resize(int width, int height){
        depthBuffer.resize(width, height);
        colorBuffer.resize(width, height);
        fbo2.resize(width, height);
        blurBuffer.resize(width, height);
    }

    @Override
    public void dispose(){
        depthBuffer.dispose();
        colorBuffer.dispose();
        fbo2.dispose();
        blurBuffer.dispose();
        brightShader.dispose();
        blurShader.dispose();
        combineShader.dispose();
    }

}
