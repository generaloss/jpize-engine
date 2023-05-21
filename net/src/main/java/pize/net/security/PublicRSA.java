package pize.net.security;

import pize.files.Resource;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class PublicRSA{

    private final PublicKey key;

    public PublicRSA(PublicKey key){
        this.key = key;
    }

    public PublicRSA(byte[] bytes){
        try{
            key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    

    public byte[] encrypt(byte[] data){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public PublicKey getKey(){
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
    
    public static PublicRSA load(Resource resource){
        try{
            InputStream inStream = resource.inStream();
            byte[] bytes = inStream.readAllBytes();
            inStream.close();
            
            return new PublicRSA(bytes);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}
