package glit.net.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class AesKey{

    private SecretKey key;

    public void set(SecretKey key){
        this.key = key;
    }

    public void set(byte[] encoded){
        try{
            key = new SecretKeySpec(encoded, 0, encoded.length, "AES");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void generate(int size){
        try{
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(size);
            key = generator.generateKey();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] data){
        try{
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            return encryptCipher.doFinal(data);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] data){
        try{
            Cipher rsa = Cipher.getInstance("AES");
            rsa.init(Cipher.DECRYPT_MODE, key);
            return rsa.doFinal(data);

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
            key = new SecretKeySpec(bytes, 0, bytes.length, "AES");
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public SecretKey getKey(){
        return key;
    }

}
