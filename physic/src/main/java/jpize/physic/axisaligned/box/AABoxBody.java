package jpize.physic.axisaligned.box;

import jpize.math.vecmath.vector.Vec3f;

public class AABoxBody{

    private final AABox boundingBox;
    private final Vec3f position;
    
    public AABoxBody(){
        this(new AABox(0, 0, 0, 0, 0, 0), new Vec3f());
    }
    
    public AABoxBody(Vec3f position){
        this(new AABox(0, 0, 0, 0, 0, 0), position);
    }
    
    public AABoxBody(AABox box){
        this(box, new Vec3f());
    }
    
    public AABoxBody(AABox box, Vec3f position){
        this.boundingBox = box;
        this.position = position;
    }
    
    public AABoxBody(AABoxBody body){
        this(body.getBoundingBox().copy(), body.getPosition().copy());
    }
    

    public Vec3f getMin(){
        return position.copy().add(boundingBox.getMin());
    }

    public Vec3f getMax(){
        return position.copy().add(boundingBox.getMax());
    }


    public AABox getBoundingBox(){
        return boundingBox;
    }

    public Vec3f getPosition(){
        return position;
    }
    
    
    public boolean intersects(AABoxBody body){
        return (
            (this.getMin().x < body.getMax().x && this.getMax().x > body.getMin().x) &&
            (this.getMin().y < body.getMax().y && this.getMax().y > body.getMin().y) &&
            (this.getMin().z < body.getMax().z && this.getMax().z > body.getMin().z)
        );
    }


    public AABoxBody copy(){
        return new AABoxBody(this);
    }

}
