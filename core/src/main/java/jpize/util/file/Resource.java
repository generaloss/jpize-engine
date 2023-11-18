package jpize.util.file;

import jpize.util.io.FastReader;
import jpize.util.io.JpizeInputStream;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class Resource{

    protected final File file;

    public Resource(File file){
        this.file = file;
    }

    
    public abstract InputStream inStream();

    public JpizeInputStream jpizeIn(){
        return new JpizeInputStream(inStream());
    }


    public FastReader reader(){
        return new FastReader(inStream());
    }

    public String readString(){
        try(final InputStream in = inStream()){
            return new String(in.readAllBytes());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public byte[] readBytes(){
        try(InputStream inStream = inStream()){
            return inStream.readAllBytes();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public ByteBuffer readByteBuffer(){
        final byte[] bytes = readBytes();

        final ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
        buffer.put(bytes);
        buffer.flip();

        return buffer;
    }
    
    
    public String name(){
        return file.getName();
    }
    
    public String extension(){
        final String name = name();
        final int dotIndex = name.lastIndexOf(".");
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }
    
    public String simpleName(){
        final String name = name();
        final int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? name : name.substring(0, dotIndex);
    }
    
    public Resource[] listRes(){
        final String[] paths = file.list();
        if(paths == null)
            return new Resource[0];
        
        final Resource[] resources = new Resource[paths.length];
        for(int i = 0; i < paths.length; i++)
            resources[i] = child(paths[i]);
        
        return resources;
    }
    
    public Resource[] listRes(FilenameFilter filter){
        final Resource[] resources = listRes();
        final List<Resource> filteredResources = new ArrayList<>();

        for(Resource resource: resources)
            if(filter.accept(file, resource.name()))
                filteredResources.add(resource);
        
        return filteredResources.toArray(new Resource[0]);
    }
    
    public String[] list(){
        return file.list();
    }
    
    public String[] list(FilenameFilter filter){
        return file.list(filter);
    }

    public abstract Resource child(String name);
    
    
    public abstract boolean exists();
    
    
    public String path(){
        return osGeneralizePath(file.getPath());
    }
    
    public String absolutePath(){
        return file.getAbsolutePath();
    }
    
    public File file(){
        return file;
    }
    
    
    public boolean isExternal(){
        return this instanceof ResourceExt;
    }
    
    public boolean isInternal(){
        return this instanceof ResourceInt;
    }

    public ResourceExt asExternal(){
        return (ResourceExt) this;
    }

    public ResourceInt asInternal(){
        return (ResourceInt) this;
    }
    
    
    @Override
    public String toString(){
        return name();
    }


    public static ResourceExt external(String filepath){
        return new ResourceExt(filepath);
    }

    public static ResourceExt external(File file){
        return new ResourceExt(file);
    }

    public static ResourceExt external(File parent, String child){
        return new ResourceExt(parent, child);
    }


    public static ResourceInt internal(String filepath){
        return new ResourceInt(filepath);
    }

    public static ResourceInt internal(File file){
        return new ResourceInt(file);
    }

    public static ResourceInt internal(File parent, String child){
        return new ResourceInt(parent, child);
    }

    public static ResourceInt internal(String filepath, Class<?> classLoader){
        return new ResourceInt(filepath, classLoader);
    }

    public static ResourceInt internal(File file, Class<?> classLoader){
        return new ResourceInt(file, classLoader);
    }

    public static ResourceInt internal(File parent, String child, Class<?> classLoader){
        return new ResourceInt(parent, child, classLoader);
    }

    
    public static String osGeneralizePath(String path){
        return path.replace("\\", "/");
    }
    
}