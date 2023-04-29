package pize.physic;

import pize.math.vecmath.point.Point2f;
import pize.math.vecmath.tuple.Tuple2f;

public class RectBody{

    private final BoundingRect rect;
    private final Point2f position;

    public RectBody(BoundingRect rect){
        this.rect = rect;
        position = new Point2f();
    }

    public RectBody(RectBody body){
        rect = body.rect.clone();
        position = body.pos().clone();
    }


    public Tuple2f getMin(){
        return position.clone().add(rect.getMin());
    }

    public Tuple2f getMax(){
        return position.clone().add(rect.getMax());
    }


    public BoundingRect rect(){
        return rect;
    }

    public Point2f pos(){
        return position;
    }


    @Override
    public RectBody clone(){
        return new RectBody(this);
    }

}
