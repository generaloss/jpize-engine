package jpize.net;

import jpize.util.file.ResourceExt;

import java.io.*;
import java.net.URL;

public class NetUtils{

    public static void downloadToStream(URL url, OutputStream outStream, int bufferSize) throws IOException{
        final BufferedInputStream inStream = new BufferedInputStream(url.openStream());
        final byte[] buffer = new byte[bufferSize];

        while(!Thread.interrupted()){
            final int length = inStream.read(buffer, 0, bufferSize);
            if(length == -1)
                break;

            outStream.write(buffer, 0, length);
        }

        inStream.close();
    }

    public static void downloadToRes(String URlString, ResourceExt resource){
        try{
            final FileOutputStream inStream = resource.outStream();
            downloadToStream(new URL(URlString), inStream, 1024);
            inStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void downloadToFile(String URlString, String filepath){
        try{
            final FileOutputStream inStream = new FileOutputStream(filepath);
            downloadToStream(new URL(URlString), inStream, 1024);
            inStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void downloadToFile(String URlString, File file){
        try{
            final FileOutputStream inStream = new FileOutputStream(file);
            downloadToStream(new URL(URlString), inStream, 1024);
            inStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}
