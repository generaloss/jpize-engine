package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

// Before GL 4.3
public enum InternalFormat{

    // COLOR
    R8	           (GL_R8	          , Format.RED),
    R8_SNORM	   (GL_R8_SNORM	      , Format.RED),
    R16	           (GL_R16	          , Format.RED),
    R16_SNORM      (GL_R16_SNORM	  , Format.RED),
    R8I	           (GL_R8I	          , Format.RED),
    R8UI	       (GL_R8UI	          , Format.RED),
    R16I	       (GL_R16I	          , Format.RED),
    R16UI	       (GL_R16UI	      , Format.RED),
    R32I	       (GL_R32I	          , Format.RED),
    R32UI	       (GL_R32UI	      , Format.RED),
    R16F	       (GL_R16F	          , Format.RED),
    R32F	       (GL_R32F	          , Format.RED),

    RG8	           (GL_RG8	          , Format.RG),
    RG8_SNORM      (GL_RG8_SNORM	  , Format.RG),
    RG16	       (GL_RG16	          , Format.RG),
    RG16_SNORM     (GL_RG16_SNORM	  , Format.RG),
    RG16F	       (GL_RG16F	      , Format.RG),
    RG32F	       (GL_RG32F	      , Format.RG),
    RG8I	       (GL_RG8I	          , Format.RG),
    RG8UI	       (GL_RG8UI	      , Format.RG),
    RG16I	       (GL_RG16I	      , Format.RG),
    RG16UI	       (GL_RG16UI	      , Format.RG),
    RG32I	       (GL_RG32I	      , Format.RG),
    RG32UI	       (GL_RG32UI	      , Format.RG),

    R3_G3_B2	   (GL_R3_G3_B2	      , Format.RGB),
    RGB4	       (GL_RGB4	          , Format.RGB),
    RGB5	       (GL_RGB5	          , Format.RGB),
    RGB8	       (GL_RGB8	          , Format.RGB),
    RGB8_SNORM     (GL_RGB8_SNORM	  , Format.RGB),
    RGB10	       (GL_RGB10	      , Format.RGB),
    RGB12	       (GL_RGB12	      , Format.RGB),
    RGB16	       (GL_RGB16	      , Format.RGB),
    RGB16_SNORM    (GL_RGB16_SNORM	  , Format.RGB),
    SRGB8	       (GL_SRGB8	      , Format.RGB),
    RGB16F	       (GL_RGB16F	      , Format.RGB),
    RGB32F	       (GL_RGB32F	      , Format.RGB),
    R11F_G11F_B10F (GL_R11F_G11F_B10F , Format.RGB),
    RGB9_E5	       (GL_RGB9_E5	      , Format.RGB),
    RGB8I	       (GL_RGB8I	      , Format.RGB),
    RGB8UI	       (GL_RGB8UI	      , Format.RGB),
    RGB16I	       (GL_RGB16I	      , Format.RGB),
    RGB16UI	       (GL_RGB16UI	      , Format.RGB),
    RGB32I	       (GL_RGB32I	      , Format.RGB),
    RGB32UI	       (GL_RGB32UI	      , Format.RGB),
    RGB10_A2UI     (GL_RGB10_A2UI     , Format.RGB),

    RGBA2	       (GL_RGBA2	      , Format.RGBA),
    RGBA4	       (GL_RGBA4	      , Format.RGBA),
    RGB5_A1	       (GL_RGB5_A1	      , Format.RGBA),
    RGBA8	       (GL_RGBA8	      , Format.RGBA),
    RGBA8_SNORM    (GL_RGBA8_SNORM    , Format.RGBA),
    RGB10_A2       (GL_RGB10_A2 	  , Format.RGBA),
    RGBA12	       (GL_RGBA12	      , Format.RGBA),
    RGBA16	       (GL_RGBA16	      , Format.RGBA),
    RGBA16_SNORM   (GL_RGBA16_SNORM   , Format.RGBA),
    SRGB8_ALPHA8   (GL_SRGB8_ALPHA8   , Format.RGBA),
    RGBA16F	       (GL_RGBA16F	      , Format.RGBA),
    RGBA32F	       (GL_RGBA32F	      , Format.RGBA),
    RGBA8I	       (GL_RGBA8I	      , Format.RGBA),
    RGBA8UI	       (GL_RGBA8UI	      , Format.RGBA),
    RGBA16I	       (GL_RGBA16I	      , Format.RGBA),
    RGBA16UI	   (GL_RGBA16UI	      , Format.RGBA),
    RGBA32I	       (GL_RGBA32I	      , Format.RGBA),
    RGBA32UI	   (GL_RGBA32UI	      , Format.RGBA),

    ALPHA4         (GL_ALPHA4         , Format.ALPHA),
    ALPHA8         (GL_ALPHA8         , Format.ALPHA),
    ALPHA12        (GL_ALPHA12        , Format.ALPHA),
    ALPHA16        (GL_ALPHA16        , Format.ALPHA),

    // DEPTH AND STENCIL
    DEPTH_COMPONENT16  (GL_DEPTH_COMPONENT16 , Format.DEPTH_COMPONENT),
    DEPTH_COMPONENT24  (GL_DEPTH_COMPONENT24 , Format.DEPTH_COMPONENT),
    DEPTH_COMPONENT32  (GL_DEPTH_COMPONENT32 , Format.DEPTH_COMPONENT),
    DEPTH_COMPONENT32F (GL_DEPTH_COMPONENT32F, Format.DEPTH_COMPONENT),
    DEPTH24_STENCIL8   (GL_DEPTH24_STENCIL8  , Format.DEPTH_STENCIL  ),
    DEPTH32F_STENCIL8  (GL_DEPTH32F_STENCIL8 , Format.DEPTH_STENCIL  ),

    // COMPRESSED
    COMPRESSED_RED	            (GL_COMPRESSED_RED	            , Format.RED),
    COMPRESSED_RED_RGTC1	    (GL_COMPRESSED_RED_RGTC1	    , Format.RED),
    COMPRESSED_SIGNED_RED_RGTC1 (GL_COMPRESSED_SIGNED_RED_RGTC1 , Format.RED),
    COMPRESSED_RG	            (GL_COMPRESSED_RG	            , Format.RG),
    COMPRESSED_RG_RGTC2	        (GL_COMPRESSED_RG_RGTC2	        , Format.RG),
    COMPRESSED_SIGNED_RG_RGTC2  (GL_COMPRESSED_SIGNED_RG_RGTC2  , Format.RG),
    COMPRESSED_RGB	            (GL_COMPRESSED_RGB	            , Format.RGB),
    COMPRESSED_SRGB	            (GL_COMPRESSED_SRGB	            , Format.RGB),
    COMPRESSED_RGBA	            (GL_COMPRESSED_RGBA	            , Format.RGBA),
    COMPRESSED_SRGB_ALPHA       (GL_COMPRESSED_SRGB_ALPHA       , Format.RGBA);


    public final int gl;
    private final Format baseFormat;

    InternalFormat(int gl,Format baseFormat){
        this.gl = gl;
        this.baseFormat = baseFormat;
    }

    public Format getBaseFormat(){
        return baseFormat;
    }


    public int getChannels(){
        return baseFormat.getChannels();
    }

}
