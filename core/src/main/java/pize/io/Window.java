package pize.io;

import pize.context.Disposable;
import pize.context.Resizable;
import pize.files.Resource;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

public class Window implements Disposable, Resizable{

    private final long ID;
    private int width, height, x, y, windowedLastWidth, windowedLastHeight, windowedLastX, windowedLastY;
    private boolean vsync, fullscreen, focused, resizable;
    private String title;
    
    public Window(String title, int width, int height){
        this(title, width, height, true, true, 1);
    }
    
    public Window(String title, int width, int height, boolean resizable, boolean vsync, int samples){
        this.width = width;
        this.height = height;
        
        this.resizable = resizable;
        this.vsync = vsync;

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? 1 : 0);
        glfwWindowHint(GLFW_SAMPLES, samples);

        ID = glfwCreateWindow(width, height, title, 0, 0);
        if(ID == 0)
            throw new Error("Failed to create the GLFW Window");

        Monitor monitor = MonitorManager.getPrimary();
        glfwSetWindowPos(ID, monitor.getWidth() / 2 - width / 2, monitor.getHeight() / 2 - height / 2);

        glfwMakeContextCurrent(ID);
        createCapabilities();
        glfwSwapInterval(vsync ? 1 : 0);

        glfwSetWindowFocusCallback(ID, (id, flag)->focused = flag);
        glfwSetWindowSizeCallback(ID, (id, w, h)->{
            this.width = w;
            this.height = h;
        });
        glfwSetWindowPosCallback(ID, (id, x, y)->{
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
            glfwSetWindowMonitor(ID, monitor.getId(), 0, 0, monitor.getWidth(), monitor.getHeight(), monitor.getRefreshRate());
        }else
            glfwSetWindowMonitor(ID, 0, windowedLastX, windowedLastY, windowedLastWidth, windowedLastHeight, monitor.getRefreshRate());
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
        glfwSetWindowTitle(ID, title);
    }


    @Override
    public void resize(int width, int height){
        glfwSetWindowSize(ID, width, height);
    }


    public void setPos(int x, int y){
        glfwSetWindowPos(ID, x, y);
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
        glfwSetWindowAttrib(ID, GLFW_RESIZABLE, resizable ? 1 : 0);
    }

    public void toggleResizable(){
        setResizable(!resizable);
    }


    public void setIcon(String filePath){
        try{
            BufferedImage bufferedImage = ImageIO.read(new Resource(filePath).inStream());
            Pixmap pixmap = PixmapIO.load(bufferedImage);

            GLFWImage image = GLFWImage.malloc();
            GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
            image.set(pixmap.getWidth(), pixmap.getHeight(), pixmap.getBuffer());

            iconBuffer.put(0, image);
            glfwSetWindowIcon(ID, iconBuffer);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setCursor(Cursor cursor){
        glfwSetCursor(ID, cursor != null ? cursor.getId() : 0);
    }


    public void swapBuffers(){
        glfwSwapBuffers(ID);
    }


    public void show(){
        glfwShowWindow(ID);
    }

    public void hide(){
        glfwHideWindow(ID);
    }


    public boolean closeRequest(){
        return glfwWindowShouldClose(ID);
    }

    public boolean isFocused(){
        return focused;
    }
    
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public float aspect(){
        return (float) width / height;
    }


    public long getID(){
        return ID;
    }

    @Override
    public void dispose(){
        glfwFreeCallbacks(ID);
        glfwDestroyWindow(ID);
    }

}
