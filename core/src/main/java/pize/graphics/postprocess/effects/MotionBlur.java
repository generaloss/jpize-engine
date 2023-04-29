package pize.graphics.postprocess.effects;

import pize.Pize;
import pize.files.FileHandle;
import pize.graphics.gl.Gl;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.postprocess.PostProcessEffect;
import pize.graphics.postprocess.RenderBufferObject;
import pize.graphics.texture.Texture;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.Shader;

public class MotionBlur implements PostProcessEffect{

    private final FrameBufferObject fbo1, fbo2;
    private final RenderBufferObject rbo1, rbo2;
    private final Shader shader;
    private final Texture backFrame;


    public MotionBlur(){
        int width = Pize.getWidth();
        int height = Pize.getHeight();

        // Frame & Render Buffer 1
        fbo1 = new FrameBufferObject(width, height);
        fbo1.create();
        fbo1.bind();
        rbo1 = new RenderBufferObject(width, height);
        rbo1.create();
        fbo1.unbind();

        // Frame & Render Buffer 2
        fbo2 = new FrameBufferObject(width, height);
        fbo2.create();
        fbo2.bind();
        rbo2 = new RenderBufferObject(width, height);
        rbo2.create();
        fbo2.unbind();

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
        Gl.clearBuffers(true);
    }

    @Override
    public void end(){
        fbo1.unbind();
        rbo1.unbind();

        // Draw Scene+FBO1 In FBO2

        fbo2.bind();
        rbo2.bind();
        Gl.clearBuffers(true);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_backFrame", backFrame);
            ScreenQuad.render();
        }
        fbo2.unbind();
        rbo2.unbind();

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
        fbo1.unbind();
        rbo1.unbind();

        // Draw Scene+FBO1 In FBO2

        fbo2.bind();
        rbo2.bind();
        Gl.clearBuffers(true);
        {
            shader.bind();
            shader.setUniform("u_frame", fbo1.getTexture());
            shader.setUniform("u_backFrame", backFrame);
            ScreenQuad.render();
        }
        fbo2.unbind();
        rbo2.unbind();

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
