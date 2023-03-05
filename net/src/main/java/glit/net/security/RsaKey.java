package glit.net.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RsaKey{

    private final PublicRsa pub;
    private final PrivateRsa pvt;

    public RsaKey(){
        pub = new PublicRsa();
        pvt = new PrivateRsa();
    }

    public void generate(int size){
        try{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(size);
            KeyPair kp = kpg.generateKeyPair();

            pub.set(kp.getPublic());
            pvt.set(kp.getPrivate());

        }catch(NoSuchAlgorithmException ignored){}
    }

    public boolean save(String filePath){
        if(!pub.save(filePath))
            return false;

        return pvt.save(filePath);
    }

    public boolean load(String filePath){
        if(!pub.load(filePath))
            return false;

        return pvt.load(filePath);
    }

    public byte[] encrypt(byte[] data){
        return pub.encrypt(data);
    }

    public byte[] decrypt(byte[] data){
        return pvt.decrypt(data);
    }

    public PublicRsa getPublic(){
        return pub;
    }

    public PrivateRsa getPrivate(){
        return pvt;
    }

}
