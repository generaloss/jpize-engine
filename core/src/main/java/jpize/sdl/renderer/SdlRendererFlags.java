package jpize.sdl.renderer;

import static io.github.libsdl4j.api.render.SDL_RendererFlags.*;

public class SdlRendererFlags{

    private int flags;

    public SdlRendererFlags(){ }

    public SdlRendererFlags(int flags){
        this.flags = flags;
    }


    public int getFlags(){
        return flags;
    }


    /** The renderer is a software fallback */
    public SdlRendererFlags software(){
        flags |= SDL_RENDERER_SOFTWARE;
        return this;
    }

    public boolean isSoftware(){
        return (flags & SDL_RENDERER_SOFTWARE) == SDL_RENDERER_SOFTWARE;
    }


    /** The renderer uses hardware acceleration */
    public SdlRendererFlags accelerated(){
        flags |= SDL_RENDERER_ACCELERATED;
        return this;
    }

    public boolean isAccelerated(){
        return (flags & SDL_RENDERER_ACCELERATED) == SDL_RENDERER_ACCELERATED;
    }


    /** Present is synchronized with the refresh rate */
    public SdlRendererFlags presentVsync(){
        flags |= SDL_RENDERER_PRESENTVSYNC;
        return this;
    }

    public boolean isPresentVsync(){
        return (flags & SDL_RENDERER_PRESENTVSYNC) == SDL_RENDERER_PRESENTVSYNC;
    }


    /** The renderer supports  rendering to texture */
    public SdlRendererFlags targetTexture(){
        flags |= SDL_RENDERER_TARGETTEXTURE;
        return this;
    }

    public boolean isTargetTexture(){
        return (flags & SDL_RENDERER_TARGETTEXTURE) == SDL_RENDERER_TARGETTEXTURE;
    }

}
