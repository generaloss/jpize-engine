package pize.tests.voxelgame.clientserver.entity;

import pize.math.Maths;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.BoundingBox;
import pize.physic.BoxBody;
import pize.physic.Collider3D;
import pize.physic.Motion3D;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.model.BlockFace;
import pize.tests.voxelgame.client.block.model.BlockShape;
import pize.tests.voxelgame.clientserver.chunk.ChunkUtils;
import pize.tests.voxelgame.clientserver.level.Level;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Entity extends BoxBody{
    
    private Level level;
    private final EntityType entityType;
    private final EulerAngles rotation;
    private final Motion3D motion;
    private UUID uuid;
    
    private BoxBody[] blockBoxes;
    private boolean onGround;

    public Entity(EntityType entityType, Level level){
        super(entityType.getBoundingBox());
        
        this.level = level;
        this.entityType = entityType;
        this.rotation = new EulerAngles();
        this.motion = new Motion3D();
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
    
    public Motion3D getMotion(){
        return motion;
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
    
    protected boolean isCollidedTo(BlockFace face){
        final Vec3d motion = new Vec3d(face.x, face.y, face.z).mul(Float.MIN_VALUE);
        return Collider3D.getCollidedMotion(this, motion, blockBoxes).len2() == 0;
    }
    
    private BoxBody[] getBlockBoxes(){
        final ArrayList<BoxBody> blockBoxes = new ArrayList<>();
        
        final Motion3D motion = getMotion();
        final Vec3f min = this.getMin();
        final Vec3f max = this.getMax();
        
        final int beginX = Maths.floor(min.x + Math.max(0, motion.x)) - 1;
        final int beginY = Math.max(0, Math.min(ChunkUtils.HEIGHT_IDX, Maths.floor(min.y + Math.max(0, motion.y)) - 3));
        final int beginZ = Maths.floor(min.z + Math.max(0, motion.z)) - 1;
        final int endX = Maths.ceil(max.x + Math.min(0, motion.x)) + 1;
        final int endY = Math.max(0, Math.min(ChunkUtils.HEIGHT_IDX, Maths.ceil(max.y + Math.min(0, motion.y)) + 1));
        final int endZ = Maths.ceil(max.z + Math.min(0, motion.z)) + 1;
        
        for(int x = beginX; x < endX; x++)
            for(int y = beginY; y < endY; y++)
                for(int z = beginZ; z < endZ; z++){
                    final BlockProperties block = BlockState.getProps(level.getBlock(x, y, z));
                    final BlockShape shape = block.getShape();
                    
                    for(BoundingBox boundingBox: shape.getBoxes()){
                        final BoxBody box = new BoxBody(boundingBox);
                        box.getPosition().set(x, y, z);
                        
                        blockBoxes.add(box);
                    }
                }
        
        return blockBoxes.toArray(new BoxBody[0]);
    }
    
    protected Vec3d moveEntity(Vec3d motion){
        if(blockBoxes == null)
            return null;
        
        final Vec3d collidedMove = Collider3D.getCollidedMotion(this, motion, blockBoxes);
        getPosition().add(collidedMove);
        
        return collidedMove;
    }
    
}
