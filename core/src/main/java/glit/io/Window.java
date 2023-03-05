package glit.io;

import glit.context.Disposable;
import glit.context.Resizable;
import glit.files.FileHandle;
import glit.graphics.texture.Pixmap;
import glit.graphics.texture.PixmapLoader;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

public class Window implements Disposable, Resizable{

    private final long id;
    private int width, height, x, y, windowedLastWidth, windowedLastHeight, windowedLastX, windowedLastY;
    private boolean vsync, fullscreen, focused, resizable;
    private String title;

    public Window(String title, int width, int height){
        this(title, width, height, true, true);
    }

    public Window(String title, int width, int height, boolean resizable, boolean vsync){
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.vsync = vsync;

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        id = glfwCreateWindow(width, height, title, 0, 0);
        if(id == 0)
            throw new Error("Failed to create the GLFW Window");

        Monitor monitor = MonitorManager.getPrimary();
        glfwSetWindowPos(id, monitor.getWidth() / 2 - width / 2, monitor.getHeight() / 2 - height / 2);

        glfwMakeContextCurrent(id);
        createCapabilities();
        glfwSwapInterval(1);

        glfwSetWindowFocusCallback(id, (id, flag)->focused = flag);

        glfwSetWindowSizeCallback(id, (id, w, h)->{
            this.width = w;
            this.height = h;
        });

        glfwSetWindowPosCallback(id, (id, x, y)->{
            this.x = x;
            this.y = y;
        });
    }


    public boolean isFullscreen(){
        return fullscreen;
    }

    public void setFullscreen(boolean flag){
        if(flag == fullscreen)
            return;
        fullscreen = flag;

        Monitor monitor = MonitorManager.getPrimary();
        if(flag){
            windowedLastX = x;
            windowedLastY = y;
            windowedLastWidth = width;
            windowedLastHeight = height;
            glfwSetWindowMonitor(id, monitor.getId(), 0, 0, monitor.getWidth(), monitor.getHeight(), monitor.getRefreshRate());
        }else
            glfwSetWindowMonitor(id, 0, windowedLastX, windowedLastY, windowedLastWidth, windowedLastHeight, monitor.getRefreshRate());
    }

    public void toggleFullscreen(){
        setFullscreen(!fullscreen);
    }


    public boolean isVsync(){
        return vsync;
    }

    public void setVsync(boolean vsync){
        if(vsync == this.vsync)
            return;

        this.vsync = vsync;
        glfwSwapInterval(vsync ? 1 : 0);
    }

    public void toggleVsync(){
        setVsync(!vsync);
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        glfwSetWindowTitle(id, title);
    }


    @Override
    public void resize(int width, int height){
        glfwSetWindowSize(id, width, height);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public void setPos(int x, int y){
        glfwSetWindowPos(id, x, y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public boolean isResizable(){
        return resizable;
    }

    public void setResizable(boolean resizable){
        this.resizable = resizable;
        glfwSetWindowAttrib(id, GLFW_RESIZABLE, resizable ? 1 : 0);
    }

    public void toggleResizable(){
        setResizable(!resizable);
    }


    public void setIcon(String filePath){
        try{
            BufferedImage bufferedImage = ImageIO.read(new FileHandle(filePath).input());
            Pixmap pixmap = PixmapLoader.loadFrom(bufferedImage);

            GLFWImage image = GLFWImage.malloc();
            GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
            image.set(pixmap.getWidth(), pixmap.getHeight(), pixmap.getBuffer());
            pixmap.dispose();

            iconBuffer.put(0, image);
            glfwSetWindowIcon(id, iconBuffer);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setCursor(Cursor cursor){
        glfwSetCursor(id, cursor != null ? cursor.getId() : 0);
    }

    public float aspect(){
        return (float) width / height;
    }


    public void swapBuffers(){
        glfwSwapBuffers(id);
    }

    public void pollEvents(){
        glfwPollEvents();
    }


    public void show(){
        glfwShowWindow(id);
    }

    public void hide(){
        glfwHideWindow(id);
    }


    public boolean closeRequest(){
        return glfwWindowShouldClose(id);
    }

    public boolean isFocused(){
        return focused;
    }


    public long getId(){
        return id;
    }

    @Override
    public void dispose(){
        glfwFreeCallbacks(id);
        glfwDestroyWindow(id);
    }

}