package pize.graphics.gl;

import pize.graphics.util.color.IColor;

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


    public static void clearBuffers(boolean depth, boolean stencil){
        int bits = GL_COLOR_BUFFER_BIT
            | (depth ? GL_DEPTH_BUFFER_BIT : 0)
            | (stencil ? GL_STENCIL_BUFFER_BIT : 0);
        glClear(bits);
    }

    public static void clearBuffers(boolean depth){
        int bits = GL_COLOR_BUFFER_BIT
            | (depth ? GL_DEPTH_BUFFER_BIT : 0);
        glClear(bits);
    }
    
    public static void clearDepthBuffer(){
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    public static void clearColorBuffer(){
        glClear(GL_COLOR_BUFFER_BIT);
    }


    public static boolean isEnabled(Target target){
        return glIsEnabled(target.GL);
    }

    public static void enable(Target target){
        glEnable(target.GL);
    }

    public static void enable(Target... targets){
        for(Target target: targets)
            enable(target);
    }

    public static void disable(Target target){
        glDisable(target.GL);
    }

    public static void disable(Target... targets){
        for(Target target: targets)
            disable(target);
    }


    public static void blendFunc(BlendFactor sFactor, BlendFactor dFactor){
        glBlendFunc(sFactor.GL, dFactor.GL);
    }

    public static void depthFunc(DepthFunc func){
        glDepthFunc(func.GL);
    }


    public static void depthMask(boolean flag){
        glDepthMask(flag);
    }

    public static void cullFace(Face mode){
        glCullFace(mode.GL);
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
        glPolygonMode(face.GL, mode.GL);
    }
    
    public static void polygonOffset(float factor, float units){
        glPolygonOffset(factor, units);
    }
    
    public static void lineWidth(float width){
        glLineWidth(width);
    }

}
