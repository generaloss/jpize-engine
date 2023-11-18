package jpize.net.security;

import jpize.util.file.Resource;
import jpize.util.file.ResourceExt;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class PublicRSA{

    private final PublicKey key;
    private Cipher cipher;

    public PublicRSA(PublicKey key){
        try{
            this.key = key;
            initCipher();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public PublicRSA(byte[] bytes){
        try{
            key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
            initCipher();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    private void initCipher() throws Exception{
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }
    

    public byte[] encrypt(byte[] data){
        try{
            return cipher.doFinal(data);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public PublicKey getKey(){
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
    
    public static PublicRSA load(Resource resource){
        try(final InputStream inStream = resource.inStream()){
            return new PublicRSA(inStream.readAllBytes());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}
