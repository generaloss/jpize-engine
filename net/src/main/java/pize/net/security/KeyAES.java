package pize.net.security;

import pize.files.Resource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

public class KeyAES{

    private final SecretKey key;
    private Cipher decryptCipher, encryptCipher;
    
    
    public KeyAES(SecretKey key){
        try{
            this.key = key;
            initCiphers();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public KeyAES(byte[] bytes){
        try{
            key = new SecretKeySpec(bytes, "AES");
            initCiphers();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public KeyAES(int size){
        try{
            final KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size);
            key = generator.generateKey();
            
            initCiphers();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    private void initCiphers() throws Exception{
        decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        
        encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
    }
    

    public synchronized byte[] encrypt(byte[] bytes){
        try{
            return encryptCipher.doFinal(bytes);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public synchronized byte[] decrypt(byte[] bytes){
        try{
            return decryptCipher.doFinal(bytes);
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
            throw new RuntimeException(e);
        }
    }
    
    public static KeyAES load(Resource resource){
        try(final InputStream inStream = resource.inStream()){
            return new KeyAES(inStream.readAllBytes());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
