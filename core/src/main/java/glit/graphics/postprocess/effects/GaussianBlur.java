package glit.graphics.postprocess.effects;

import glit.Glit;
import glit.files.FileHandle;
import glit.graphics.postprocess.FrameBufferObject;
import glit.graphics.postprocess.PostProcessEffect;
import glit.graphics.postprocess.RenderBufferObject;
import glit.graphics.util.ScreenQuad;
import glit.graphics.util.Shader;

import static org.lwjgl.opengl.GL33.*;

public class GaussianBlur implements PostProcessEffect{

    private final FrameBufferObject fbo1, fbo2;
    private final RenderBufferObject rbo;
    private final Shader shader;
    private float radius;


    public GaussianBlur(float radius){
        this.radius = radius;

        int width = Glit.getWidth();
        int height = Glit.getHeight();

        // Frame Buffer 1 & Render Buffer
        fbo1 = new FrameBufferObject(width, height);
        fbo1.create();
        fbo1.bind();
        rbo = new RenderBufferObject(width, height);
        rbo.create();
        FrameBufferObject.unbind();

        // Frame Buffer 2
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();

        // Shader (quad)
        shader = new Shader(new FileHandle("shader/blur/blur.vert"), new FileHandle("shader/blur/blur.frag"));
    }


    public void setRadius(float radius){
        this.radius = radius;
    }

    @Override
    public void begin(){
        // Draw Scene In FBO1

        rbo.bind();
        fbo1.bind();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void end(){
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Draw FBO1+BlurX In FBO2

        fbo2.bind();
        glClear(GL_COLOR_BUFFER_BIT);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_axis", 0);
            shader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();

        // Draw FBO2+BlurY On Screen

        shader.bind();
        shader.setUniform("u_frame", fbo2.getTexture());
        shader.setUniform("u_axis", 1);
        shader.setUniform("u_radius", radius);
        ScreenQuad.render();
    }

    @Override
    public void end(PostProcessEffect target){
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Draw FBO1+BlurX In FBO2

        fbo2.bind();
        glClear(GL_COLOR_BUFFER_BIT);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_axis", 0);
            shader.setUniform("u_radius", radius);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();

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
