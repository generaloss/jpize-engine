package jpize.util.file;

import jpize.util.io.JpizeOutputStream;

import java.io.*;

public class ResourceExt extends Resource{

    protected ResourceExt(File file){
        super(file);
    }

    protected ResourceExt(String filepath){
        super(new File(osGeneralizePath(filepath)));
    }

    protected ResourceExt(File parent, String child){
        super(new File(parent, child));
    }


    public boolean create(){
        try{
            return super.file.createNewFile();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean mkDir(){
        return super.file.mkdir();
    }

    public boolean mkDirs(){
        return super.file.mkdirs();
    }

    public boolean mkParentDirs(){
        final File parentFile = super.file.getParentFile();
        if(parentFile != null)
            return parentFile.mkdirs();

        return false;
    }

    public boolean mkDirsAndFile(){
        return mkParentDirs() && create();
    }

    public boolean delete(){
        return super.file.delete();
    }


    @Override
    public InputStream inStream(){
        try{
            return new FileInputStream(super.file);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File does not exists: " + super.file);
        }
    }


    public FileOutputStream outStream(){
        try{
            return new FileOutputStream(super.file);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File does not exists: " + super.file);
        }
    }

    public JpizeOutputStream jpizeOut(){
        return new JpizeOutputStream(outStream());
    }

    public PrintStream writer(){
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


    public void writeBytes(byte[] bytes){
        try(final FileOutputStream out = outStream()){
            out.write(bytes);
            out.flush();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public Resource child(String name){
        if(super.path().isEmpty())
            return new ResourceExt(name);

        return new ResourceExt(new File(super.file, name));
    }


    @Override
    public boolean exists(){
        return super.file.exists();
    }

}
