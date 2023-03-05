package glit.net.security;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateRsa{

    private Key key;

    public void set(Key key){
        this.key = key;
    }

    public void set(byte[] encoded){
        try{
            key = KeyFactory.getInstance("RSA").generatePublic(new PKCS8EncodedKeySpec(encoded));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public byte[] decrypt(byte[] data){
        try{
            Cipher rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.DECRYPT_MODE, key);
            return rsa.doFinal(data);

        }catch(Exception e){
            e.printStackTrace();
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
            key = KeyFactory.getInstance("RSA").generatePublic(new PKCS8EncodedKeySpec(bytes));
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
