package jpize.gl;

import jpize.gl.glenum.*;
import jpize.gl.tesselation.GlFace;
import jpize.gl.type.GlType;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.gl.texture.GlBlendFactor;
import jpize.gl.texture.GlSizedFormat;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import java.nio.ByteBuffer;
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
    
    public static void clearColorDepthBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public static void clearCDSBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }


    public static void clearDepth(float depth){
        glClearDepth(depth);
    }
    

    public static boolean isEnabled(GlTarget target){
        return glIsEnabled(target.GL);
    }
    
    public static boolean isEnabled(GlTarget target, int index){
        return glIsEnabledi(target.GL, index);
    }

    
    public static void enable(GlTarget target){
        glEnable(target.GL);
    }
    
    public static void enable(GlTarget target, int index){
        glEnablei(target.GL, index);
    }

    public static void enable(GlTarget... targets){
        for(GlTarget target: targets)
            enable(target);
    }

    
    public static void disable(GlTarget target){
        glDisable(target.GL);
    }
    
    public static void disable(GlTarget target, int index){
        glDisablei(target.GL, index);
    }

    public static void disable(GlTarget... targets){
        for(GlTarget target: targets)
            disable(target);
    }


    public static void enableCullFace(){
        enable(GlTarget.CULL_FACE);
    }

    public static void disableCullFace(){
        disable(GlTarget.CULL_FACE);
    }


    public static void blendFunc(GlBlendFactor sFactor, GlBlendFactor dFactor){
        glBlendFunc(sFactor.GL, dFactor.GL);
    }

    public static void depthFunc(GlDepthFunc func){
        glDepthFunc(func.GL);
    }


    public static void depthMask(boolean flag){
        glDepthMask(flag);
    }

    public static void cullFace(GlFace mode){
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


    public static void polygonMode(GlFace face, GlPolygonMode mode){
        glPolygonMode(face.GL, mode.GL);
    }

    public static void polygonMode(GlPolygonMode mode){
        polygonMode(GlFace.FRONT_AND_BACK, mode);
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
    
    
    public static void hint(GlHint hint, GlMode mode){
        glHint(hint.GL, mode.GL);
    }
    
    
    public static void fog(GlFog fog, int i){
        glFogi(fog.GL, i);
    }
    
    public static void fog(GlFog fog, float v){
        glFogf(fog.GL, v);
    }
    
    public static void fog(Color color){
        glFogfv(GlFog.COLOR.GL, color.toArray());
    }


    public static void drawPixels(int width, int height, GlSizedFormat format, GlType type, ByteBuffer pixels){
        glDrawPixels(width, height, format.GL, type.GL, pixels);
    }

    public static void readPixels(int x, int y, int width, int height, GlSizedFormat format, GlType type, ByteBuffer pixels){
        glReadPixels(x, y, width, height, format.GL, type.GL, pixels);
    }


    public static void blendFuncSeparate(int srcFactorRGB, int dstFactorRGB, int srcFactorAlpha, int dstFactorAlpha){
        glBlendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
    }

    public static void setCapabilities(GLCapabilities capabilities){
        GL.setCapabilities(capabilities);
    }

}
