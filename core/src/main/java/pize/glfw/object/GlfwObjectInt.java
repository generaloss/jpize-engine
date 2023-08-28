package pize.glfw.object;

public abstract class GlfwObjectInt{

    protected int ID;

    public GlfwObjectInt(){ }

    public GlfwObjectInt(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

}
