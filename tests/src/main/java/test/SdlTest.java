package test;

import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.texture.GlBlendFactor;
import jpize.sdl.window.SdlWindow;
import jpize.sdl.Sdl;
import jpize.sdl.gl.SdlGlAttr;
import jpize.sdl.window.SdlWinFlags;

public class SdlTest{

    public static void main(String[] args){
        Sdl.init();

        final SdlWindow window = new SdlWindow("Window Title", 1280, 720, new SdlWinFlags().openGL().shown().resizable());
        Sdl.enableVsync(true);
        window.toCenter();

        Sdl.setGlAttribute(SdlGlAttr.GL_CONTEXT_MAJOR_VERSION, 3);
        Sdl.setGlAttribute(SdlGlAttr.GL_CONTEXT_MINOR_VERSION, 5);
        Sdl.setGlAttribute(SdlGlAttr.GL_DEPTH_SIZE, 16);

        Gl.enable(GlTarget.BLEND, GlTarget.CULL_FACE);
        Gl.blendFunc(GlBlendFactor.SRC_ALPHA, GlBlendFactor.ONE_MINUS_SRC_ALPHA);

        // SDL_Event event = new SDL_Event();
        // main_loop:
        while(!Thread.currentThread().isInterrupted()){
            // while(SdlEvents.SDL_PollEvent(event) != 0){
            //     switch(event.type){
            //         case SDL_EventType.SDL_QUIT -> break main_loop;
            //     }
            // }

            // Gl.clearColorBuffer();
            // Gl.clearColor(0.2, 0.5, 0.7);

            // window.swapBuffers();
        }

        window.dispose();
        Sdl.quit();
    }

}
