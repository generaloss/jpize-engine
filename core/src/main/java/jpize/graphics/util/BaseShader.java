package jpize.graphics.util;

import jpize.util.file.Resource;
import jpize.graphics.camera.Camera;
import jpize.graphics.util.color.IColor;
import jpize.math.vecmath.matrix.Matrix4f;

public class BaseShader extends Shader{
    
    private BaseShader(String path){
        super(Resource.internal("shader/base/" + path + ".vert"), Resource.internal("shader/base/" + path + ".frag"));
    }
    
    
    public void setMatrices(Matrix4f projection, Matrix4f view){
        setUniform("u_projection", projection);
        setUniform("u_view", view);
    }
    
    public void setMatrices(Camera camera){
        setMatrices(camera.getProjection(), camera.getView());
    }
    
    
    public void setColor(IColor color){
        setUniform("u_color", color);
    }
    
    public void setColor(float r, float g, float b, float a){
        setUniform("u_color", r, g, b, a);
    }
    
    public void setColor(float r, float g, float b){
        setColor(r, g, b, 1F);
    }
    
    
    private static BaseShader pos2Color;
    
    /** Attributes: vec2 POSITION, vec4 COLOR */
    public static BaseShader getPos2Color(){
        if(pos2Color == null)
            pos2Color = new BaseShader("pos2-color");
        
        return pos2Color;
    }
    
    private static BaseShader pos3Color;
    
    /** Attributes: vec3 POSITION, vec4 COLOR */
    public static BaseShader getPos3Color(){
        if(pos3Color == null)
            pos3Color = new BaseShader("pos3-color");
        
        return pos3Color;
    }
    
    private static BaseShader pos3UColor;
    
    /** Attributes: vec3 POSITION; Uniforms: vec4 COLOR */
    public static BaseShader getPos3UColor(){
        if(pos3UColor == null)
            pos3UColor = new BaseShader("pos3-ucolor");
        
        return pos3UColor;
    }
    
    
    private static void disposeShaders(){ // Calls from ContextManager
        if(pos2Color != null)
            pos2Color.dispose();
        
        if(pos3Color != null)
            pos3Color.dispose();
        
        if(pos3UColor != null)
            pos3UColor.dispose();
    }
    
}
