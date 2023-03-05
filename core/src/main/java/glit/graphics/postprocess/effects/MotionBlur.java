package glit.graphics.postprocess.effects;

import glit.Glit;
import glit.files.FileHandle;
import glit.graphics.postprocess.FrameBufferObject;
import glit.graphics.postprocess.PostProcessEffect;
import glit.graphics.postprocess.RenderBufferObject;
import glit.graphics.texture.Texture;
import glit.graphics.util.ScreenQuad;
import glit.graphics.util.Shader;

import static org.lwjgl.opengl.GL33.*;

public class MotionBlur implements PostProcessEffect{

    private final FrameBufferObject fbo1, fbo2;
    private final RenderBufferObject rbo1, rbo2;
    private final Shader shader;
    private final Texture backFrame;


    public MotionBlur(){
        int width = Glit.getWidth();
        int height = Glit.getHeight();

        // Frame & Render Buffer 1
        fbo1 = new FrameBufferObject(width, height);
        fbo1.create();
        fbo1.bind();
        rbo1 = new RenderBufferObject(width, height);
        rbo1.create();
        FrameBufferObject.unbind();

        // Frame & Render Buffer 2
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();
        fbo2.bind();
        rbo2 = new RenderBufferObject(width, height);
        rbo2.create();
        FrameBufferObject.unbind();

        // Shader (quad)
        shader = new Shader(new FileHandle("shader/motion/motion.vert"), new FileHandle("shader/motion/motion.frag"));

        // Texture (previous frame)
        backFrame = new Texture(width, height);
    }


    @Override
    public void begin(){
        // Draw Scene In Fbo1

        fbo1.bind();
        rbo1.bind();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void end(){
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Draw Scene+FBO1 In FBO2

        fbo2.bind();
        rbo2.bind();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_backFrame", backFrame);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Copy FBO2 as backFrame

        fbo2.copyTo(backFrame);

        // Draw Scene+FBO1 On Screen

        shader.bind();
        shader.setUniform("u_frame", fbo1.getTexture());
        shader.setUniform("u_backFrame", backFrame);
        ScreenQuad.render();
    }

    @Override
    public void end(PostProcessEffect target){
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Draw Scene+FBO1 In FBO2

        fbo2.bind();
        rbo2.bind();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_backFrame", backFrame);
            ScreenQuad.render();
        }
        FrameBufferObject.unbind();
        RenderBufferObject.unbind();

        // Copy FBO2 as backFrame

        fbo2.copyTo(backFrame);

        // Draw Scene+FBO1 In Target

        target.begin();

        shader.bind();
        shader.setUniform("u_frame", fbo1.getTexture());
        shader.setUniform("u_backFrame", backFrame);
        ScreenQuad.render();
    }

    @Override
    public void resize(int width, int height){
        fbo1.resize(width, height);
        fbo2.resize(width, height);

        rbo1.resize(width, height);
        rbo2.resize(width, height);

        backFrame.resize(width, height);
    }

    @Override
    public void dispose(){
        fbo1.dispose();
        fbo2.dispose();

        rbo1.dispose();
        rbo2.dispose();

        backFrame.dispose();

        shader.dispose();
    }

}
