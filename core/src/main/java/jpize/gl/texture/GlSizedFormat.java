package jpize.gl.texture;

import static org.lwjgl.opengl.GL46.*;

public enum GlSizedFormat{

    // COLOR
    ALPHA4         (GL_ALPHA4        , GlFormat.ALPHA),
    ALPHA8         (GL_ALPHA8        , GlFormat.ALPHA),
    ALPHA12        (GL_ALPHA12       , GlFormat.ALPHA),
    ALPHA16        (GL_ALPHA16       , GlFormat.ALPHA),

    R8	           (GL_R8	         , GlFormat.RED),
    R8_SNORM	   (GL_R8_SNORM	     , GlFormat.RED),
    R16	           (GL_R16	         , GlFormat.RED),
    R16_SNORM      (GL_R16_SNORM	 , GlFormat.RED),
    R8I	           (GL_R8I	         , GlFormat.RED),
    R8UI	       (GL_R8UI	         , GlFormat.RED),
    R16I	       (GL_R16I	         , GlFormat.RED),
    R16UI	       (GL_R16UI	     , GlFormat.RED),
    R32I	       (GL_R32I	         , GlFormat.RED),
    R32UI	       (GL_R32UI	     , GlFormat.RED),
    R16F	       (GL_R16F	         , GlFormat.RED),
    R32F	       (GL_R32F	         , GlFormat.RED),

    RG8	           (GL_RG8	         , GlFormat.RG),
    RG8_SNORM      (GL_RG8_SNORM	 , GlFormat.RG),
    RG16	       (GL_RG16	         , GlFormat.RG),
    RG16_SNORM     (GL_RG16_SNORM	 , GlFormat.RG),
    RG16F	       (GL_RG16F	     , GlFormat.RG),
    RG32F	       (GL_RG32F	     , GlFormat.RG),
    RG8I	       (GL_RG8I	         , GlFormat.RG),
    RG8UI	       (GL_RG8UI	     , GlFormat.RG),
    RG16I	       (GL_RG16I	     , GlFormat.RG),
    RG16UI	       (GL_RG16UI	     , GlFormat.RG),
    RG32I	       (GL_RG32I	     , GlFormat.RG),
    RG32UI	       (GL_RG32UI	     , GlFormat.RG),

    R3_G3_B2	   (GL_R3_G3_B2	     , GlFormat.RGB),
    RGB4	       (GL_RGB4	         , GlFormat.RGB),
    RGB5	       (GL_RGB5	         , GlFormat.RGB),
    RGB8	       (GL_RGB8	         , GlFormat.RGB),
    RGB8_SNORM     (GL_RGB8_SNORM	 , GlFormat.RGB),
    RGB10	       (GL_RGB10	     , GlFormat.RGB),
    RGB12	       (GL_RGB12	     , GlFormat.RGB),
    RGB16	       (GL_RGB16	     , GlFormat.RGB),
    RGB16_SNORM    (GL_RGB16_SNORM	 , GlFormat.RGB),
    SRGB8	       (GL_SRGB8	     , GlFormat.RGB),
    RGB16F	       (GL_RGB16F	     , GlFormat.RGB),
    RGB32F	       (GL_RGB32F	     , GlFormat.RGB),
    R11F_G11F_B10F (GL_R11F_G11F_B10F, GlFormat.RGB),
    RGB9_E5	       (GL_RGB9_E5	     , GlFormat.RGB),
    RGB8I	       (GL_RGB8I	     , GlFormat.RGB),
    RGB8UI	       (GL_RGB8UI	     , GlFormat.RGB),
    RGB16I	       (GL_RGB16I	     , GlFormat.RGB),
    RGB16UI	       (GL_RGB16UI	     , GlFormat.RGB),
    RGB32I	       (GL_RGB32I	     , GlFormat.RGB),
    RGB32UI	       (GL_RGB32UI	     , GlFormat.RGB),
    RGB10_A2UI     (GL_RGB10_A2UI    , GlFormat.RGB),

