package pize.util.io;

import pize.util.Utils;

import java.io.IOException;
import java.io.InputStream;

public class FastReader{

    private static final int BUFFER_SIZE = 1 << 16;
    private static final byte EOF = -1;
    private static final byte NEW_LINE = 10;
    private static final byte SPACE = 32;

    private final InputStream inputStream;
    private final byte[] buffer;
    private int pointer, bytesRead;
    private char[] charBuffer;


    public FastReader(InputStream inputStream){
        this.inputStream = inputStream;

        buffer = new byte[BUFFER_SIZE];
        charBuffer = new char[128];
    }

    public FastReader(){
        this(System.in);
    }


    private byte read() throws IOException{
        if(bytesRead == EOF)
            throw new IOException();

        if(pointer == bytesRead)
            fillBuffer();

        return buffer[pointer++];
    }

    private void fillBuffer(){
        try{
            pointer = 0;
            bytesRead = inputStream.read(buffer);
            if(!hasNext())
                buffer[0] = EOF;

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    public String next(){
        try{
            if(!hasNext() || readJunk(SPACE) == EOF)
                return null;

            for(int i = 0; ; ){
                while(pointer < bytesRead){
                    if(buffer[pointer] > SPACE){
                        if(i == charBuffer.length)
                            doubleCharBufferSize();
                        charBuffer[i++] = (char) buffer[pointer++];
                    }else{
                        pointer++;
                        return new String(charBuffer, 0, i);
                    }
                }

                bytesRead = inputStream.read(buffer);
                if(bytesRead == EOF)
                    return new String(charBuffer, 0, i);

                pointer = 0;
            }

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void doubleCharBufferSize(){
        char[] newBuffer = new char[charBuffer.length << 1];
        System.arraycopy(charBuffer, 0, newBuffer, 0, charBuffer.length);
        charBuffer = newBuffer;
    }


    private int readJunk(int token) throws IOException{
        if(bytesRead == EOF)
            return EOF;

        do{
            while(pointer < bytesRead){
                if(buffer[pointer] > token)
                    return 0;

                pointer++;
            }

            bytesRead = inputStream.read(buffer);
            if(bytesRead == EOF)
                return EOF;

            pointer = 0;

        }
        while(true);
    }

    public String nextLine(){
        try{
            byte c = read();
            if(c == NEW_LINE || c == EOF)
                return "";

            int i = 0;
            charBuffer[i++] = (char) c;

            do{
                while(pointer < bytesRead){
                    if(buffer[pointer] != NEW_LINE){
                        if(i == charBuffer.length)
                            doubleCharBufferSize();
                        charBuffer[i++] = (char) buffer[pointer++];
                    }else{
                        pointer++;
                        return new String(charBuffer, 0, i);
                    }
                }

                bytesRead = inputStream.read(buffer);
                if(bytesRead == EOF)
                    return new String(charBuffer, 0, i);

                pointer = 0;

            }
            while(true);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    public int nextInt(){
        return Integer.parseInt(next());
    }

    public float nextFloat(){
        return Float.parseFloat(next());
    }

    public long nextLong(){
        return Long.parseLong(next());
    }

    public double nextDouble(){
        return Double.parseDouble(next());
    }

    public boolean nextBool(){
        return Boolean.parseBoolean(next());
    }


    public boolean hasNext(){
        return bytesRead != EOF;
    }

    public void waitNext(){
        while(!hasNext())
            ;
    }

    public void close(){
        if(inputStream != null)
            Utils.close(inputStream);
    }

}
