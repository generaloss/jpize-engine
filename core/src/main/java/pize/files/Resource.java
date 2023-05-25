package pize.files;

import pize.util.io.FastReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Resource{
    
    private final boolean external;
    private final File file;
    
    public Resource(String filepath, boolean external){
        this.external = external;
        if(!external)
            filepath = "/" + filepath;
        
        file = new File(filepath);
    }
    
    private Resource(File file, boolean external){
        this.external = external;
        this.file = file;
    }
    
    
    public Resource(String filepath){
        this(filepath, false);
    }
    
    public Resource(File file){
        this(file, false);
    }
    
    public Resource(File parent, String child, boolean external){
        this(new File(parent, child), external);
    }
    
    public Resource(File parent, String child){
        this(parent, child, false);
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
        return file.getParentFile().mkdirs();
    }
    
    public boolean mkDirsAndFile(){ // External only
        return mkParentDirs() && create();
    }
    
    
    public InputStream inStream(){
        try{
            if(external)
                return new FileInputStream(file);
            else{
                final InputStream inputStream = getClass().getResourceAsStream(getPath());
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
    
    
    public FastReader getReader(){
        return new FastReader(inStream());
    }
    
    public PrintStream getWriter(){
        return new PrintStream(outStream());
    }
    
    public String readString(){
        try(final InputStream in = inStream()){
            return new String(in.readAllBytes());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
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
    
    public Resource[] list(){
        final String[] relativePaths = file.list();
        if(relativePaths == null)
            return new Resource[0];
        
        final Resource[] resources = new Resource[relativePaths.length];
        for(int i = 0; i < relativePaths.length; i++)
            resources[i] = getChild(relativePaths[i]);
        
        return resources;
    }
    
    public Resource getChild(String name){
        if(getPath().length() == 0)
            return new Resource(name, external);
        
        return new Resource(new File(file, name), external);
    }
    
    
    public static String readString(String path){
        try{
            return new String(Files.readAllBytes(Paths.get(path)));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
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
    
}