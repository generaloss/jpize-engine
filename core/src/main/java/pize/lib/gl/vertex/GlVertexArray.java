package pize.lib.gl.vertex;

import pize.lib.gl.GlObject;
import pize.lib.gl.tesselation.GlPrimitive;
import pize.lib.gl.type.GlIndexType;

import static org.lwjgl.opengl.GL33.*;

public class GlVertexArray extends GlObject{

    public GlVertexArray(){
        super(glGenVertexArrays());
        bind();
    }


    public void drawArrays(int verticesNum){
        drawArrays(verticesNum, GlPrimitive.TRIANGLES);
    }

    public void drawArrays(int verticesNum, GlPrimitive mode){
        bind();
        glDrawArrays(mode.GL, 0, verticesNum);
    }


    public void drawElements(int indicesNum){
        drawElements(indicesNum, GlPrimitive.TRIANGLES);
    }

    public void drawElements(int indicesNum, GlPrimitive mode){
        drawElements(indicesNum, mode, GlIndexType.UNSIGNED_INT);
    }

    public void drawElements(int indicesNum, GlPrimitive mode, GlIndexType indexType){
        bind();
        glDrawElements(mode.GL, indicesNum, indexType.GL, 0);
    }
    
    public void bind(){
        glBindVertexArray(ID);
    }

    public static void unbind(){
        glBindVertexArray(0);
    }


    @Override
    public void dispose(){
        glDeleteVertexArrays(ID);
    }

}