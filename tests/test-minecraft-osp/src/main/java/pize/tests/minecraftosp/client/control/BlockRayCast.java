package pize.tests.minecraftosp.client.control;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import pize.physic.Ray3f;
import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.entity.LocalPlayer;
import pize.tests.minecraftosp.client.level.ClientLevel;
import pize.tests.minecraftosp.main.Dir;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;

public class BlockRayCast{
    
    private final Minecraft session;
    
    private final Ray3f ray;
    private float length;
    private final Vec3i selectedBlock, imaginarySelectedBlock;
    private BlockProps selectedBlockProps;
    private Dir selectedFace;
    private boolean selected;
    private ClientLevel level;
    
    public BlockRayCast(Minecraft session, float length){
        this.session = session;
        
        ray = new Ray3f();
        setLength(length);
        
        selectedBlock = new Vec3i();
        imaginarySelectedBlock = new Vec3i();
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    public void setLevel(ClientLevel level){
        this.level = level;
    }
    
    
    public void update(){
        if(level == null)
            return;
        
        // Update ray
        final LocalPlayer player = session.getGame().getPlayer();
        ray.getDirection().set(player.getRotation().getDirection());
        ray.getOrigin().set(player.getLerpPosition().copy().add(0, player.getEyeHeight(), 0));
        
        // Get pos, dir, len
        final Vec3f pos = ray.getOrigin();
        final Vec3f dir = ray.getDirection();
        
        // ...
        final Vec3i step = new Vec3i(
            Mathc.signum(dir.x),
            Mathc.signum(dir.y),
            Mathc.signum(dir.z)
        );
        final Vec3f delta = new Vec3f(
            step.x / dir.x,
            step.y / dir.y,
            step.z / dir.z
        );
        final Vec3f tMax = new Vec3f(
            Math.min(length / 2, Maths.abs((Math.max(step.x, 0) - Maths.frac(pos.x)) / dir.x)),
            Math.min(length / 2, Maths.abs((Math.max(step.y, 0) - Maths.frac(pos.y)) / dir.y)),
            Math.min(length / 2, Maths.abs((Math.max(step.z, 0) - Maths.frac(pos.z)) / dir.z))
        );
        
        selectedBlock.set(pos.xf(), pos.yf(), pos.zf());
        final Vec3i faceNormal = new Vec3i();
        
        selected = false;
        while(tMax.len() < length){
            
            if(tMax.x < tMax.y){
                if(tMax.x < tMax.z){
                    tMax.x += delta.x;
                    selectedBlock.x += step.x;
                    faceNormal.set(-step.x, 0, 0);
                }else{
                    tMax.z += delta.z;
                    selectedBlock.z += step.z;
                    faceNormal.set(0, 0, -step.z);
                }
            }else{
                if(tMax.y < tMax.z){
                    tMax.y += delta.y;
                    selectedBlock.y += step.y;
                    faceNormal.set(0, -step.y, 0);
                }else{
                    tMax.z += delta.z;
                    selectedBlock.z += step.z;
                    faceNormal.set(0, 0, -step.z);
                }
            }
            
            if(selectedBlock.y < 0 || selectedBlock.y > ChunkUtils.HEIGHT_IDX)
                break;
            
            final short blockData = level.getBlockState(selectedBlock.x, selectedBlock.y, selectedBlock.z);
            final BlockProps block = BlockData.getProps(blockData);

            if(!block.isEmpty() && block.getID() != Blocks.VOID_AIR.getID() && block.getCursor() != null){
                if(block.isSolid()){
                    selectedFace = Dir.fromNormal(faceNormal.x, faceNormal.y, faceNormal.z);
                    selectedBlockProps = block;
                    imaginarySelectedBlock.set(selectedBlock).add(selectedFace.getNormal());
                    selected = true;
                    
                    break;
                }else{
                    final BlockCursor shape = block.getCursor();
                    // final float[] vertices = shape.getMesh().getIndexedVertices();
                    // if(ray.intersects(vertices)){
                    //     selectedFace = Direction.fromNormal(faceNormal.x, faceNormal.y, faceNormal.z);
                    //     selectedBlockProperties = block;
                    //     selectedBlockState = blockState;
                    //     imaginarySelectedBlock.set(selectedBlock).add(selectedFace.getNormal());
                    //     selected = true;
                    //
                    //     break;
                    // }
                }
            }
        }
        
    }
    
    
    public Dir getSelectedFace(){
        return selectedFace;
    }
    
    public Vec3i getSelectedBlockPosition(){
        return selectedBlock;
    }

    public BlockProps getSelectedBlockProps(){
        return selectedBlockProps;
    }
    
    public Vec3i getImaginaryBlockPosition(){
        return imaginarySelectedBlock;
    }
    
    public void setLength(float length){
        this.length = length;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
}
