package jpize.sdl.window;

import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;

public class SdlWinFlags{

    public static int DEFAULT = new SdlWinFlags().openGL().resizable().hidden().flags;


    private int flags;

    public SdlWinFlags(){ }

    public SdlWinFlags(int flags){
        this.flags = flags;
    }


    public int getFlags(){
        return flags;
    }


    /** fullscreen window */
    public SdlWinFlags fullscreen(){
        flags |= SDL_WINDOW_FULLSCREEN;
        return this;
    }

    public static boolean isFullscreen(int flags){
        return (flags & SDL_WINDOW_FULLSCREEN) == SDL_WINDOW_FULLSCREEN;
    }

    public boolean isFullscreen(){
        return isFullscreen(flags);
    }


    /** window usable with OpenGL context */
    public SdlWinFlags openGL(){
        flags |= SDL_WINDOW_OPENGL;
        return this;
    }

    public static boolean isOpenGL(int flags){
        return (flags & SDL_WINDOW_OPENGL) == SDL_WINDOW_OPENGL;
    }

    public boolean isOpenGL(){
        return isOpenGL(flags);
    }



    /** window is visible */
    public SdlWinFlags shown(){
        flags |= SDL_WINDOW_SHOWN;
        return this;
    }

    public static boolean isShown(int flags){
        return (flags & SDL_WINDOW_SHOWN) == SDL_WINDOW_SHOWN;
    }

    public boolean isShown(){
        return isShown(flags);
    }



    /** window is not visible */
    public SdlWinFlags hidden(){
        flags |= SDL_WINDOW_HIDDEN;
        return this;
    }

    public static boolean isHidden(int flags){
        return (flags & SDL_WINDOW_HIDDEN) == SDL_WINDOW_HIDDEN;
    }

    public boolean isHidden(){
        return isHidden(flags);
    }



    /** no window decoration */
    public SdlWinFlags borderless(){
        flags |= SDL_WINDOW_BORDERLESS;
        return this;
    }

    public static boolean isBorderless(int flags){
        return (flags & SDL_WINDOW_BORDERLESS) == SDL_WINDOW_BORDERLESS;
    }

    public boolean isBorderless(){
        return isBorderless(flags);
    }



    /** window can be resized */
    public SdlWinFlags resizable(){
        flags |= SDL_WINDOW_RESIZABLE;
        return this;
    }

    public static boolean isResizable(int flags){
        return (flags & SDL_WINDOW_RESIZABLE) == SDL_WINDOW_RESIZABLE;
    }

    public boolean isResizable(){
        return isResizable(flags);
    }



    /** window is minimized */
    public SdlWinFlags minimized(){
        flags |= SDL_WINDOW_MINIMIZED;
        return this;
    }

    public static boolean isMinimized(int flags){
        return (flags & SDL_WINDOW_MINIMIZED) == SDL_WINDOW_MINIMIZED;
    }

    public boolean isMinimized(){
        return isMinimized(flags);
    }



    /** window is maximized */
    public SdlWinFlags maximized(){
        flags |= SDL_WINDOW_MAXIMIZED;
        return this;
    }

    public static boolean isMaximized(int flags){
        return (flags & SDL_WINDOW_MAXIMIZED) == SDL_WINDOW_MAXIMIZED;
    }

    public boolean isMaximized(){
        return isMaximized(flags);
    }



    /** window has grabbed mouse input */
    public SdlWinFlags mouseGrabbed(){
        flags |= SDL_WINDOW_MOUSE_GRABBED;
        return this;
    }

    public static boolean isMouseGrabbed(int flags){
        return (flags & SDL_WINDOW_MOUSE_GRABBED) == SDL_WINDOW_MOUSE_GRABBED;
    }

    public boolean isMouseGrabbed(){
        return isMouseGrabbed(flags);
    }



    /** equivalent to SDL_WINDOW_MOUSE_GRABBED for compatibility */
    public SdlWinFlags inputGrabbed(){
        flags |= SDL_WINDOW_INPUT_GRABBED;
        return this;
    }

    public static boolean isInputGrabbed(int flags){
        return (flags & SDL_WINDOW_INPUT_GRABBED) == SDL_WINDOW_INPUT_GRABBED;
    }

    public boolean isInputGrabbed(){
        return isInputGrabbed(flags);
    }



    /** window has mouse focus */
    public SdlWinFlags mouseFocus(){
        flags |= SDL_WINDOW_MOUSE_FOCUS;
        return this;
    }

    public static boolean isMouseFocus(int flags){
        return (flags & SDL_WINDOW_MOUSE_FOCUS) == SDL_WINDOW_MOUSE_FOCUS;
    }

    public boolean isMouseFocus(){
        return isMouseFocus(flags);
    }



    /** window has input focus */
    public SdlWinFlags inputFocus(){
        flags |= SDL_WINDOW_INPUT_FOCUS;
        return this;
    }

    public static boolean isInputFocus(int flags){
        return (flags & SDL_WINDOW_INPUT_FOCUS) == SDL_WINDOW_INPUT_FOCUS;
    }

    public boolean isInputFocus(){
        return isInputFocus(flags);
    }




