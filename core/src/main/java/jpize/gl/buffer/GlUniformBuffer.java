package jpize.gl.buffer;

public class GlUniformBuffer extends GlBuffer{

    public GlUniformBuffer(){
        super(GlBufTarget.UNIFORM_BUFFER);
        allocateData(152);
    }

}
