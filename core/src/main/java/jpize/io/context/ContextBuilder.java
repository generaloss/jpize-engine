package jpize.io.context;

import jpize.glfw.glfw.Glfw;
import jpize.glfw.glfw.GlfwHint;
import jpize.graphics.util.color.Color;
import jpize.io.Window;
import jpize.math.vecmath.vector.Vec2i;

public class ContextBuilder{

    static{
        ContextManager.init();
    }

    public static ContextBuilder newContext(){
        return new ContextBuilder();
    }

    public static ContextBuilder newContext(String title){
        return newContext().title(title);
    }

    public static ContextBuilder newContext(int width, int height){
        return newContext().size(width, height);
    }

    public static ContextBuilder newContext(int width, int height, String title){
        return newContext(width, height).title(title);
    }


    private final Vec2i size = new Vec2i(800, 600);
    private String title = "";
    private String icon = null;

    private boolean vsync = true;
    private boolean resizable = true;
    private boolean fullscreen = false;
    private boolean transparentFramebuffer = false;
    private boolean exitOnClose = true;
    private boolean showWindowOnInit = true;
    private boolean borderless = false;

    private int samples = 1;
    private float opacity = 1F;
    private Window shared = null;
    private Vec2i position = null;
    private Color initialColor = null;


    /** Создать <b>контекст</b> окна по заданным ранее параметрам */
    public Context register(){
        // Hints
        Glfw.defaultWindowHints();
        Glfw.windowHint(GlfwHint.VISIBLE, 0);
        Glfw.windowHint(GlfwHint.SAMPLES, samples);
        Glfw.windowHint(GlfwHint.RESIZABLE, resizable);
        Glfw.windowHint(GlfwHint.TRANSPARENT_FRAMEBUFFER, transparentFramebuffer);
        Glfw.windowHint(GlfwHint.DECORATED, !borderless);

        // Window
        final Window window = new Window(size.x, size.y, title, vsync, shared);
        window.setFullscreen(fullscreen);
        window.setOpacity(opacity);

        if(icon != null)
            window.setIcon(icon);

        if(position != null)
            window.setPos(position.x, position.y);
        else
            window.toCenter();

        if(initialColor != null)
            window.show();

        // Context
        final Context context = new Context(window);
        context.setExitOnClose(exitOnClose);
        context.setShowWindowOnInit(showWindowOnInit);

        return context;
    }


    /** Задать размер окна */
    public ContextBuilder size(int width, int height){
        this.size.set(width, height);
        return this;
    }

    public ContextBuilder title(String title){
        this.title = title;
        return this;
    }

    /**
     * Задать иконку для окна
     * @param filepath внутренний путь до файла иконки
     */
    public ContextBuilder icon(String filepath){
        icon = filepath;
        return this;
    }


    /** Показать окно перед выполнением {@link JpizeApplication#init()} */
    public ContextBuilder showBeforeInit(float r, float g, float b){
        this.initialColor = new Color(r, g, b);
        return this;
    }

    /** Включить вертикальную синхронизацию */
    public ContextBuilder vsync(boolean vsync){
        this.vsync = vsync;
        return this;
    }

    /** Включить возможность изменять размер окна */
    public ContextBuilder resizable(boolean resizable){
        this.resizable = resizable;
        return this;
    }

    /** Войти в полноэкранный режим */
    public ContextBuilder fullscreen(){
        this.fullscreen = true;
        return this;
    }


    /** Количество выборок на пиксель для <b>SSAO</b>(super sample anti-aliasing) */
    public ContextBuilder ssaaSamples(int samples){
        this.samples = samples;
        return this;
    }

    /** Изменить непрозрачность окна
     * (0 - полностью прозрачное)
     */
    public ContextBuilder opacity(float opacity){
        this.opacity = opacity;
        return this;
    }

    public ContextBuilder sharedWindow(Window shared){
        this.shared = shared;
        return this;
    }


    /** Координаты окна (от верхнего левого угла) */
    public ContextBuilder position(int x, int y){
        this.position = new Vec2i(x, y);
        return this;
    }

    /** Остановить программу в случае закрытия окна */
    public ContextBuilder exitWhenWindowClose(boolean exitOnClose){
        this.exitOnClose = exitOnClose;
        return this;
    }

    /** Показать окно при инициализации */
    public ContextBuilder showOnInit(boolean showWindowOnInit){
        this.showWindowOnInit = showWindowOnInit;
        return this;
    }

    /** Включить альфа-канал для фреймбуфера */
    public ContextBuilder transparentFramebuffer(boolean transparentFramebuffer){
        this.transparentFramebuffer = transparentFramebuffer;
        return this;
    }

    /** Убрать рамки */
    public ContextBuilder borderless(boolean borderless){
        this.borderless = borderless;
        return this;
    }

}
