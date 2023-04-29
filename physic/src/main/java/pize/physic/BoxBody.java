package pize.physic;

import pize.math.vecmath.point.Point3f;
import pize.math.vecmath.tuple.Tuple3f;

public class BoxBody implements Cloneable{

    private final BoundingBox boundingBox;
    private final Point3f position;

    public BoxBody(BoundingBox boundingBox){
        this.boundingBox = boundingBox;
        position = new Point3f();
    }

    public BoxBody(BoxBody boxBody){
        boundingBox = boxBody.getBoundingBox().clone();
        position = boxBody.getPosition().clone();
    }


    public Tuple3f getMin(){
        return position.clone().add(boundingBox.getMin());
    }

    public Tuple3f getMax(){
        return position.clone().add(boundingBox.getMax());
    }


    public BoundingBox getBoundingBox(){
        return boundingBox;
    }

    public Point3f getPosition(){
        return position;
    }


    @Override
    public BoxBody clone(){
        return new BoxBody(this);
    }

}
