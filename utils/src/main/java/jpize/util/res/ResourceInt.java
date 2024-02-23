package jpize.util.res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResourceInt extends Resource{

    private final Class<?> classLoader;


    protected ResourceInt(String filepath, Class<?> classLoader){
        super(new File(filepath));
        this.classLoader = classLoader;
    }

    protected ResourceInt(File file, Class<?> classLoader){
        super(file);
        this.classLoader = classLoader;
    }

    protected ResourceInt(File parent, String child, Class<?> classLoader){
        this(new File(parent, child), classLoader);
    }

    protected ResourceInt(String filepath){
        this(filepath, ResourceInt.class);
    }

    protected ResourceInt(File file){
        this(file, ResourceInt.class);
    }

    protected ResourceInt(File parent, String child){
        this(new File(parent, child), ResourceInt.class);
    }


    public String resName(){
        try(InputStream in = inStream()){
            return in.toString();
        }catch(IOException e){
            return null;
        }
    }


    @Override
    public InputStream inStream(){
        try{
            final InputStream inputStream = classLoader.getResourceAsStream("/" + super.path());
            if(inputStream == null)
                throw new FileNotFoundException("Internal file does not exists: " + super.path());

            return inputStream;

        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource child(String name){
        if(super.path().isEmpty())
            return new ResourceInt(name, classLoader);

        return new ResourceInt(new File(super.file, name), classLoader);
    }

    @Override
    public boolean exists(){
        return classLoader.getResource("/" + super.path()) != null;
    }

}
