package pize.net.security;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

public class PublicRsa{

    private Key key;

    public void set(Key key){
        this.key = key;
    }

    public void set(byte[] encoded){
        try{
            key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encoded));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] data){
        try{
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            return encryptCipher.doFinal(data);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean save(String filePath){
        if(key == null)
            return false;

        try{
            File file = new File(filePath);
            if(!file.createNewFile())
                return false;
            FileOutputStream out = new FileOutputStream(file);
            out.write(key.getEncoded());
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(String filePath){
        try{
            byte[] bytes = Files.readAllBytes(new File(filePath).toPath());
            key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Key getKey(){
        return key;
    }

}
