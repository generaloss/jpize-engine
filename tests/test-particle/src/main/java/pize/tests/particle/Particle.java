package pize.tests.particle;

public class Particle{

    private boolean destroyed;

    public Particle(){

    }



    public void destroy(){
        destroyed = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
