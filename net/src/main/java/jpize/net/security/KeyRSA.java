package jpize.net.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyRSA{

    private final PublicRSA publicKey;
    private final PrivateRSA privateKey;

    public KeyRSA(int size){
        try{
            final KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
            pairGenerator.initialize(size);
            final KeyPair pair = pairGenerator.generateKeyPair();
            
            publicKey = new PublicRSA(pair.getPublic());
            privateKey = new PrivateRSA(pair.getPrivate());
            
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] bytes){
        return publicKey.encrypt(bytes);
    }

    public byte[] decrypt(byte[] bytes){
        return privateKey.decrypt(bytes);
    }

    public PublicRSA getPublic(){
        return publicKey;
    }

    public PrivateRSA getPrivate(){
        return privateKey;
    }

}
