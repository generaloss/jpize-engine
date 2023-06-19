package pize.tests.voxelgame.client.entity.model;

import pize.Pize;
import pize.math.Mathc;
import pize.math.vecmath.vector.Vec3d;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.clientserver.entity.Player;

public class PlayerModel extends HumanoidModel{
    
    private static final float t = 1 / 64F; // Pixel size on texture
    private static final float w = 1.8F / 32; // Pixel size in world
    
    
    private final ModelPart jacket, hat, leftPants, rightPants, leftSleeve, rightSleeve;
    
    public PlayerModel(Player player){
        super(player);
        
        final float scale = 1 + w;
        
        hat = new ModelPart(new BoxBuilder(-4 * w, 0 * w, -4 * w,  4 * w, 8 * w, 4 * w)
            .nx(1, 1, 1, 1, 56 * t, 8  * t, 64 * t, 16 * t)
            .px(1, 1, 1, 1, 40 * t, 8  * t, 48 * t, 16 * t)
            .ny(1, 1, 1, 1, 48 * t, 0  * t, 56 * t, 8  * t)
            .py(1, 1, 1, 1, 40 * t, 0  * t, 48 * t, 8  * t)
            .pz(1, 1, 1, 1, 48 * t, 8  * t, 56 * t, 16 * t)
            .nz(1, 1, 1, 1, 32 * t, 8  * t, 40 * t, 16 * t)
            .end());
        hat.setParent(head);
        hat.getPose().getScale().set(scale * 1.05);
        
        jacket = new ModelPart(new BoxBuilder(-2 * w, -6 * w, -4 * w,  2 * w, 6 * w, 4 * w)
            .nx(1, 1, 1, 1, 32 * t, 36 * t, 40 * t, 48 * t)
            .px(1, 1, 1, 1, 20 * t, 36 * t, 28 * t, 48 * t)
            .ny(1, 1, 1, 1, 28 * t, 32 * t, 36 * t, 36 * t)
            .py(1, 1, 1, 1, 20 * t, 32 * t, 28 * t, 36 * t)
            .pz(1, 1, 1, 1, 28 * t, 36 * t, 32 * t, 48 * t)
            .nz(1, 1, 1, 1, 16 * t, 36 * t, 20 * t, 48 * t)
        .end());
        jacket.setParent(torso);
        jacket.getPose().getScale().set(scale * 1.04);
        
        leftPants = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 52 * t, 16 * t, 64 * t)
            .px(1, 1, 1, 1, 4  * t, 52 * t, 8  * t, 64 * t)
            .ny(1, 1, 1, 1, 8  * t, 48 * t, 12 * t, 52 * t)
            .py(1, 1, 1, 1, 4  * t, 48 * t, 8  * t, 52 * t)
            .pz(1, 1, 1, 1, 8  * t, 52 * t, 12 * t, 64 * t)
            .nz(1, 1, 1, 1, 0  * t, 52 * t, 4  * t, 64 * t)
        .end());
        leftPants.setParent(leftLeg);
        leftPants.getPose().getScale().set(scale * 1.03);
        
        rightPants = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 36 * t, 16 * t, 48 * t)
            .px(1, 1, 1, 1, 4  * t, 36 * t, 8  * t, 48 * t)
            .ny(1, 1, 1, 1, 8  * t, 32 * t, 12 * t, 36 * t)
            .py(1, 1, 1, 1, 4  * t, 32 * t, 8  * t, 36 * t)
            .pz(1, 1, 1, 1, 8  * t, 36 * t, 12 * t, 48 * t)
            .nz(1, 1, 1, 1, 0  * t, 36 * t, 4  * t, 48 * t)
        .end());
        rightPants.setParent(rightLeg);
        rightPants.getPose().getScale().set(scale * 1.02);
        
        leftSleeve = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 60 * t, 52 * t, 64 * t, 64 * t)
            .px(1, 1, 1, 1, 52 * t, 52 * t, 56 * t, 64 * t)
            .ny(1, 1, 1, 1, 56 * t, 48 * t, 60 * t, 52 * t)
            .py(1, 1, 1, 1, 52 * t, 48 * t, 56 * t, 52 * t)
            .pz(1, 1, 1, 1, 56 * t, 52 * t, 60 * t, 64 * t)
            .nz(1, 1, 1, 1, 48 * t, 52 * t, 52 * t, 64 * t)
        .end());
        leftSleeve.setParent(leftHand);
        leftSleeve.getPose().getScale().set(scale * 1.01);
        
        rightSleeve = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 52 * t, 36 * t, 56 * t, 48 * t)
            .px(1, 1, 1, 1, 44 * t, 36 * t, 48 * t, 48 * t)
            .ny(1, 1, 1, 1, 48 * t, 32 * t, 52 * t, 36 * t)
            .py(1, 1, 1, 1, 44 * t, 32 * t, 48 * t, 36 * t)
            .pz(1, 1, 1, 1, 48 * t, 36 * t, 52 * t, 48 * t)
            .nz(1, 1, 1, 1, 40 * t, 36 * t, 44 * t, 48 * t)
        .end());
        rightSleeve.setParent(rightHand);
        rightSleeve.getPose().getScale().set(scale * 1.01);
    }
    
    
    public void render(GameCamera camera){
        super.render(camera);
        
        jacket.render(camera, shader, "u_model");
        hat.render(camera, shader, "u_model");
        leftPants.render(camera, shader, "u_model");
        rightPants.render(camera, shader, "u_model");
        leftSleeve.render(camera, shader, "u_model");
        rightSleeve.render(camera, shader, "u_model");
    }
    
    
    private float time = 0;
    
    public void animate(){
        torso.getPosition().set(player.getPosition());
        head .getPosition().set(player.getPosition());
        
        torso.getRotation().yaw += (-player.getRotation().yaw - torso.getRotation().yaw) * Pize.getDt() * 4;
        
        head.getRotation().yaw = -player.getRotation().yaw;
        head.getRotation().pitch = player.getRotation().pitch;
        
        if(player.isSneaking())
            torso.getPosition().y -= 1 * w;
        
        final Vec3d motion = this.player.getMotion();
        if(motion.len2() > 10E-5){
            
            final double animationSpeed;
            if(player.isSprinting())
                animationSpeed = 6;
            else if(player.isSneaking())
                animationSpeed = 2;
            else
                animationSpeed = 4;
            
            time += Pize.getUpdateDt() * animationSpeed;
            
            rightHand.getRotation().pitch = 45 * Mathc.sin(time);
            leftHand.getRotation().pitch = -45 * Mathc.sin(time);
            rightLeg.getRotation().pitch = -45 * Mathc.sin(time);
            leftLeg.getRotation().pitch = 45 * Mathc.sin(time);
        }else{
            rightHand.getRotation().pitch -= rightHand.getRotation().pitch / 10;
            leftHand.getRotation().pitch  -= leftHand.getRotation().pitch  / 10;
            rightLeg.getRotation().pitch  -= rightLeg.getRotation().pitch  / 10;
            leftLeg.getRotation().pitch   -= leftLeg.getRotation().pitch   / 10;
            
            time = 0;
        }
    }
    
    
    public void dispose(){
        super.dispose();
    }
    
}
