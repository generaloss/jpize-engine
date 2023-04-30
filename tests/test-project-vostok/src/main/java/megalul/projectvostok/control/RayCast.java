package megalul.projectvostok.control;

import pize.graphics.camera.Camera3D;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import megalul.projectvostok.Main;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.block.model.BlockFace;

import static megalul.projectvostok.chunk.ChunkUtils.HEIGHT_IDX;

public class RayCast{
    
    private final Main sessionOF;
    
    private float rayLength;
    private final Vec3i selectedBlock, imaginarySelectedBlock;
    private BlockFace selectedFace;
    private boolean selected;
    
    public RayCast(Main sessionOF, float length){
        this.sessionOF = sessionOF;
        
        setRayLength(length);
        selectedBlock = new Vec3i();
        imaginarySelectedBlock = new Vec3i();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public void update(){
        Camera3D cam = sessionOF.getCamera();
        Vec3f start = cam.getPos();
        Vec3f dir = cam.getRot().direction();
        
        Vec3i step = new Vec3i(
            Mathc.signum(dir.x),
            Mathc.signum(dir.y),
            Mathc.signum(dir.z)
        );
        Vec3f delta = new Vec3f(
            step.x / dir.x,
            step.y / dir.y,
            step.z / dir.z
        );
        Vec3f tMax = new Vec3f(
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.x) + Math.max(step.x, 0)) / dir.x)),
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.y) + Math.max(step.y, 0)) / dir.y)),
            Math.min(rayLength / 2, Maths.abs((Maths.frac(start.z) + Math.max(step.z, 0)) / dir.z))
        );
        
        selectedBlock.set(start.xf(), start.yf(), start.zf());
        Vec3i faceNormal = new Vec3i();
        
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
            
            byte block = BlockState.getID(sessionOF.getWorld().getBlock(selectedBlock.x, selectedBlock.y, selectedBlock.z));
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
