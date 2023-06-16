package pize.tests.voxelgame.clientserver.entity;

import pize.math.Maths;
import pize.math.util.EulerAngle;
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
import pize.tests.voxelgame.clientserver.world.World;

import java.util.ArrayList;

public abstract class Entity extends BoxBody{
    
    private final World worldOF;
    
    protected final Motion3D motion;
    protected final EulerAngle rotation;
    
    private BoxBody[] blockBoxes;
    protected boolean onGround;
    
    public Entity(BoundingBox boundingBox, World worldOF){
        super(boundingBox);
        this.worldOF = worldOF;
        
        motion = new Motion3D();
        rotation = new EulerAngle();
    }
    
    public World getWorldOf(){
        return worldOF;
    }
    
    public void update(){
        // Check is chunk loaded
        final Vec3f pos = getPosition();
        if(worldOF.getChunk(pos.xf(), pos.zf()) == null)
            return;
        
        // Update blocks around player
        blockBoxes = getBlockBoxes();
        // Update is player on ground
        onGround = isCollidedTo(BlockFace.NEGATIVE_Y);
        
        onLivingUpdate();
    }
    
    
    public abstract void onLivingUpdate();
    
    public abstract float getEyes();
    
    
    public Motion3D getMotion(){
        return motion;
    }
    
    public EulerAngle getRotation(){
        return rotation;
    }
    
    public boolean isOnGround(){
        return onGround;
    }
    
    
    protected Vec3d moveEntity(Vec3d motion){
        final Vec3d collidedMove = Collider3D.getCollidedMotion(this, motion, blockBoxes);
        getPosition().add(collidedMove);
        
        return collidedMove;
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
                    final BlockProperties block = BlockState.getProps(worldOF.getBlock(x, y, z));
                    final BlockShape shape = block.getShape();
                    
                    for(BoundingBox boundingBox: shape.getBoxes()){
                        final BoxBody box = new BoxBody(boundingBox);
                        box.getPosition().set(x, y, z);
                        
                        blockBoxes.add(box);
                    }
                }
        
        return blockBoxes.toArray(new BoxBody[0]);
    }
    
}
