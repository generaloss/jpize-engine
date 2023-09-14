package jpize.physic;

import jpize.math.vecmath.vector.Vec3f;

public class BoxBody{

    private final AxisAlignedBox boundingBox;
    private final Vec3f position;
    
    public BoxBody(){
        this(new AxisAlignedBox(0, 0, 0, 0, 0, 0), new Vec3f());
    }
    
    public BoxBody(Vec3f position){
        this(new AxisAlignedBox(0, 0, 0, 0, 0, 0), position);
    }
    
    public BoxBody(AxisAlignedBox box){
        this(box, new Vec3f());
    }
    
    public BoxBody(AxisAlignedBox box, Vec3f position){
        this.boundingBox = box;
        this.position = position;
    }
    
    public BoxBody(BoxBody body){
        this(body.getBoundingBox().copy(), body.getPosition().copy());
    }
    

    public Vec3f getMin(){
        return position.copy().add(boundingBox.getMin());
    }

    public Vec3f getMax(){
        return position.copy().add(boundingBox.getMax());
    }


    public AxisAlignedBox getBoundingBox(){
        return boundingBox;
    }

    public Vec3f getPosition(){
        return position;
    }
    
    
    public boolean intersects(BoxBody body){
        return (
            (this.getMin().x < body.getMax().x && this.getMax().x > body.getMin().x) &&
            (this.getMin().y < body.getMax().y && this.getMax().y > body.getMin().y) &&
            (this.getMin().z < body.getMax().z && this.getMax().z > body.getMin().z)
        );
    }


    public BoxBody copy(){
        return new BoxBody(this);
    }

}
