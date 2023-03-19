package glit.graphics.gl;

import glit.graphics.util.color.IColor;

import static org.lwjgl.opengl.GL33.*;

public class Gl{

    public static void clearColor(double r, double g, double b, double a){
        glClearColor((float) r, (float) g, (float) b, (float) a);
    }

    public static void clearColor(IColor color){
        clearColor(color.r(), color.g(), color.b(), 1);
    }

    public static void clearColor(double r, double g, double b){
        clearColor(r, g, b, 1);
    }


    public static void clearBuffer(boolean depth, boolean stencil){
        int bits = GL_COLOR_BUFFER_BIT
            | (depth ? GL_DEPTH_BUFFER_BIT : 0)
            | (stencil ? GL_STENCIL_BUFFER_BIT : 0);
        glClear(bits);
    }

    public static void clearBuffer(boolean depth){
        int bits = GL_COLOR_BUFFER_BIT
            | (depth ? GL_DEPTH_BUFFER_BIT : 0);
        glClear(bits);
    }

    public static void clearBufferColor(){
        glClear(GL_COLOR_BUFFER_BIT);
    }


    public static boolean isEnabled(Target target){
        return glIsEnabled(target.gl);
    }

    public static void enable(Target target){
        glEnable(target.gl);
    }

    public static void enable(Target... targets){
        for(Target target: targets)
            enable(target);
    }

    public static void disable(Target target){
        glDisable(target.gl);
    }

    public static void disable(Target... targets){
        for(Target target: targets)
            disable(target);
    }


    public static void blendFunc(BlendFactor sFactor, BlendFactor dFactor){
        glBlendFunc(sFactor.gl, dFactor.gl);
    }

    public static void depthFunc(DepthFunc func){
        glDepthFunc(func.gl);
    }


    public static void depthMask(boolean flag){
        glDepthMask(flag);
    }

    public static void cullFace(Face mode){
        glCullFace(mode.gl);
    }


    public static void pointSize(float size){
        glPointSize(size);
    }


    public static void setViewport(int x, int y, int width, int height){
        glViewport(x, y, width, height);
    }

    public static void setViewport(int width, int height){
        setViewport(0, 0, width, height);
    }


    public static void setPolygonMode(Face face, PolygonMode mode){
        glPolygonMode(face.gl, mode.gl);
    }

}
