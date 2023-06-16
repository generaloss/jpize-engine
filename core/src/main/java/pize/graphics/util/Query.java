package pize.graphics.util;

import pize.app.Disposable;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.QueryTarget;

import static org.lwjgl.opengl.GL46.*;

public class Query extends GlObject implements Disposable{
    
    private QueryTarget type;
    
    public Query(QueryTarget type){
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
    
    
    public QueryTarget getType(){
        return type;
    }
    
    public void setType(QueryTarget type){
        this.type = type;
    }
    
    
    @Override
    public void dispose(){
        glDeleteQueries(ID);
    }
    
}
