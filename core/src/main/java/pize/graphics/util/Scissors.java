package pize.graphics.util;

import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.graphics.util.batch.TextureBatch;

import java.awt.*;
import java.util.LinkedList;

public class Scissors{
    
    private final TextureBatch batch;
    private final LinkedList<Rectangle> scissorList;
    
    public Scissors(TextureBatch batch){
        this.batch = batch;
        scissorList = new LinkedList<>();
    }
    
    public void begin(int x, int y, int width, int height){
        batch.end();
        
        if(!Gl.isEnabled(Target.SCISSOR_TEST))
            Gl.enable(Target.SCISSOR_TEST);
        
        if(!scissorList.isEmpty()){
            final Rectangle rect = scissorList.getFirst();
            final int rectX2 = rect.x + rect.width;
            final int rectY2 = rect.y + rect.height;
            
            final int oldX2 = x + width ;
            final int oldY2 = y + height;
            x = Math.max(Math.min(x, rectX2), rect.x);
            y = Math.max(Math.min(y, rectY2), rect.y);
            final int x2 = x + width ;
            final int y2 = y + height;
            width  = Math.max(0, Math.min(Math.min(width , oldX2 - rect.x), Math.min(rectX2, x2) - x));
            height = Math.max(0, Math.min(Math.min(height, oldY2 - rect.y), Math.min(rectY2, y2) - y));
        }
        
        final Rectangle rect = new Rectangle(x, y, width, height);
        
        Gl.scissor(rect.x, rect.y, rect.width, rect.height);
        scissorList.add(rect);
        
        /*
        System.out.println("  ".repeat(scissorList.size() - 1) + "Added: " + rect.width + " " + rect.height);
        
        Gl.disable(Target.SCISSOR_TEST);
        batch.begin();
        Rectangle[] array = scissorList.toArray(new Rectangle[]{});
        for(int i = 0; i < scissorList.size(); i++){
            // if(i == 0)
            //     continue;
            
            final Rectangle rectangle = array[i];
            
            final int hash = rectangle.hashCode();
            batch.setColor(new ImmutableColor(
                Integer.parseInt(String.valueOf(hash).substring(0, 3)),
                Integer.parseInt(String.valueOf(hash).substring(3, 5)),
                Integer.parseInt(String.valueOf(hash).substring(5, 8)),
                25
            ));
            batch.draw(TextureUtils.quadTexture(), rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        
        batch.resetColor();
        Gl.enable(Target.SCISSOR_TEST);
        */
    }
    
    public void end(){
        batch.end();
        
        // final Rectangle pollRect =
        scissorList.pollLast();
        // System.out.println("  ".repeat(scissorList.size()) + "Removed: " + pollRect.width + " " + pollRect.height);
        
        if(scissorList.size() != 0){
            final Rectangle rect = scissorList.getFirst();
            Gl.scissor(rect.x, rect.y, rect.width, rect.height);
            
        }else if(Gl.isEnabled(Target.SCISSOR_TEST))
            Gl.disable(Target.SCISSOR_TEST);
    }
    
}
