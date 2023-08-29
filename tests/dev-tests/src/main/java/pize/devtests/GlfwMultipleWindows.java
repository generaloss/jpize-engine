package pize.devtests;

import org.lwjgl.opengl.GL;
import pize.Jize;
import pize.gl.Gl;
import pize.glfw.glfw.Glfw;
import pize.io.Window;
import pize.math.vecmath.vector.Vec2f;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GlfwMultipleWindows{

    public static void main(String[] args){
        final Vec2f scale = Jize.primaryMonitor().getContentScale();
        final List<Window> windows = new CopyOnWriteArrayList<>();

        for(int i = 0; i < 4; i++){
            final Window window = new Window(300, 200, "Window " + (i + 1));
            window.show();

            Gl.clearColor((i & 1), (i >> 1), (i == 1) ? 0 : 1, 0);

            float x = 100 + (i & 1)  * (300 * scale.x + 100);
            float y = 100 + (i >> 1) * (200 * scale.y + 100);
            window.setPos((int) x, (int) y);

            windows.add(i, window);
        }

        while(!windows.isEmpty()){
            Glfw.pollEvents();

            for(Window window: windows){
                window.makeCurrent();
                GL.setCapabilities(window.getCapabilities());

                if(window.closeRequest()){
                    window.dispose();
                    windows.remove(window);
                    continue;
                }

                Gl.clearColorBuffer();
                window.swapBuffers();
            }
        }

        GL.setCapabilities(null);
    }
}