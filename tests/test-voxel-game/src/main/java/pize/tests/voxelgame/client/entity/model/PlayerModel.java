package pize.tests.voxelgame.client.entity.model;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.math.Mathc;
import pize.math.vecmath.vector.Vec3d;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.clientserver.entity.Entity;

public class PlayerModel{
    
    private static final float t = 1 / 64F; // Pixel size on texture
    private static final float w = 1.8F / 32; // Pixel size in world
    
    
    private final Entity entityOF;
    private final ModelPart torso, head, leftLeg, rightLeg, leftHand, rightHand;
    private final Shader shader;
    private final Texture skinTexture;
    
    public PlayerModel(Entity entityOF){
        this.entityOF = entityOF;
        
        shader = new Shader(new Resource("shader/model.vert"), new Resource("shader/model.frag"));
        skinTexture = new Texture("texture/skin1.png");
        
        torso = new ModelPart(new BoxBuilder(-2 * w, -6 * w, -4 * w,  2 * w, 6 * w, 4 * w)
            .nx(1, 1, 1, 1, 32 * t, 20 * t, 40 * t, 32 * t)
            .px(1, 1, 1, 1, 20 * t, 20 * t, 28 * t, 32 * t)
            .ny(1, 1, 1, 1, 28 * t, 16 * t, 36 * t, 20 * t)
            .py(1, 1, 1, 1, 20 * t, 16 * t, 28 * t, 20 * t)
            .pz(1, 1, 1, 1, 28 * t, 20 * t, 32 * t, 32 * t)
            .nz(1, 1, 1, 1, 16 * t, 20 * t, 20 * t, 32 * t)
        .end());
        torso.setInitialPose(0, 18 * w, 0);
        
        head = new ModelPart(new BoxBuilder(-4 * w, 0 * w, -4 * w,  4 * w, 8 * w, 4 * w)
            .nx(1, 1, 1, 1, 24 * t, 8  * t, 32 * t, 16 * t)
            .px(1, 1, 1, 1, 8  * t, 8  * t, 16 * t, 16 * t)
            .ny(1, 1, 1, 1, 16 * t, 0  * t, 24 * t, 8  * t)
            .py(1, 1, 1, 1, 8  * t, 0  * t, 16 * t, 8  * t)
            .pz(1, 1, 1, 1, 16 * t, 8  * t, 24 * t, 16 * t)
            .nz(1, 1, 1, 1, 0  * t, 8  * t, 8  * t, 16 * t)
        .end());
        head.setInitialPose(0, 24 * w, 0);
        
        leftLeg = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 28 * t, 52 * t, 32 * t, 64 * t)
            .px(1, 1, 1, 1, 20 * t, 52 * t, 24 * t, 64 * t)
            .ny(1, 1, 1, 1, 24 * t, 48 * t, 28 * t, 52 * t)
            .py(1, 1, 1, 1, 20 * t, 48 * t, 24 * t, 52 * t)
            .pz(1, 1, 1, 1, 24 * t, 52 * t, 28 * t, 64 * t)
            .nz(1, 1, 1, 1, 16 * t, 52 * t, 20 * t, 64 * t)
        .end());
        leftLeg.setParent(torso);
        leftLeg.setInitialPose(0, 10 * w, 2 * w);
        
        rightLeg = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 20 * t, 16 * t, 32 * t)
            .px(1, 1, 1, 1, 4  * t, 20 * t, 8  * t, 32 * t)
            .ny(1, 1, 1, 1, 8  * t, 16 * t, 12 * t, 20 * t)
            .py(1, 1, 1, 1, 4  * t, 16 * t, 8  * t, 20 * t)
            .pz(1, 1, 1, 1, 8  * t, 20 * t, 12 * t, 32 * t)
            .nz(1, 1, 1, 1, 0  * t, 20 * t, 4  * t, 32 * t)
        .end());
        rightLeg.setParent(torso);
        rightLeg.setInitialPose(0, 10 * w, -2 * w);
        
        leftHand = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 44 * t, 52 * t, 48 * t, 64 * t)
            .px(1, 1, 1, 1, 36 * t, 52 * t, 40 * t, 64 * t)
            .ny(1, 1, 1, 1, 40 * t, 48 * t, 44 * t, 52 * t)
            .py(1, 1, 1, 1, 36 * t, 48 * t, 40 * t, 52 * t)
            .pz(1, 1, 1, 1, 40 * t, 52 * t, 44 * t, 64 * t)
            .nz(1, 1, 1, 1, 32 * t, 52 * t, 36 * t, 64 * t)
        .end());
        leftHand.setParent(torso);
        leftHand.setInitialPose(0, 22 * w, 6 * w);
        
        rightHand = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 52 * t, 20 * t, 56 * t, 32 * t)
            .px(1, 1, 1, 1, 44 * t, 20 * t, 48 * t, 32 * t)
            .ny(1, 1, 1, 1, 48 * t, 16 * t, 52 * t, 20 * t)
            .py(1, 1, 1, 1, 44 * t, 16 * t, 48 * t, 20 * t)
            .pz(1, 1, 1, 1, 48 * t, 20 * t, 52 * t, 32 * t)
            .nz(1, 1, 1, 1, 40 * t, 20 * t, 44 * t, 32 * t)
        .end());
        rightHand.setParent(torso);
        rightHand.setInitialPose(0, 22 * w, -6 * w);
    }
    
    public Entity getEntityOf(){
        return entityOF;
    }
    
    
    public void render(GameCamera camera){
        if(camera == null)
            return;
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        shader.setUniform("u_texture", skinTexture);
        
        torso.render(camera, shader, "u_model");
        head.render(camera, shader, "u_model");
        leftLeg.render(camera, shader, "u_model");
        rightLeg.render(camera, shader, "u_model");
        leftHand.render(camera, shader, "u_model");
        rightHand.render(camera, shader, "u_model");
    }
    
    
    private float time = 0;
    
    public void animate(){
        torso.getPosition().set(entityOF.getPosition());
        head .getPosition().set(entityOF.getPosition());
        
        torso.getRotation().yaw += (-entityOF.getRotation().yaw - torso.getRotation().yaw) * Pize.getDt() * 4;
        
        head.getRotation().yaw = -entityOF.getRotation().yaw;
        head.getRotation().pitch = entityOF.getRotation().pitch;
        
        //final LocalPlayer player = (LocalPlayer) entityOF;
        //if(player.isSneaking())
        //    torso.getPosition().y -= 1 * w;
        
        
        final Vec3d motion = entityOF.getMotion();
        if(motion.len2() > 10E-5){
            time += Pize.getDt();
            final double animationSpeed = 5;
            
            rightHand.getRotation().pitch = 45 * Mathc.sin(time * animationSpeed);
            leftHand.getRotation().pitch = -45 * Mathc.sin(time * animationSpeed);
            rightLeg.getRotation().pitch = -45 * Mathc.sin(time * animationSpeed);
            leftLeg.getRotation().pitch = 45 * Mathc.sin(time * animationSpeed);
        }else{
            rightHand.getRotation().pitch -= rightHand.getRotation().pitch / 10;
            leftHand.getRotation().pitch  -= leftHand.getRotation().pitch  / 10;
            rightLeg.getRotation().pitch  -= rightLeg.getRotation().pitch  / 10;
            leftLeg.getRotation().pitch   -= leftLeg.getRotation().pitch   / 10;
            
            time = 0;
        }
    }
    
    
    public void dispose(){
        torso.getMesh().dispose();
        head.getMesh().dispose();
        leftLeg.getMesh().dispose();
        rightLeg.getMesh().dispose();
        leftHand.getMesh().dispose();
        rightHand.getMesh().dispose();
        
        shader.dispose();
        skinTexture.dispose();
    }
    
}
