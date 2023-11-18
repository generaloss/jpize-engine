package jpize.sdl.window;

import static io.github.libsdl4j.api.video.SDL_FlashOperation.*;

public enum SdlFlashOp{

    FLASH_CANCEL       (SDL_FLASH_CANCEL       ),
    FLASH_BRIEFLY      (SDL_FLASH_BRIEFLY      ),
    FLASH_UNTIL_FOCUSED(SDL_FLASH_UNTIL_FOCUSED);

    public final int SDL;

    SdlFlashOp(int SDL){
        this.SDL = SDL;
    }

}
