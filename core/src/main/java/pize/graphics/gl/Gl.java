package pize.graphics.gl;

import pize.graphics.util.color.IColor;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Gl{
    
    public static void clearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }
    
    public static void clearColor(float r, float g, float b){
        clearColor(r, g, b, 1F);
    }

    public static void clearColor(double r, double g, double b, double a){
        glClearColor((float) r, (float) g, (float) b, (float) a);
    }
    
    public static void clearColor(double r, double g, double b){
        clearColor(r, g, b, 1D);
    }
    
    public static void clearColor(IColor color){
        clearColor(color.r(), color.g(), color.b(), color.a());
    }
    
    
    public static void clearColorBuffer(){
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void clearDepthBuffer(){
        glClear(GL_DEPTH_BUFFER_BIT);
    }
    
    public static void clearStencilBuffer(){
        glClear(GL_STENCIL_BUFFER_BIT);
    }
    
    public static void clearCDBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public static void clearCDSBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }
    

    public static boolean isEnabled(Target target){
        return glIsEnabled(target.GL);
    }
    
    public static boolean isEnabled(Target target, int index){
        return glIsEnabledi(target.GL, index);
    }

    
    public static void enable(Target target){
        glEnable(target.GL);
    }
    
    public static void enable(Target target, int index){
        glEnablei(target.GL, index);
    }

    public static void enable(Target... targets){
        for(Target target: targets)
            enable(target);
    }

    
    public static void disable(Target target){
        glDisable(target.GL);
    }
    
    public static void disable(Target target, int index){
        glDisablei(target.GL, index);
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
    
    public static void lineWidth(float width){
        glLineWidth(width);
    }


    public static void viewport(int x, int y, int width, int height){
        glViewport(x, y, width, height);
    }

    public static void viewport(int width, int height){
        viewport(0, 0, width, height);
    }
    
    public static void viewportIndexed(int index, float x, float y, float width, float height){
        glViewportIndexedf(index, x, y, width, height);
    }
    
    public static void viewportIndexed(int index, float[] array){
        glViewportIndexedfv(index, array);
    }
    
    public static void viewportIndexed(int index, FloatBuffer buffer){
        glViewportIndexedfv(index, buffer);
    }
    
    public static void viewportArray(int first, float[] array){
        glViewportArrayv(first, array);
    }
    
    public static void viewportArray(int first, FloatBuffer buffer){
        glViewportArrayv(first, buffer);
    }


    public static void polygonMode(Face face, PolygonMode mode){
        glPolygonMode(face.GL, mode.GL);
    }
    
    public static void polygonOffset(float factor, float units){
        glPolygonOffset(factor, units);
    }
    
    
    public static void scissor(int x, int y, int width, int height){
        glScissor(x, y, width, height);
    }
    
    public static void scissorIndexed(int index, int x, int y, int width, int height){
        glScissorIndexed(index, x, y, width, height);
    }
    
    public static void scissorArray(int first, int[] array){
        glScissorArrayv(first, array);
    }
    
    public static void scissorArray(int first, IntBuffer buffer){
        glScissorArrayv(first, buffer);
    }

}
