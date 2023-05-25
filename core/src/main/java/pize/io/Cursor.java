package pize.io;

import pize.activity.Disposable;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;
import pize.graphics.texture.Texture;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class Cursor implements Disposable{

    private final long id;

    public Cursor(String filepath){
        Pixmap cursorTextureData = PixmapIO.load(filepath);
        GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTextureData.getWidth(), cursorTextureData.getHeight(), cursorTextureData.getBuffer());
        id = glfwCreateCursor(cursorBuffer, 0, 0);
    }

    public Cursor(Texture cursorTexture){
        GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture.getPixmap().getBuffer());
        id = glfwCreateCursor(cursorBuffer, 0, 0);
    }

    public Cursor(Pixmap cursorTexture){
        GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture.getBuffer());
        id = glfwCreateCursor(cursorBuffer, 0, 0);
    }


    public long getId(){
        return id;
    }

    @Override
    public void dispose(){
        glfwDestroyCursor(id);
    }

}
