package pize.net.security;

import pize.files.Resource;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateRSA{

    private final PrivateKey key;
    private Cipher cipher;

    public PrivateRSA(PrivateKey key){
        try{
            this.key = key;
            initCipher();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public PrivateRSA(byte[] bytes){
        try{
            key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
            initCipher();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    private void initCipher() throws Exception{
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    
    public byte[] decrypt(byte[] data){
        try{
            return cipher.doFinal(data);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public PrivateKey getKey(){
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
    
    public static PrivateRSA load(Resource resource){
        try(final InputStream inStream = resource.inStream()){
            return new PrivateRSA(inStream.readAllBytes());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
