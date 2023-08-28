package pize.glfw.object;

public abstract class GlfwObjectLong{

    protected long ID;

    public GlfwObjectLong(){ }

    public GlfwObjectLong(long ID){
        this.ID = ID;
    }

    public long getID(){
        return ID;
    }

}
