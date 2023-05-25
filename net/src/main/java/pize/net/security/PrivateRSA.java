package pize.net.security;

import pize.files.Resource;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateRSA{

    private final PrivateKey key;

    public PrivateRSA(PrivateKey key){
        this.key = key;
    }

    public PrivateRSA(byte[] bytes){
        try{
            key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    
    public byte[] decrypt(byte[] data){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);

        }catch(Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
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
