package pize.tests.minecraftosp.client.renderer.particle;

import pize.Jize;
import pize.graphics.texture.Texture;
import pize.math.Maths;

public enum Particles{
    
    BLOCK_BREAK(new Particle()
        .init(instance->{
            instance.size = Maths.random(0.05F, 0.25F);
            instance.velocity.set(Maths.random(-0.05F, 0.05F), 0, Maths.random(-0.05F, 0.05F));
        })
        .texture(new Texture("texture/block/planks.png"))
        .alphaFunc(time->1 - time)
        .animate(instance->{
            instance.velocity.y -= Jize.getDt() * 0.5;
            instance.velocity.mul(0.95);
            instance.position.add(instance.velocity);
        })
    );
    
    
    private final Particle particle;
    
    Particles(Particle particle){
        this.particle = particle;
    }
    
    public Particle getParticle(){
        return particle;
    }
    
}
