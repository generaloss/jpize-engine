package pize.net.tcp;

import java.io.DataOutputStream;

@FunctionalInterface
public interface DataWriter{
    
    void write(DataOutputStream dataStream);
    
}
