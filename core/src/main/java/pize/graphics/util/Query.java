package pize.graphics.util;

import pize.app.Disposable;
import pize.lib.gl.GlObject;
import pize.lib.gl.glenum.GlQueryTarget;

import static org.lwjgl.opengl.GL46.*;

public class Query extends GlObject implements Disposable{
    
    private GlQueryTarget type;
    
    public Query(GlQueryTarget type){
        super(glGenQueries());
        this.type = type;
    }
    
    public void begin(){
        glBeginQuery(type.GL, ID);
    }
    
    public void end(){
        glEndQuery(type.GL);
    }
    
    
    public boolean isResultAvailable(){
        return glGetQueryObjecti(ID, GL_QUERY_RESULT_AVAILABLE) == GL_TRUE;
    }
    
    public int getResult(){
        return glGetQueryObjecti(ID, GL_QUERY_RESULT);
    }
    
    public int waitForResult(){
        while(!isResultAvailable());
        return getResult();
    }
    
    
    public GlQueryTarget getType(){
        return type;
    }
    
    public void setType(GlQueryTarget type){
        this.type = type;
    }
    
    
    @Override
    public void dispose(){
        glDeleteQueries(ID);
    }
    
}
