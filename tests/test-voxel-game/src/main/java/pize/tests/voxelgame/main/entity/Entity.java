package pize.tests.voxelgame.main.entity;

import pize.math.Maths;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.BoundingBox3;
import pize.physic.BoxBody;
import pize.physic.Collider3f;
import pize.physic.Velocity3f;
import pize.tests.voxelgame.client.block.BlockFace;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.shape.BlockCollideShape;
import pize.tests.voxelgame.main.chunk.ChunkUtils;
import pize.tests.voxelgame.main.level.Level;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Entity extends BoxBody{
    
    private Level level;
    private final EntityType<?> entityType;
    private final EulerAngles rotation;
    private final Velocity3f velocity;
    private UUID uuid;
    
    private BoxBody[] blockBoxes;
    private boolean onGround;
    
    public Entity(EntityType<?> entityType, Level level){
        super(entityType.getBoundingBox());
        
        this.level = level;
        this.entityType = entityType;
        this.rotation = new EulerAngles();
        this.velocity = new Velocity3f();
        this.uuid = UUID.randomUUID();
    }
    
    
    public Level getLevel(){
        return level;
    }
    
    public void setLevel(Level level){
        this.level = level;
    }
    
    public EntityType<?> getEntityType(){
        return entityType;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
    public Velocity3f getVelocity(){
        return velocity;
    }
    
    
    public UUID getUUID(){
        return uuid;
    }
    
    public void setUUID(UUID uuid){
        this.uuid = uuid;
    }
    
    public float getEyeHeight(){
        return getBoundingBox().getSizeY() * 0.85F;
    }
    
    
    public void tick(){
        // Check is chunk loaded
        final Vec3f pos = getPosition();
        if(level.getChunk(pos.xf(), pos.zf()) == null)
            return;
        
        // Update blocks around player
        blockBoxes = getBlockBoxes();
        // Update is player on ground
        onGround = isCollidedTo(BlockFace.NEGATIVE_Y);
    }
    
    public boolean isOnGround(){
        return onGround;
    }
    
    public boolean isCollidedTo(BlockFace face){
        return isCollidedTo(new Vec3f(face.x, face.y, face.z));
    }
    
    public boolean isCollidedTo(Vec3f direction){
        final Vec3f dir = direction.copy().nor().mul(Maths.Epsilon);
        return Collider3f.getCollidedMotion(this, dir, blockBoxes).len2() < dir.len2();
    }
    
    /** Get Bounding Boxes of blocks around Entity */
    private BoxBody[] getBlockBoxes(){
        final ArrayList<BoxBody> blockBoxes = new ArrayList<>();
        
        final Velocity3f velocity = getVelocity();
        final Vec3f min = this.getMin();
        final Vec3f max = this.getMax();
        
        final int beginX = Maths.floor(min.x - 0.5 + Math.min(0, velocity.x));
        final int beginY = Math.max(0, Math.min(ChunkUtils.HEIGHT_IDX,
            Maths.floor(min.y - 0.5 + Math.min(0, velocity.y))
        ));
        final int beginZ = Maths.floor(min.z - 0.5 + Math.min(0, velocity.z));
        
        final int endX = Maths.ceil(max.x + 0.5 + Math.max(0, velocity.x));
        final int endY = Math.max(0, Math.min(ChunkUtils.HEIGHT,
            Maths.ceil(max.y + 0.5 + Math.max(0, velocity.y))
        ));
        final int endZ = Maths.ceil(max.z + 0.5 + Math.max(0, velocity.z));
        
        for(int x = beginX; x < endX; x++)
            for(int y = beginY; y < endY; y++)
                for(int z = beginZ; z < endZ; z++){
                    final BlockProperties block = level.getBlockProps(x, y, z);
                    final BlockCollideShape shape = block.getCollideShape();
                    if(shape == null)
                        continue;
                    
                    for(BoundingBox3 boundingBox: shape.getBoxes()){
                        final BoxBody box = new BoxBody(boundingBox);
                        box.getPosition().set(x, y, z);
                        
                        blockBoxes.add(box);
                    }
                }
        
        return blockBoxes.toArray(new BoxBody[0]);
    }
    
    protected Vec3f moveEntity(Vec3f motion){
        if(blockBoxes == null)
            return null;
        
        final Vec3f collidedMove = Collider3f.getCollidedMotion(this, motion, blockBoxes);
        getPosition().add(collidedMove);
        
        return collidedMove;
    }
    
}
