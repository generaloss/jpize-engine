package jpize.sdl.window;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import io.github.libsdl4j.api.error.SdlError;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.surface.SDL_Surface;
import io.github.libsdl4j.api.video.*;
import jpize.Jpize;
import jpize.math.vecmath.vector.Vec2i;
import jpize.sdl.image.SdlImage;
import jpize.util.Disposable;

import java.awt.*;

import static io.github.libsdl4j.api.video.SdlVideo.SDL_GetDisplayMode;
import static io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED;

public class SdlWindow implements Disposable{

    private static final IntByReference tmp_int_1 = new IntByReference();
    private static final IntByReference tmp_int_2 = new IntByReference();
    private static final IntByReference tmp_int_3 = new IntByReference();
    private static final IntByReference tmp_int_4 = new IntByReference();
    private static final FloatByReference tmp_float_1 = new FloatByReference();
    private static final SDL_Rect tmp_rect_1 = new SDL_Rect();


    final SDL_Window windowSDL;

    public SdlWindow(int ID){
        this.windowSDL = SdlVideo.SDL_GetWindowFromID(ID);
    }

    public SdlWindow(SDL_Window windowSDL){
        this.windowSDL = windowSDL;
    }

    public SdlWindow(String title, int x, int y, int width, int height, int flags){
        this.windowSDL = SdlVideo.SDL_CreateWindow(title, x, y, width, height, flags);
        if(this.windowSDL == null)
            throw new IllegalStateException("Unable to create SDL window: " + SdlError.SDL_GetError());
    }

    public SdlWindow(String title, int x, int y, int width, int height, SdlWinFlags flags){
        this(title, x, y, width, height, flags.getFlags());
    }

    public SdlWindow(String title, int x, int y, int width, int height){
        this(title, x, y, width, height, SdlWinFlags.DEFAULT);
    }

    public SdlWindow(String title, int width, int height){
        this(title, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height);
    }

    public SdlWindow(String title, int width, int height, int flags){
        this(title, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, flags);
    }

    public SdlWindow(String title, int width, int height, SdlWinFlags flags){
        this(title, width, height, flags.getFlags());
    }


    public SDL_Window getSDL(){
        return windowSDL;
    }

    public int getID(){
        return SdlVideo.SDL_GetWindowID(windowSDL);
    }


    public String getTitle(){
        return SdlVideo.SDL_GetWindowTitle(windowSDL);
    }

    public void setTitle(String title){
        SdlVideo.SDL_SetWindowTitle(windowSDL, title);
    }


