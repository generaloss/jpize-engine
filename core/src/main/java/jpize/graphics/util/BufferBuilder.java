package jpize.graphics.util;

import jpize.graphics.mesh.VertexBuffer;
import jpize.graphics.texture.Region;
import jpize.graphics.util.color.IColor;
import jpize.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class BufferBuilder{
    
    private final List<Float> vertices;
    
    public BufferBuilder(){
        vertices = new ArrayList<>();
    }
    
    
    public BufferBuilder array(float... array){
        for(float value: array)
            vertices.add(value);
        
        return this;
    }
    
    public BufferBuilder array(double... array){
        for(double value: array)
            vertices.add((float) value);
        
        return this;
    }
    
    public BufferBuilder vertex(double x, double y){
        vertices.add((float) x);
        vertices.add((float) y);
        
        return this;
    }
    
    public BufferBuilder vertex(double x, double y, double z){
        vertices.add((float) x);
        vertices.add((float) y);
        vertices.add((float) z);
        
        return this;
    }
    
    public BufferBuilder color(double r, double g, double b){
        vertices.add((float) r);
        vertices.add((float) g);
        vertices.add((float) b);
        vertices.add(1F);
        
        return this;
    }
    
    public BufferBuilder color(double r, double g, double b, double a){
        vertices.add((float) r);
        vertices.add((float) g);
        vertices.add((float) b);
        vertices.add((float) a);
        
        return this;
    }
    
    public BufferBuilder color(IColor color){
        vertices.add(color.r());
        vertices.add(color.g());
        vertices.add(color.b());
        vertices.add(color.a());
        
        return this;
    }
    
    public BufferBuilder color(){
        return color(1, 1, 1);
    }
    
    
    public BufferBuilder uv(float u, float v){
        vertices.add(u);
        vertices.add(v);
        
        return this;
    }
    
    public BufferBuilder uv(double u, double v){
        vertices.add((float) u);
        vertices.add((float) v);
        
        return this;
    }
    
    
    public BufferBuilder quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, double u1, double v1, double u2, double v2, IColor color){
        vertex(x1, y1, z1).color(color).uv(u1, v1); // 1
        vertex(x2, y2, z2).color(color).uv(u1, v2); // 2
        vertex(x3, y3, z3).color(color).uv(u2, v2); // 3
        vertex(x3, y3, z3).color(color).uv(u2, v2); // 3
        vertex(x4, y4, z4).color(color).uv(u2, v1); // 4
        vertex(x1, y1, z1).color(color).uv(u1, v1); // 1
        
        return this;
    }

    public BufferBuilder quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Region region, IColor color){
        return quad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, region.u1(), region.v1(), region.u2(), region.v2(), color);
    }
    
    
    public void end(VertexBuffer buffer){
        buffer.setData(ArrayUtils.fromList(vertices));
        vertices.clear();
    }
    
    public void end(List<Float> list){
        list.addAll(vertices);
        vertices.clear();
    }
    
}
