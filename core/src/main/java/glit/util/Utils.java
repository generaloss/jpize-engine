package glit.util;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.libc.LibCStdlib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.*;

public class Utils{

    public static void delay(long millis, int nanos){
        try{
            Thread.sleep(millis, nanos);
        }catch(InterruptedException ignored){ }
    }

    public static void delayMillis(long millis){
        try{
            Thread.sleep(millis);
        }catch(InterruptedException ignored){ }
    }

    public static void delayNanos(int nanos){
        try{
            Thread.sleep(0, nanos);
        }catch(InterruptedException ignored){ }
    }


    public static void close(InputStream stream){
        try{
            stream.close();
        }catch(IOException ignored){ }
    }

    public static void close(OutputStream stream){
        try{
            stream.close();
        }catch(IOException ignored){ }
    }

    public static void close(Socket socket){
        try{
            socket.close();
        }catch(IOException ignored){ }
    }

    public static void close(ServerSocket socket){
        try{
            socket.close();
        }catch(IOException ignored){ }
    }


    public static void free(ByteBuffer buffer){
        // LibCStdlib.free(buffer);
    }

    public static void free(ShortBuffer buffer){
        LibCStdlib.free(buffer);
    }

    public static void free(IntBuffer buffer){
        LibCStdlib.free(buffer);
    }

    public static void free(LongBuffer buffer){
        LibCStdlib.free(buffer);
    }

    public static void free(FloatBuffer buffer){
        LibCStdlib.free(buffer);
    }

    public static void free(DoubleBuffer buffer){
        LibCStdlib.free(buffer);
    }

    public static void free(PointerBuffer buffer){
        LibCStdlib.free(buffer);
    }


    public static void invokeStatic(Class<?> targetClass, String name, Object... args){
        try{
            Method method = targetClass.getDeclaredMethod(name);

            method.setAccessible(true);
            method.invoke(null, args);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
