package lana.sockserver.util.hashing;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashingUtilImpl implements HashingUtil {
    @Override
    public String hash(String input) {
        PBEKeySpec keySpec = new PBEKeySpec(input.toCharArray());
        return generateSecret(keySpec);
    }

    @Override
    public String hash(String input, String salt) {
        byte[] base64DecodedSalt = Base64.getDecoder().decode(salt);
        PBEKeySpec keySpec = new PBEKeySpec(input.toCharArray(), base64DecodedSalt, 64000, 128);
        return generateSecret(keySpec);
    }

    private String generateSecret(PBEKeySpec keySpec) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(keySpec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String random() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] random = new byte[16];
        secureRandom.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }

}
