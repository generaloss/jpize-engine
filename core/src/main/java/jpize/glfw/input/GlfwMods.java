package jpize.glfw.input;

public class GlfwMods{

    private int mods;

    public int getMods(){
        return mods;
    }

    public void setMods(int mods){
        this.mods = mods;
    }

    public boolean has(GlfwMod mod){
        return ((mods >> (mod.GLFW - 1)) & 0b1) == 1;
    }

}
