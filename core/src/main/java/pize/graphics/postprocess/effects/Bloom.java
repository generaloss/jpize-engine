package pize.graphics.postprocess.effects;

import pize.Jize;
import pize.files.Resource;
import pize.gl.Gl;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.postprocess.PostProcessEffect;
import pize.graphics.postprocess.RenderBufferObject;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.Shader;

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

        int width = Jize.getWidth();
        int height = Jize.getHeight();

        // Frame Buffer 1 & Render Buffer
        colorBuffer = new FrameBufferObject(width, height);
        colorBuffer.create();
        colorBuffer.bind();
        depthBuffer = new RenderBufferObject(width, height);
        depthBuffer.create();
        colorBuffer.unbind();

        // Frame Buffers 2 & 3
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();
        blurBuffer = new FrameBufferObject(width, height);
        blurBuffer.create();

        // Shader
        final Resource vertexShader = new Resource("shader/bloom/bloom.vert");

        brightShader = new Shader(vertexShader, new Resource("shader/bloom/bloom_bright.frag"));
        blurShader = new Shader(vertexShader, new Resource("shader/bloom/bloom_blur.frag"));
        combineShader = new Shader(vertexShader, new Resource("shader/bloom/bloom_combine.frag"));
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
        Gl.clearColorDepthBuffers();
    }

    @Override
    public void end(){
        depthBuffer.unbind();
        colorBuffer.unbind();

        fbo2.bind();
        Gl.clearColorBuffer();
        {
            brightShader.bind();
            brightShader.setUniform("u_frame", colorBuffer.getTexture());
            brightShader.setUniform("u_brightness", brightness);
            ScreenQuad.render();
        }
        fbo2.unbind();

        blurBuffer.bind();
        Gl.clearColorBuffer();
        {
            blurShader.bind();
            blurShader.setUniform("u_frame", fbo2.getTexture());
            blurShader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        blurBuffer.unbind();

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
