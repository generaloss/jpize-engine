package pize.net.security;

import pize.files.Resource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

public class KeyAES{

    private final SecretKey key;

    public KeyAES(SecretKey key){
        this.key = key;
    }
    
    public KeyAES(byte[] bytes){
        try{
            key = new SecretKeySpec(bytes, "AES");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public KeyAES(int size){
        try{
            final KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size);
            key = generator.generateKey();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    

    public byte[] encrypt(byte[] bytes){
        try{
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(bytes);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] bytes){
        try{
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(bytes);
            
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public SecretKey getKey(){
        return key;
    }
    

    public void save(Resource resource){
        try{
            resource.mkDirsAndFile();
            resource.getWriter().write(key.getEncoded());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static KeyAES load(Resource resource){
        try{
            InputStream inStream = resource.inStream();
            byte[] bytes = inStream.readAllBytes();
            inStream.close();
            
            return new KeyAES(bytes);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
