package pize.glfw.mouse.cursor;

import org.lwjgl.glfw.GLFWImage;
import pize.util.Disposable;
import pize.glfw.object.GlfwObjectLong;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class GlfwCursor extends GlfwObjectLong implements Disposable{

    public GlfwCursor(Pixmap cursorTexture){
        final GLFWImage cursorBuffer = GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture.getBuffer());

        ID = glfwCreateCursor(cursorBuffer, 0, 0);
    }

    public GlfwCursor(String filepath){
        this(PixmapIO.load(filepath));
    }


    @Override
    public void dispose(){
        glfwDestroyCursor(ID);
    }

}
