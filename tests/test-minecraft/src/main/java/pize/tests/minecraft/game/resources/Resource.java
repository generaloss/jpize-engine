package pize.tests.minecraft.game.resources;

import pize.app.Disposable;

public abstract class Resource<T> implements Disposable{

    private String location;

    public Resource(String location){
        this.location = location;
    }


    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }


    abstract public void loadResource();

    abstract public void reloadResource();

    abstract public boolean isLoaded();

    abstract public T getResource();

}
