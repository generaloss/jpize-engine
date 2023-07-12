package pize.tests.voxelgame.client.entity.model;

import pize.files.Resource;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.tests.voxelgame.client.entity.AbstractClientPlayer;
import pize.tests.voxelgame.main.entity.Player;
import pize.tests.voxelgame.client.control.camera.GameCamera;

public class HumanoidModel{
    
    private static final float t = 1 / 64F; // Pixel size on texture
    private static final float w = 1.8F / 32; // Pixel size in world
    
    
    protected final AbstractClientPlayer player;
    protected final ModelPart torso, head, leftLeg, rightLeg, leftArm, rightArm;
    protected final Shader shader;
    protected final Texture skinTexture;
    
    public HumanoidModel(AbstractClientPlayer player){
        this.player = player;
        
        shader = new Shader(new Resource("shader/level/entity/model.vert"), new Resource("shader/level/entity/model.frag"));
        final int skinID = (Math.abs(player.getName().hashCode()) % 20 + 1);
        skinTexture = new Texture("texture/skin/skin" + skinID + ".png");
        
        head = new ModelPart(new BoxBuilder(-4 * w, 0 * w, -4 * w,  4 * w, 8 * w, 4 * w)
            .nx(1, 1, 1, 1, 24 * t, 8  * t, 32 * t, 16 * t)
            .px(1, 1, 1, 1, 8  * t, 8  * t, 16 * t, 16 * t)
            .ny(1, 1, 1, 1, 16 * t, 0  * t, 24 * t, 8  * t)
            .py(1, 1, 1, 1, 8  * t, 0  * t, 16 * t, 8  * t)
            .pz(1, 1, 1, 1, 16 * t, 8  * t, 24 * t, 16 * t)
            .nz(1, 1, 1, 1, 0  * t, 8  * t, 8  * t, 16 * t)
            .end());
        head.setInitialPose(0, 24 * w, 0);
        
        torso = new ModelPart(new BoxBuilder(-2 * w, -6 * w, -4 * w,  2 * w, 6 * w, 4 * w)
            .nx(1, 1, 1, 1, 32 * t, 20 * t, 40 * t, 32 * t)
            .px(1, 1, 1, 1, 20 * t, 20 * t, 28 * t, 32 * t)
            .ny(1, 1, 1, 1, 28 * t, 16 * t, 36 * t, 20 * t)
            .py(1, 1, 1, 1, 20 * t, 16 * t, 28 * t, 20 * t)
            .pz(1, 1, 1, 1, 28 * t, 20 * t, 32 * t, 32 * t)
            .nz(1, 1, 1, 1, 16 * t, 20 * t, 20 * t, 32 * t)
            .end());
        torso.setInitialPose(0, 18 * w, 0);
        
        leftLeg = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 28 * t, 52 * t, 32 * t, 64 * t)
            .px(1, 1, 1, 1, 20 * t, 52 * t, 24 * t, 64 * t)
            .ny(1, 1, 1, 1, 24 * t, 48 * t, 28 * t, 52 * t)
            .py(1, 1, 1, 1, 20 * t, 48 * t, 24 * t, 52 * t)
            .pz(1, 1, 1, 1, 24 * t, 52 * t, 28 * t, 64 * t)
            .nz(1, 1, 1, 1, 16 * t, 52 * t, 20 * t, 64 * t)
            .end());
        leftLeg.setParent(torso);
        leftLeg.setInitialPose(0, -8 * w, 2 * w);
        
        rightLeg = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 20 * t, 16 * t, 32 * t)
            .px(1, 1, 1, 1, 4  * t, 20 * t, 8  * t, 32 * t)
            .ny(1, 1, 1, 1, 8  * t, 16 * t, 12 * t, 20 * t)
            .py(1, 1, 1, 1, 4  * t, 16 * t, 8  * t, 20 * t)
            .pz(1, 1, 1, 1, 8  * t, 20 * t, 12 * t, 32 * t)
            .nz(1, 1, 1, 1, 0  * t, 20 * t, 4  * t, 32 * t)
            .end());
        rightLeg.setParent(torso);
        rightLeg.setInitialPose(0, -8 * w, -2 * w);
        
        leftArm = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 44 * t, 52 * t, 48 * t, 64 * t)
            .px(1, 1, 1, 1, 36 * t, 52 * t, 40 * t, 64 * t)
            .ny(1, 1, 1, 1, 40 * t, 48 * t, 44 * t, 52 * t)
            .py(1, 1, 1, 1, 36 * t, 48 * t, 40 * t, 52 * t)
            .pz(1, 1, 1, 1, 40 * t, 52 * t, 44 * t, 64 * t)
            .nz(1, 1, 1, 1, 32 * t, 52 * t, 36 * t, 64 * t)
            .end());
        leftArm.setParent(torso);
        leftArm.setInitialPose(0, 4 * w, 6 * w);
        
        rightArm = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 52 * t, 20 * t, 56 * t, 32 * t)
            .px(1, 1, 1, 1, 44 * t, 20 * t, 48 * t, 32 * t)
            .ny(1, 1, 1, 1, 48 * t, 16 * t, 52 * t, 20 * t)
            .py(1, 1, 1, 1, 44 * t, 16 * t, 48 * t, 20 * t)
            .pz(1, 1, 1, 1, 48 * t, 20 * t, 52 * t, 32 * t)
            .nz(1, 1, 1, 1, 40 * t, 20 * t, 44 * t, 32 * t)
            .end());
        rightArm.setParent(torso);
        rightArm.setInitialPose(0, 4 * w, -6 * w);
    }
    
    public Player getPlayer(){
        return player;
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
        leftArm.render(camera, shader, "u_model");
        rightArm.render(camera, shader, "u_model");
    }
    
    
    public void dispose(){
        torso.getMesh().dispose();
        head.getMesh().dispose();
        leftLeg.getMesh().dispose();
        rightLeg.getMesh().dispose();
        leftArm.getMesh().dispose();
        rightArm.getMesh().dispose();
        
        shader.dispose();
        skinTexture.dispose();
    }
    
}
