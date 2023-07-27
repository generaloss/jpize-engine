package pize.tests.voxelgame.client.renderer.particle;

import pize.graphics.texture.Texture;
import pize.graphics.util.color.Color;
import pize.math.vecmath.vector.Vec3f;

import java.util.function.Function;

public class Particle{
    
    public static final Function<Float, Float> DEFAULT_ALPHA_FUNC = time -> 1F;
    
    
    protected Texture texture;
    protected ParticleCallback initialSetup;
    protected Function<Float, Float> alphaFunc;
    protected ParticleCallback onAnimate;
    protected Color color;
    
    public Particle(){
        alphaFunc = DEFAULT_ALPHA_FUNC;
        onAnimate = instance -> {};
        color = new Color();
    }
    
    
    public Particle texture(Texture texture){
        this.texture = texture;
        return this;
    }
    
    public Particle init(ParticleCallback initialSetup){
        this.initialSetup = initialSetup;
        return this;
    }
    
    public Particle alphaFunc(Function<Float, Float> alphaFunc){
        this.alphaFunc = alphaFunc;
        return this;
    }
    
    public Particle animate(ParticleCallback onAnimate){
        this.onAnimate = onAnimate;
        return this;
    }
    
    
    public ParticleInstance createInstance(Vec3f position){
        final ParticleInstance instance = new ParticleInstance(this, position);
        initialSetup.invoke(instance);
        return instance;
    }
    
}
