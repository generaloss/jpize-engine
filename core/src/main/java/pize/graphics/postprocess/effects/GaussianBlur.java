package pize.graphics.postprocess.effects;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.postprocess.PostProcessEffect;
import pize.graphics.postprocess.RenderBufferObject;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.Shader;

public class GaussianBlur implements PostProcessEffect{

    private final FrameBufferObject fbo1, fbo2;
    private final RenderBufferObject rbo;
    private final Shader shader;
    private float radius;


    public GaussianBlur(float radius){
        this.radius = radius;

        int width = Pize.getWidth();
        int height = Pize.getHeight();

        // Frame Buffer 1 & Render Buffer
        fbo1 = new FrameBufferObject(width, height);
        fbo1.create();
        fbo1.bind();
        rbo = new RenderBufferObject(width, height);
        rbo.create();
        fbo1.unbind();

        // Frame Buffer 2
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();

        // Shader (quad)
        shader = new Shader(new Resource("shader/blur/blur.vert"), new Resource("shader/blur/blur.frag"));
    }


    public void setRadius(float radius){
        this.radius = radius;
    }

    @Override
    public void begin(){
        // Draw Scene In FBO1

        rbo.bind();
        fbo1.bind();
        Gl.clearCDBuffers();
    }

    @Override
    public void end(){
        fbo1.unbind();
        rbo.unbind();

        // Draw FBO1+BlurX In FBO2

        fbo2.bind();
        Gl.clearColorBuffer();
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_axis", 0);
            shader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        fbo2.unbind();

        // Draw FBO2+BlurY On Screen

        shader.bind();
        shader.setUniform("u_frame", fbo2.getTexture());
        shader.setUniform("u_axis", 1);
        shader.setUniform("u_radius", radius);
        ScreenQuad.render();
    }

    @Override
    public void end(PostProcessEffect target){
        fbo1.unbind();
        rbo.unbind();

        // Draw FBO1+BlurX In FBO2

        fbo2.bind();
        Gl.clearColorBuffer();
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_axis", 0);
            shader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        fbo2.unbind();

        // Draw FBO2+BlurY In Target

        target.begin();

        shader.bind();
        shader.setUniform("u_frame", fbo2.getTexture());
        shader.setUniform("u_axis", 1);
        shader.setUniform("u_radius", radius);
        ScreenQuad.render();
    }

    @Override
    public void resize(int width, int height){
        fbo1.resize(width, height);
        fbo2.resize(width, height);

        rbo.resize(width, height);
    }

    @Override
    public void dispose(){
        fbo1.dispose();
        fbo2.dispose();

        rbo.dispose();

        shader.dispose();
    }

}
