package pize.io.mouse;

import pize.app.Disposable;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;
import pize.graphics.texture.Texture;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class Cursor implements Disposable{

    private final long ID;

    public Cursor(String filepath){
        final Pixmap cursorTextureData = PixmapIO.load(filepath);
        final GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTextureData.getWidth(), cursorTextureData.getHeight(), cursorTextureData.getBuffer());
        ID = glfwCreateCursor(cursorBuffer, 0, 0);
    }

    public Cursor(Texture cursorTexture){
        final GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture.getPixmap().getBuffer());
        ID = glfwCreateCursor(cursorBuffer, 0, 0);
    }

    public Cursor(Pixmap cursorTexture){
        final GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture.getBuffer());
        ID = glfwCreateCursor(cursorBuffer, 0, 0);
    }


    public long getID(){
        return ID;
    }

    @Override
    public void dispose(){
        glfwDestroyCursor(ID);
    }

}
