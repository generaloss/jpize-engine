package glit.files;

import glit.util.io.FastReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandle{

    private final File file;
    private final boolean external;


    public FileHandle(String filepath, boolean external){
        file = new File(filepath);
        this.external = external;
    }

    public FileHandle(String filepath){
        this(filepath, false);
    }

    public FileHandle(File file, boolean external){
        this.file = file;
        this.external = external;
    }

    public FileHandle(File file){
        this(file, false);
    }

    public FileHandle(File parent, String child, boolean external){
        this(new File(parent, child), external);
    }

    public FileHandle(File parent, String child){
        this(parent, child, false);
    }


    public boolean mkdirs(){
        return file.mkdirs();
    }

    public boolean mkdir(){
        return file.mkdir();
    }

    public boolean create(){
        try{
            return file.createNewFile();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    public InputStream input(){
        if(external){
            try{
                return new FileInputStream(file);
            }catch(FileNotFoundException e){
                throw new RuntimeException("File does not exists: " + getPath());
            }
        }else{
            InputStream inputStream = getClass().getResourceAsStream("/" + getPath());
            if(inputStream == null)
                throw new RuntimeException("File does not exists: /" + getPath());

            return inputStream;
        }
    }

    public FileOutputStream output(){
        if(!external)
            throw new RuntimeException("Cannot write into internal file: " + file);

        try{
            return new FileOutputStream(file);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File does not exists: " + file);
        }
    }


    public FastReader reader(){
        return new FastReader(input());
    }

    public PrintStream writer(){
        return new PrintStream(output());
    }

    public String readString(){
        try{
            return new String(input().readAllBytes());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void writeString(String string){
        try{
            FileOutputStream out = output();
            out.write(string.getBytes());
            out.flush();
            out.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void appendString(CharSequence string){
        writeString(readString() + string);
    }


    public String name(){
        return file.getName();
    }

    public String extension(){
        String name = name();
        int dotIndex = name.lastIndexOf(".");
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }

    public String nameWithoutExtension(){
        String name = name();
        int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? name : name.substring(0, dotIndex);
    }

    public FileHandle[] list(){
        String[] relativePaths = file.list();
        if(relativePaths == null)
            return new FileHandle[0];

        FileHandle[] handles = new FileHandle[relativePaths.length];
        for(int i = 0; i < relativePaths.length; i++)
            handles[i] = child(relativePaths[i]);

        return handles;
    }

    public FileHandle child(String name){
        if(getPath().length() == 0)
            return new FileHandle(name, external);

        return new FileHandle(new File(file, name), external);
    }


    public static String readString(String path){
        try{
            return new String(Files.readAllBytes(Paths.get(path)));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public String parent(){
        return file.getParent();
    }

    public File parentFile(){
        return file.getParentFile();
    }

    public boolean exists(){
        return file.exists();
    }


    public String getPath(){
        return file.getPath().replace('\\', '/');
    }

    public String getAbsolutePath(){
        return file.getAbsolutePath();
    }

    public File getFile(){
        return file;
    }


    @Override
    public String toString(){
        return getAbsolutePath();
    }

}
