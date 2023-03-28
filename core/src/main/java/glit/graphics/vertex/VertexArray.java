package glit.graphics.vertex;

import glit.context.Disposable;
import glit.graphics.gl.Primitive;
import glit.graphics.gl.Type;

import static org.lwjgl.opengl.GL33.*;

public class VertexArray implements Disposable{

    private final int id;

    public VertexArray(){
        id = glGenVertexArrays();
        bind();
    }


    public void drawArrays(int verticesNum){
        drawArrays(verticesNum, Primitive.TRIANGLES);
    }

    public void drawArrays(int verticesNum, Primitive mode){
        bind();
        glDrawArrays(mode.GL, 0, verticesNum);
    }


    public void drawElements(int indicesNum){
        drawElements(indicesNum, Primitive.TRIANGLES);
    }

    public void drawElements(int indicesNum, Primitive mode){
        drawElements(indicesNum, mode, Type.UNSIGNED_INT);
    }

    public void drawElements(int indicesNum, Primitive mode, Type indicesType){
        bind();
        glDrawElements(mode.GL, indicesNum, indicesType.GL, 0);
    }

    public int getId(){
        return id;
    }

    public void bind(){
        glBindVertexArray(id);
    }

    public static void unbind(){
        glBindVertexArray(0);
    }


    @Override
    public void dispose(){
        glDeleteVertexArrays(id);
    }

}