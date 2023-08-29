package jpize.gl.query;

import jpize.util.Disposable;
import jpize.gl.GlObject;

import static org.lwjgl.opengl.GL46.*;

public class GlQuery extends GlObject implements Disposable{
    
    private GlQueryTarget type;
    
    public GlQuery(GlQueryTarget type){
        super(glGenQueries());
        this.type = type;
    }
    
    public void begin(){
        glBeginQuery(type.GL, ID);
    }
    
    public void end(){
        glEndQuery(type.GL);
    }


    public GlQueryTarget getType(){
        return type;
    }

    public void setType(GlQueryTarget type){
        this.type = type;
    }


    public int getResult(){
        return glGetQueryObjecti(ID, GL_QUERY_RESULT);
    }

    public boolean isResultAvailable(){
        return glGetQueryObjecti(ID, GL_QUERY_RESULT_AVAILABLE) == GL_TRUE;
    }

    public int waitForResult(){
        while(!isResultAvailable());
        return getResult();
    }


    @Override
    public void dispose(){
        glDeleteQueries(ID);
    }
    
}