    public Vec2i getPos(){
        SdlVideo.SDL_GetWindowPosition(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public void setPos(int x, int y){
        SdlVideo.SDL_SetWindowPosition(windowSDL, x, y);
    }

    public void toCenter(){
        SdlVideo.SDL_SetWindowPosition(windowSDL, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED);
    }


    public Vec2i getSize(){
        SdlVideo.SDL_GetWindowSize(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public int getWidth(){
        SdlVideo.SDL_GetWindowSize(windowSDL, tmp_int_1, null);
        return tmp_int_1.getValue();
    }

    public int getHeight(){
        SdlVideo.SDL_GetWindowSize(windowSDL, null, tmp_int_2);
        return tmp_int_2.getValue();
    }

    public Vec2i getSizeInPixels(){
        SdlVideo.SDL_GetWindowSizeInPixels(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public float aspect(){
        return (float) getWidth() / getHeight();
    }

    public void setSize(int width, int height){
        SdlVideo.SDL_SetWindowSize(windowSDL, width, height);
    }


    public Vec2i getMinimumSize(){
        SdlVideo.SDL_GetWindowMinimumSize(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public void setMinimumSize(int width, int height){
        SdlVideo.SDL_SetWindowMinimumSize(windowSDL, width, height);
    }


    public Vec2i getMaximumSize(){
        SdlVideo.SDL_GetWindowMaximumSize(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }

    public void setMaximumSize(int width, int height){
        SdlVideo.SDL_SetWindowMaximumSize(windowSDL, width, height);
    }


    public boolean getGrab(){
        return SdlVideo.SDL_GetWindowGrab(windowSDL);
    }

    public void setGrab(boolean grabbed){
        SdlVideo.SDL_SetWindowGrab(windowSDL, grabbed);
    }


    public boolean getKeyboardGrab(){
        return SdlVideo.SDL_GetWindowKeyboardGrab(windowSDL);
    }

    public void setKeyboardGrab(boolean grabbed){
        SdlVideo.SDL_SetWindowKeyboardGrab(windowSDL, grabbed);
    }


    public boolean getMouseGrab(){
        return SdlVideo.SDL_GetWindowMouseGrab(windowSDL);
    }

    public void setMouseGrab(boolean grabbed){
        SdlVideo.SDL_SetWindowMouseGrab(windowSDL, grabbed);
    }


    public float getBrightness(){
        return SdlVideo.SDL_GetWindowBrightness(windowSDL);
    }

    public void setBrightness(float brightness){
        SdlVideo.SDL_SetWindowBrightness(windowSDL, brightness);
    }


    public float getOpacity(){
        SdlVideo.SDL_GetWindowOpacity(windowSDL, tmp_float_1);
        return tmp_float_1.getValue();
    }

    public void setOpacity(float opacity){
        SdlVideo.SDL_SetWindowOpacity(windowSDL, opacity);
    }


    public void setInputFocus(){
        SdlVideo.SDL_SetWindowInputFocus(windowSDL);
    }

    public void setResizable(boolean resizable){
        SdlVideo.SDL_SetWindowResizable(windowSDL, resizable);
    }


    public Insets getBordersSize(){
        SdlVideo.SDL_GetWindowBordersSize(windowSDL, tmp_int_1, tmp_int_2, tmp_int_3, tmp_int_4);
        return new Insets(tmp_int_1.getValue(), tmp_int_2.getValue(), tmp_int_3.getValue(), tmp_int_4.getValue());
    }

    public void setBordered(boolean bordered){
        SdlVideo.SDL_SetWindowBordered(windowSDL, bordered);
    }


    public SDL_Rect getMouseRect(){
        return SdlVideo.SDL_GetWindowMouseRect(windowSDL);
    }

    public void setMouseRect(int x, int y, int width, int height){
        tmp_rect_1.x = x;
        tmp_rect_1.y = y;
        tmp_rect_1.w = width;
        tmp_rect_1.h = height;
        SdlVideo.SDL_SetWindowMouseRect(windowSDL, tmp_rect_1);
    }


    public SDL_Surface getSurface(){
        return SdlVideo.SDL_GetWindowSurface(windowSDL);
    }

    public void updateSurface(){
        SdlVideo.SDL_UpdateWindowSurface(windowSDL);
    }

    public void hasSurface(){
        SdlVideo.SDL_HasWindowSurface(windowSDL);
    }

    public void setIcon(SDL_Surface icon){
        SdlVideo.SDL_SetWindowIcon(windowSDL, icon);
    }

    public void setIcon(String iconPath){
        setIcon(SdlImage.load(iconPath));
    }


    public Pointer getData(String name){
        return SdlVideo.SDL_GetWindowData(windowSDL, name);
    }

    public void setData(String name, Pointer userData){
        SdlVideo.SDL_SetWindowData(windowSDL, name, userData);
    }


    public SDL_DisplayMode getDisplayMode(){
        final SDL_DisplayMode mode = new SDL_DisplayMode();
        SdlVideo.SDL_GetWindowDisplayMode(windowSDL, mode);
        return mode;
    }

    public SDL_DisplayMode getDisplayMode(int modeIndex){
        final SDL_DisplayMode mode = new SDL_DisplayMode();
        SDL_GetDisplayMode(Jpize.window().getDisplayIndex(), modeIndex, mode);
        return mode;
    }

    public void setDisplayMode(SDL_DisplayMode mode){
        SdlVideo.SDL_SetWindowDisplayMode(windowSDL, mode);
    }


    public void getGammaRamp(short[] red, short[] green, short[] blue){
        SdlVideo.SDL_GetWindowGammaRamp(windowSDL, red, green, blue);
    }

    public void setGammaRamp(short[] red, short[] green, short[] blue){
        SdlVideo.SDL_SetWindowGammaRamp(windowSDL, red, green, blue);
    }


    public int getDisplayIndex(){
        return SdlVideo.SDL_GetWindowDisplayIndex(windowSDL);
    }

    public int getPixelFormat(){
        return SdlVideo.SDL_GetWindowPixelFormat(windowSDL);
    }

    public byte[] getIccProfile(){
        return SdlVideo.SDL_GetWindowICCProfile(windowSDL);
    }

    public Vec2i getDrawableSize(){
        SdlVideo.SDL_GL_GetDrawableSize(windowSDL, tmp_int_1, tmp_int_2);
        return new Vec2i(tmp_int_1.getValue(), tmp_int_2.getValue());
    }


    public void setFullscreen(boolean fullscreen){
        SdlVideo.SDL_SetWindowFullscreen(windowSDL, fullscreen ? SDL_WindowFlags.SDL_WINDOW_FULLSCREEN : 0);
    }

    public void setFullscreenDesktop(boolean fullscreen){
        SdlVideo.SDL_SetWindowFullscreen(windowSDL, fullscreen ? SDL_WindowFlags.SDL_WINDOW_FULLSCREEN_DESKTOP : 0);
    }

    public void toggleFullscreen(){
        setFullscreen(!isFullscreen());
    }

    public void toggleFullscreenDesktop(){
        setFullscreenDesktop(!isFullscreenDesktop());
    }


    public void setModalFor(SdlWindow window){
        SdlVideo.SDL_SetWindowModalFor(windowSDL, window.getSDL());
    }

    public void setAlwaysOnTop(boolean onTop){
        SdlVideo.SDL_SetWindowAlwaysOnTop(windowSDL, onTop);
    }

    public void setHitTest(SDL_HitTest callback){
        SdlVideo.SDL_SetWindowHitTest(windowSDL, callback, null);
    }


    public void swapBuffers(){
        SdlVideo.SDL_GL_SwapWindow(windowSDL);
    }


    public void show(){
        SdlVideo.SDL_ShowWindow(windowSDL);
    }

    public void hide(){
        SdlVideo.SDL_HideWindow(windowSDL);
    }


    public void raise(){
        SdlVideo.SDL_RaiseWindow(windowSDL);
    }

    public void restore(){
        SdlVideo.SDL_RestoreWindow(windowSDL);
    }


    public void flash(SdlFlashOp operation){
        SdlVideo.SDL_FlashWindow(windowSDL, operation.SDL);
    }


    public int getIntFlags(){
        return SdlVideo.SDL_GetWindowFlags(windowSDL);
    }

    public SdlWinFlags getFlags(){
        return new SdlWinFlags(getIntFlags());
    }

    public boolean isFullscreen(){
        return SdlWinFlags.isFullscreen(getIntFlags());
    }

    public boolean isOpenGL(){
        return SdlWinFlags.isOpenGL(getIntFlags());
    }

    public boolean isShown(){
        return SdlWinFlags.isShown(getIntFlags());
    }

    public boolean isHidden(){
        return SdlWinFlags.isHidden(getIntFlags());
    }

    public boolean isBorderless(){
        return SdlWinFlags.isBorderless(getIntFlags());
    }

    public boolean isResizable(){
        return SdlWinFlags.isResizable(getIntFlags());
    }

    public boolean isMinimized(){
        return SdlWinFlags.isMinimized(getIntFlags());
    }

    public boolean isMaximized(){
        return SdlWinFlags.isMaximized(getIntFlags());
    }

    public boolean isMouseGrabbed(){
        return SdlWinFlags.isMouseGrabbed(getIntFlags());
    }

    public boolean isInputGrabbed(){
        return SdlWinFlags.isInputGrabbed(getIntFlags());
    }

    public boolean isMouseFocus(){
        return SdlWinFlags.isMouseFocus(getIntFlags());
    }

    public boolean isInputFocus(){
        return SdlWinFlags.isInputFocus(getIntFlags());
    }

    public boolean isFullscreenDesktop(){
        return SdlWinFlags.isFullscreenDesktop(getIntFlags());
    }

    public boolean isForeign(){
        return SdlWinFlags.isForeign(getIntFlags());
    }

    public boolean isAllowHighDPI(){
        return SdlWinFlags.isAllowHighDPI(getIntFlags());
    }

    public boolean isMouseCapture(){
        return SdlWinFlags.isMouseCapture(getIntFlags());
    }

    public boolean isAlwaysOnTop(){
        return SdlWinFlags.isAlwaysOnTop(getIntFlags());
    }

    public boolean isSkipTaskbar(){
        return SdlWinFlags.isSkipTaskbar(getIntFlags());
    }

    public boolean isUtility(){
        return SdlWinFlags.isUtility(getIntFlags());
    }

    public boolean isTooltip(){
        return SdlWinFlags.isTooltip(getIntFlags());
    }

    public boolean isPopupMenu(){
        return SdlWinFlags.isPopupMenu(getIntFlags());
    }

    public boolean isKeyboardGrabbed(){
        return SdlWinFlags.isKeyboardGrabbed(getIntFlags());
    }

    public boolean isVulkan(){
        return SdlWinFlags.isVulkan(getIntFlags());
    }

    public boolean isMetal(){
        return SdlWinFlags.isMetal(getIntFlags());
    }


    @Override
    public void dispose(){
        SdlVideo.SDL_DestroyWindow(windowSDL);
    }

}