    public SdlWinFlags fullscreenDesktop(){
        flags |= SDL_WINDOW_FULLSCREEN_DESKTOP;
        return this;
    }

    public static boolean isFullscreenDesktop(int flags){
        return (flags & SDL_WINDOW_FULLSCREEN_DESKTOP) == SDL_WINDOW_FULLSCREEN_DESKTOP;
    }

    public boolean isFullscreenDesktop(){
        return isFullscreenDesktop(flags);
    }



    /** window not created by SDL */
    public SdlWinFlags foreign(){
        flags |= SDL_WINDOW_FOREIGN;
        return this;
    }

    public static boolean isForeign(int flags){
        return (flags & SDL_WINDOW_FOREIGN) == SDL_WINDOW_FOREIGN;
    }

    public boolean isForeign(){
        return isForeign(flags);
    }



    /**
     * window should be created in high-DPI mode if supported.
     * On macOS NSHighResolutionCapable must be set true in the
     * application's Info.plist for this to have any effect.
     */
    public SdlWinFlags allowHighDPI(){
        flags |= SDL_WINDOW_ALLOW_HIGHDPI;
        return this;
    }

    public static boolean isAllowHighDPI(int flags){
        return (flags & SDL_WINDOW_ALLOW_HIGHDPI) == SDL_WINDOW_ALLOW_HIGHDPI;
    }

    public boolean isAllowHighDPI(){
        return isAllowHighDPI(flags);
    }



    /** window has mouse captured (unrelated to MOUSE_GRABBED) */
    public SdlWinFlags mouseCapture(){
        flags |= SDL_WINDOW_MOUSE_CAPTURE;
        return this;
    }

    public static boolean isMouseCapture(int flags){
        return (flags & SDL_WINDOW_MOUSE_CAPTURE) == SDL_WINDOW_MOUSE_CAPTURE;
    }

    public boolean isMouseCapture(){
        return isMouseCapture(flags);
    }



    /** window should always be above others */
    public SdlWinFlags alwaysOnTop(){
        flags |= SDL_WINDOW_ALWAYS_ON_TOP;
        return this;
    }

    public static boolean isAlwaysOnTop(int flags){
        return (flags & SDL_WINDOW_ALWAYS_ON_TOP) == SDL_WINDOW_ALWAYS_ON_TOP;
    }

    public boolean isAlwaysOnTop(){
        return isAlwaysOnTop(flags);
    }



    /** window should not be added to the taskbar */
    public SdlWinFlags skipTaskbar(){
        flags |= SDL_WINDOW_SKIP_TASKBAR;
        return this;
    }

    public static boolean isSkipTaskbar(int flags){
        return (flags & SDL_WINDOW_SKIP_TASKBAR) == SDL_WINDOW_SKIP_TASKBAR;
    }

    public boolean isSkipTaskbar(){
        return isSkipTaskbar(flags);
    }



    /** window should be treated as a utility window */
    public SdlWinFlags utility(){
        flags |= SDL_WINDOW_UTILITY;
        return this;
    }

    public static boolean isUtility(int flags){
        return (flags & SDL_WINDOW_UTILITY) == SDL_WINDOW_UTILITY;
    }

    public boolean isUtility(){
        return isUtility(flags);
    }



    /** window should be treated as a tooltip */
    public SdlWinFlags tooltip(){
        flags |= SDL_WINDOW_TOOLTIP;
        return this;
    }

    public static boolean isTooltip(int flags){
        return (flags & SDL_WINDOW_TOOLTIP) == SDL_WINDOW_TOOLTIP;
    }

    public boolean isTooltip(){
        return isTooltip(flags);
    }



    /** window should be treated as a popup menu */
    public SdlWinFlags popupMenu(){
        flags |= SDL_WINDOW_POPUP_MENU;
        return this;
    }

    public static boolean isPopupMenu(int flags){
        return (flags & SDL_WINDOW_POPUP_MENU) == SDL_WINDOW_POPUP_MENU;
    }

    public boolean isPopupMenu(){
        return isPopupMenu(flags);
    }



    /** window has grabbed keyboard input */
    public SdlWinFlags keyboardGrabbed(){
        flags |= SDL_WINDOW_KEYBOARD_GRABBED;
        return this;
    }

    public static boolean isKeyboardGrabbed(int flags){
        return (flags & SDL_WINDOW_KEYBOARD_GRABBED) == SDL_WINDOW_KEYBOARD_GRABBED;
    }

    public boolean isKeyboardGrabbed(){
        return isKeyboardGrabbed(flags);
    }



    /** window usable for Vulkan surface */
    public SdlWinFlags vulkan(){
        flags |= SDL_WINDOW_VULKAN;
        return this;
    }

    public static boolean isVulkan(int flags){
        return (flags & SDL_WINDOW_VULKAN) == SDL_WINDOW_VULKAN;
    }

    public boolean isVulkan(){
        return isVulkan(flags);
    }



    /** window usable for Metal view */
    public SdlWinFlags metal(){
        flags |= SDL_WINDOW_METAL;
        return this;
    }

    public static boolean isMetal(int flags){
        return (flags & SDL_WINDOW_METAL) == SDL_WINDOW_METAL;
    }

    public boolean isMetal(){
        return isMetal(flags);
    }

}
