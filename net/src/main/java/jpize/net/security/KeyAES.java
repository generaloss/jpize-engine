package jpize.net.security;

import jpize.util.res.Resource;
import jpize.util.res.ResourceExt;

import javax.crypto.*;
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
    

    public byte[] encrypt(byte[] bytes){
        try{
            return encryptCipher.doFinal(bytes);
        }catch(IllegalBlockSizeException | BadPaddingException e){
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] bytes){
        try{
            return decryptCipher.doFinal(bytes);
        }catch(Exception ignored){
            return null;
        }
    }
    
    public SecretKey getKey(){
        return key;
    }
    

    public void save(ResourceExt resource){
        try{
            resource.mkDirsAndFile();
            resource.writeBytes(key.getEncoded());
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