    RGBA2	       (GL_RGBA2	     , GlFormat.RGBA),
    RGBA4	       (GL_RGBA4	     , GlFormat.RGBA),
    RGB5_A1	       (GL_RGB5_A1	     , GlFormat.RGBA),
    RGBA8	       (GL_RGBA8	     , GlFormat.RGBA),
    RGBA8_SNORM    (GL_RGBA8_SNORM   , GlFormat.RGBA),
    RGB10_A2       (GL_RGB10_A2 	 , GlFormat.RGBA),
    RGBA12	       (GL_RGBA12	     , GlFormat.RGBA),
    RGBA16	       (GL_RGBA16	     , GlFormat.RGBA),
    RGBA16_SNORM   (GL_RGBA16_SNORM  , GlFormat.RGBA),
    SRGB8_ALPHA8   (GL_SRGB8_ALPHA8  , GlFormat.RGBA),
    RGBA16F	       (GL_RGBA16F	     , GlFormat.RGBA),
    RGBA32F	       (GL_RGBA32F	     , GlFormat.RGBA),
    RGBA8I	       (GL_RGBA8I	     , GlFormat.RGBA),
    RGBA8UI	       (GL_RGBA8UI	     , GlFormat.RGBA),
    RGBA16I	       (GL_RGBA16I	     , GlFormat.RGBA),
    RGBA16UI	   (GL_RGBA16UI	     , GlFormat.RGBA),
    RGBA32I	       (GL_RGBA32I	     , GlFormat.RGBA),
    RGBA32UI	   (GL_RGBA32UI	     , GlFormat.RGBA),

    // DEPTH AND STENCIL
    DEPTH_COMPONENT16  (GL_DEPTH_COMPONENT16 , GlFormat.DEPTH_COMPONENT),
    DEPTH_COMPONENT24  (GL_DEPTH_COMPONENT24 , GlFormat.DEPTH_COMPONENT),
    DEPTH_COMPONENT32  (GL_DEPTH_COMPONENT32 , GlFormat.DEPTH_COMPONENT),
    DEPTH_COMPONENT32F (GL_DEPTH_COMPONENT32F, GlFormat.DEPTH_COMPONENT),
    DEPTH24_STENCIL8   (GL_DEPTH24_STENCIL8  , GlFormat.DEPTH_STENCIL  ),
    DEPTH32F_STENCIL8  (GL_DEPTH32F_STENCIL8 , GlFormat.DEPTH_STENCIL  ),

    // COMPRESSED
    COMPRESSED_RED	                  (GL_COMPRESSED_RED	                , GlFormat.RED ),
    COMPRESSED_RED_RGTC1	          (GL_COMPRESSED_RED_RGTC1	            , GlFormat.RED ),
    COMPRESSED_SIGNED_RED_RGTC1       (GL_COMPRESSED_SIGNED_RED_RGTC1       , GlFormat.RED ),
    COMPRESSED_RG	                  (GL_COMPRESSED_RG	                    , GlFormat.RG  ),
    COMPRESSED_RG_RGTC2	              (GL_COMPRESSED_RG_RGTC2	            , GlFormat.RG  ),
    COMPRESSED_SIGNED_RG_RGTC2        (GL_COMPRESSED_SIGNED_RG_RGTC2        , GlFormat.RG  ),
    COMPRESSED_RGB	                  (GL_COMPRESSED_RGB	                , GlFormat.RGB ),
    COMPRESSED_RGB_BPTC_SIGNED_FLOAT  (GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT  , GlFormat.RGB ),
    COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT(GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT, GlFormat.RGB ),
    COMPRESSED_SRGB	                  (GL_COMPRESSED_SRGB	                , GlFormat.RGB ),
    COMPRESSED_RGBA	                  (GL_COMPRESSED_RGBA	                , GlFormat.RGBA),
    COMPRESSED_RGBA_BPTC_UNORM        (GL_COMPRESSED_RGBA_BPTC_UNORM        , GlFormat.RGBA),
    COMPRESSED_SRGB_ALPHA             (GL_COMPRESSED_SRGB_ALPHA             , GlFormat.RGBA),
    COMPRESSED_SRGB_ALPHA_BPTC_UNORM  (GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM  , GlFormat.RGBA);


    public final int GL;
    private final GlFormat baseFormat;

    GlSizedFormat(int GL, GlFormat baseFormat){
        this.GL = GL;
        this.baseFormat = baseFormat;
    }

    public GlFormat getBase(){
        return baseFormat;
    }


    public int getChannels(){
        return baseFormat.getChannels();
    }

}
