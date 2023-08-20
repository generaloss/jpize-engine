package pize.lib.glfw;

public abstract class GlfwObject{

    protected long ID;

    public GlfwObject(){ }

    public GlfwObject(long ID){
        this.ID = ID;
    }

    public long getID(){
        return ID;
    }

}
