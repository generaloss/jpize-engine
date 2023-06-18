package pize.tests.voxelgame.client.control;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.block.model.BlockFace;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.HEIGHT_IDX;

public class RayCast{
    
    private final Main sessionOF;
    
    private float rayLength;
    private final Vec3i selectedBlock, imaginarySelectedBlock;
    private BlockFace selectedFace;
    private boolean selected;
    private ClientLevel world;
    
    public RayCast(Main sessionOF, float length){
        this.sessionOF = sessionOF;
        
        setRayLength(length);
        selectedBlock = new Vec3i();
        imaginarySelectedBlock = new Vec3i();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void setWorld(ClientLevel world){
        this.world = world;
    }
    
    
    public void update(){
        if(world == null)
            return;
        
        final LocalPlayer player = sessionOF.getGame().getPlayer();
        final Vec3f start = player.getPosition().clone().add(0, player.getEyeHeight(), 0);
        final Vec3f dir = player.getRotation().direction();
        
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
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.x) + Math.max(step.x, 0)) / dir.x)),
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.y) + Math.max(step.y, 0)) / dir.y)),
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.z) + Math.max(step.z, 0)) / dir.z))
        );
        
        selectedBlock.set(start.xf(), start.yf(), start.zf());
        final Vec3i faceNormal = new Vec3i();
        
        selected = false;
        while(tMax.len() < rayLength){
            
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
            
            if(selectedBlock.y < 0 || selectedBlock.y > HEIGHT_IDX)
                break;
            
            final byte block = BlockState.getID(world.getBlock(selectedBlock.x, selectedBlock.y, selectedBlock.z));
            if(block != Block.AIR.ID){
                selectedFace = BlockFace.fromNormal(faceNormal.x, faceNormal.y, faceNormal.z);
                imaginarySelectedBlock.set(selectedBlock).add(selectedFace.x, selectedFace.y, selectedFace.z);
                selected = true;
                
                break;
            }
        }
        
    }
    
    
    public BlockFace getSelectedFace(){
        return selectedFace;
    }
    
    public Vec3i getSelectedBlockPosition(){
        return selectedBlock;
    }
    
    public Vec3i getImaginaryBlockPosition(){
        return imaginarySelectedBlock;
    }
    
    public float getRayLength(){
        return rayLength;
    }
    
    public void setRayLength(float rayLength){
        this.rayLength = rayLength;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
}
