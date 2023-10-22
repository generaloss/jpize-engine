package jpize.files;

import jpize.util.io.FastReader;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Resource{
    
    private final boolean external;
    private final File file;
    private final Class<?> classLoader;
    
    // Main
    public Resource(File file, boolean external, Class<?> classLoader){
        this.external = external;
        this.file = file;
        this.classLoader = classLoader;
    }
    
    public Resource(String filepath, boolean external, Class<?> classLoader){
        this(new File((!external ? "/" : "") + filepath), external, classLoader);
    }
    
    // Parent child
    public Resource(File parent, String child, boolean external, Class<?> classLoader){
        this(new File(parent, child), external, classLoader);
    }
    
    public Resource(File parent, String child, boolean external){
        this(new File(parent, child), external, Resource.class);
    }
    
    // Internal
    public Resource(String filepath, Class<?> classLoader){
        this(filepath, false, classLoader);
    }
    
    public Resource(String filepath){
        this(filepath, Resource.class);
    }
    
    public Resource(File file){
        this(file, false, Resource.class);
    }
    
    // External
    public Resource(String filepath, boolean external){
        this(new File((!external ? "/" : "") + filepath), external, Resource.class);
    }
    
    
    public boolean create(){ // External only
        try{
            return file.createNewFile();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
    public boolean mkDir(){ // External only
        return file.mkdir();
    }
    
    public boolean mkDirs(){ // External only
        return file.mkdirs();
    }
    
    public boolean mkParentDirs(){ // External only
        final File parentFile = file.getParentFile();
        if(parentFile != null)
            return parentFile.mkdirs();

        return false;
    }
    
    public boolean mkDirsAndFile(){ // External only
        return mkParentDirs() && create();
    }
    
    
    public InputStream inStream(){
        try{
            if(external)
                return new FileInputStream(file);
            else{
                final InputStream inputStream = classLoader.getResourceAsStream(getPath());
                if(inputStream == null)
                    throw new FileNotFoundException("Internal file does not exists: " + getPath());
                
                return inputStream;
            }
            
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    
    public FileOutputStream outStream(){
        if(!external)
            throw new RuntimeException("Cannot write into internal file: " + file);
        
        try{
            return new FileOutputStream(file);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File does not exists: " + file);
        }
    }


    public JpizeInputStream getJpizeIn(){
        return new JpizeInputStream(inStream());
    }

    public JpizeOutputStream getJpizeOut(){
        return new JpizeOutputStream(outStream());
    }

    public FastReader getReader(){
        return new FastReader(inStream());
    }

    public PrintStream getWriter(){
        return new PrintStream(outStream());
    }


    public void writeString(String string){
        try(final FileOutputStream out = outStream()){
            out.write(string.getBytes());
            out.flush();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void appendString(CharSequence string){
        writeString(readString() + string);
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
    
    
    public String getName(){
        return file.getName();
    }
    
    public String getExtension(){
        final String name = getName();
        final int dotIndex = name.lastIndexOf(".");
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }
    
    public String getSimpleName(){
        final String name = getName();
        final int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? name : name.substring(0, dotIndex);
    }
    
    public Resource[] listResources(){
        final String[] relativePaths = file.list();
        if(relativePaths == null)
            return new Resource[0];
        
        final Resource[] resources = new Resource[relativePaths.length];
        for(int i = 0; i < relativePaths.length; i++)
            resources[i] = getChild(relativePaths[i]);
        
        return resources;
    }
    
    public Resource[] listResources(FilenameFilter filter){
        final Resource[] resources = listResources();
        final List<Resource> filteredResources = new ArrayList<>();
        for(Resource resource: resources)
            if(filter.accept(file, resource.getName()))
                filteredResources.add(resource);
        
        return filteredResources.toArray(new Resource[0]);
    }
    
    public String[] list(){
        return file.list();
    }
    
    public String[] list(FilenameFilter filter){
        return file.list(filter);
    }
    
    public Resource getChild(String name){
        if(getPath().isEmpty())
            return new Resource(name, external, classLoader);
        
        return new Resource(new File(file, name), external, classLoader);
    }
    
    
    public boolean exists(){
        return file.exists();
    }
    
    
    public String getPath(){
        return file.getPath().replace("\\", "/");
    }
    
    public String getAbsolutePath(){
        return file.getAbsolutePath();
    }
    
    public File getFile(){
        return file;
    }
    
    
    public boolean isExternal(){
        return external;
    }
    
    public boolean isInternal(){
        return !external;
    }
    
    
    @Override
    public String toString(){
        return getAbsolutePath();
    }
    
    
    public static String readString(String filepath, boolean external){
        return new Resource(filepath, external).readString();
    }
    
    public static String readString(String filepath){
        return readString(filepath, false);
    }

    public static byte[] readBytes(String filepath, boolean external){
        return new Resource(filepath, external).readBytes();
    }

    public static byte[] readBytes(String filepath){
        return readBytes(filepath, false);
    }

    public static ByteBuffer readByteBuffer(String filepath, boolean external){
        return new Resource(filepath, external).readByteBuffer();
    }

    public static ByteBuffer readByteBuffer(String filepath){
        return readByteBuffer(filepath, false);
    }
    
}