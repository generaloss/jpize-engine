package pize.lib.glfw.monitor;

public record GlfwMonitorWorkarea(int x, int y, int width, int height){

    @Override
    public String toString(){
        return x + ", " + y + ", " + width + ", " + height;
    }

}
