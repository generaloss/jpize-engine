package glit.physic;

import glit.math.vecmath.point.Point3f;
import glit.math.vecmath.tuple.Tuple3f;

public class BoxBody implements Cloneable{

    private final BoundingBox box;
    private final Point3f position;

    public BoxBody(BoundingBox boundingBox){
        this.box = boundingBox;
        position = new Point3f();
    }

    public BoxBody(BoxBody boxBody){
        box = boxBody.box().clone();
        position = boxBody.pos().clone();
    }


    public Tuple3f getMin(){
        return position.clone().add(box.getMin());
    }

    public Tuple3f getMax(){
        return position.clone().add(box.getMax());
    }


    public BoundingBox box(){
        return box;
    }

    public Point3f pos(){
        return position;
    }


    @Override
    public BoxBody clone(){
        return new BoxBody(this);
    }

}
