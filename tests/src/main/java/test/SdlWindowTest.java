package test;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.event.SDL_EventType;
import io.github.libsdl4j.api.event.SdlEvents;
import jpize.gl.Gl;
import jpize.io.Window;
import jpize.sdl.Sdl;

public class SdlWindowTest{

    public static void main(String[] args){
        Sdl.init();
        final Window window = new Window("Window Title", 1280, 720);

        SDL_Event event = new SDL_Event();

        boolean exitRequest = false;

        while(!exitRequest){
            while(SdlEvents.SDL_PollEvent(event) != 0){
                switch(event.type){
                    case SDL_EventType.SDL_QUIT -> exitRequest = true;
                }
            }

            Gl.clearColorBuffer();
            Gl.clearColor(0.2, 0.5, 0.7);

            window.swapBuffers();
        }

        window.dispose();
        Sdl.quit();
    }

}
