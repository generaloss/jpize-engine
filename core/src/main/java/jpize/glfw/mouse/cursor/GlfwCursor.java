package jpize.glfw.mouse.cursor;

import org.lwjgl.glfw.GLFWImage;
import jpize.util.Disposable;
import jpize.glfw.object.GlfwObjectLong;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.PixmapIO;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class GlfwCursor extends GlfwObjectLong implements Disposable{

    public GlfwCursor(PixmapRGBA cursorTexture){
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
