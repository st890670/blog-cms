package jason.idv.blog.crypt;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Component;

@Component
public class RSACrypt {

    private Cipher cipher;

    public RSACrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("RSA");
    }

    public KeyPair genarateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public <T extends PublicKey> byte[] encrypt(T publicKey, String plainContent)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return cipher.doFinal(plainContent.getBytes());
    }

    public <T extends PrivateKey> String decrypt(T privateKey, byte[] encryptedContent)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(encryptedContent));
    }

}
