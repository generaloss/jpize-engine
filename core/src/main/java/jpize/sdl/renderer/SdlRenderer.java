package jpize.sdl.renderer;

import io.github.libsdl4j.api.error.SdlError;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;
import jpize.sdl.window.SdlWindow;
import jpize.util.Disposable;

public class SdlRenderer implements Disposable{

    private final SDL_Renderer rendererSDL;

    public SdlRenderer(SdlWindow window, int index, int flags){
        this.rendererSDL = SdlRender.SDL_CreateRenderer(window.getSDL(), index, flags);
        if(this.rendererSDL == null)
            throw new IllegalStateException("Unable to create SDL renderer: " + SdlError.SDL_GetError());
    }


    public SdlRenderer(SdlWindow window, int index, SdlRendererFlags flags){
        this(window, index, flags.getFlags());
    }


    public void setDrawColor(double r, double g, double b, double a){
        SdlRender.SDL_SetRenderDrawColor(rendererSDL, (byte) (r * 255), (byte) (g * 255), (byte) (b * 255), (byte) (a * 255));
    }

    public void clear(){
        SdlRender.SDL_RenderClear(rendererSDL);
    }

    public void present(){
        SdlRender.SDL_RenderPresent(rendererSDL);
    }

    public SDL_Texture createTexture(int format, int access, int width, int height){
        return SdlRender.SDL_CreateTexture(rendererSDL, format, access, width, height);
    }

    public void destroyTexture(SDL_Texture texture){
        SdlRender.SDL_DestroyTexture(texture);
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        SdlRender.SDL_RenderDrawLine(rendererSDL, x1, y1, x2, y2);
    }

    // public void (){
    //     SdlRender.SDL_(rendererSDL);
    // }

    @Override
    public void dispose(){
        SdlRender.SDL_DestroyRenderer(rendererSDL);
    }

}
