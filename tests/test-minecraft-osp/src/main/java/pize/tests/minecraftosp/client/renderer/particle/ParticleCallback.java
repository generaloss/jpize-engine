package pize.tests.minecraftosp.client.renderer.particle;

@FunctionalInterface
public interface ParticleCallback{
    
    void invoke(ParticleInstance instance);
    
}
