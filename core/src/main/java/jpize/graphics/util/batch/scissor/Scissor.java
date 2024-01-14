package jpize.graphics.util.batch.scissor;

import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.math.Maths;
import jpize.util.math.vecmath.vector.Vec2d;
import jpize.util.math.vecmath.vector.Vec2f;
import jpize.util.math.vecmath.vector.Vec2i;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Scissor{
    
    private final TextureBatch batch;
    private final Map<Integer, ScissorNode> scissorList;
    
    public Scissor(TextureBatch batch){
        this.batch = batch;
        scissorList = new HashMap<>();
    }
    
    
    public void begin(int index, double x, double y, double width, double height){
        this.begin(index, Maths.round(x), Maths.round(y), Maths.round(width), Maths.round(height));
    }
    
    public void begin(int index, double x, double y, double width, double height, int scissorOfIndex){
        this.begin(index, Maths.round(x), Maths.round(y), Maths.round(width), Maths.round(height), scissorOfIndex);
    }
    
    public void begin(int index, int x, int y, int width, int height){
        this.begin(index, x, y, width, height, -1);
    }
    
    public void begin(ScissorNode scissor){
        if(scissor.getIndex() < 0)
            return;
        
        if(scissor.getScissorOfIndex() != -1 &&!scissorList.isEmpty()){
            final ScissorNode scissorOf = scissorList.get(scissor.getScissorOfIndex());
            
            final int scissorOfX2 = scissorOf.getX2();
            final int scissorOfY2 = scissorOf.getY2();
            
            final int oldX2 = scissor.getX2();
            final int oldY2 = scissor.getY2();
            
            scissor.getRectangle().x = Math.max(Math.min(scissor.getX(), scissorOfX2), scissorOf.getX());
            scissor.getRectangle().y = Math.max(Math.min(scissor.getY(), scissorOfY2), scissorOf.getY());
            scissor.getRectangle().width  = Math.max(0, Math.min(Math.min(scissor.getWidth() , oldX2 - scissorOf.getX()), Math.min(scissorOfX2, scissor.getX2()) - scissor.getX()));
            scissor.getRectangle().height = Math.max(0, Math.min(Math.min(scissor.getHeight(), oldY2 - scissorOf.getY()), Math.min(scissorOfY2, scissor.getY2()) - scissor.getY()));
        }
        
        scissorList.put(scissor.getIndex(), scissor);
        
        batch.end();
        if(!Gl.isEnabled(GlTarget.SCISSOR_TEST))
            Gl.enable(GlTarget.SCISSOR_TEST);
        scissor.activate();
    }
    
    public void begin(int index, int x, int y, int width, int height, int scissorOfIndex){
        if(index < 0)
            return;
        
        if(scissorOfIndex != -1 &&!scissorList.isEmpty()){
            final ScissorNode scissorOf = scissorList.get(scissorOfIndex);
            
            final int scissorOfX2 = scissorOf.getX2();
            final int scissorOfY2 = scissorOf.getY2();
            
            final int oldX2 = x + width;
            final int oldY2 = y + height;
            
            x = Math.max(Math.min(x, scissorOfX2), scissorOf.getX());
            y = Math.max(Math.min(y, scissorOfY2), scissorOf.getY());
            
            final int x2 = x + width;
            final int y2 = y + height;
            
            width  = Math.max(0, Math.min(Math.min(width , oldX2 - scissorOf.getX()), Math.min(scissorOfX2, x2) - x));
            height = Math.max(0, Math.min(Math.min(height, oldY2 - scissorOf.getY()), Math.min(scissorOfY2, y2) - y));
        }
        
        final ScissorNode scissor = new ScissorNode(index, x, y, width, height, scissorOfIndex);
        scissorList.put(index, scissor);
        
        batch.end();
        if(!Gl.isEnabled(GlTarget.SCISSOR_TEST))
            Gl.enable(GlTarget.SCISSOR_TEST);
        scissor.activate();
    }
    
    public void end(int index){
        final ScissorNode removedScissor = scissorList.remove(index);
        final int removedIndexOf = removedScissor.getScissorOfIndex();
        
        batch.end();
        if(removedIndexOf != -1 && !scissorList.isEmpty()){
            final ScissorNode scissor = scissorList.get(removedIndexOf);
            scissor.activate();
            
        }else if(Gl.isEnabled(GlTarget.SCISSOR_TEST))
            Gl.disable(GlTarget.SCISSOR_TEST);
    }


    public boolean contains(double x, double y){
        for(ScissorNode node: scissorList.values())
            if(node.getRectangle().contains(x, y))
                return true;

        return false;
    }

    public boolean contains(Vec2d vec2){
        return contains(vec2.x, vec2.y);
    }

    public boolean contains(Vec2f vec2){
        return contains(vec2.x, vec2.y);
    }

    public boolean contains(Vec2i vec2){
        return contains(vec2.x, vec2.y);
    }


    public boolean contains(double x, double y, double width, double height){
        for(ScissorNode node: scissorList.values())
            if(node.getRectangle().contains(x, y, width, height))
                return true;

        return false;
    }

    public boolean contains(Rectangle2D rectangle){
        return contains(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }


    public boolean intersects(double x, double y, double width, double height){
        for(ScissorNode node: scissorList.values())
            if(node.getRectangle().intersects(x, y, width, height))
                return true;

        return false;
    }

    public boolean intersects(Rectangle2D rectangle){
        return intersects(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
    
}
