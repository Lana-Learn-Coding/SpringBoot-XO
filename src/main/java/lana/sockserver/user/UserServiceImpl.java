package lana.sockserver.user;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Override
    public User save(User user) {
        return null;
    }

    public byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    public byte[] generateHash(String password, byte[] salt) {
        final String PBKDF2Hash = "PBKDF2WithHmacSHA1";
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 64000, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2Hash);
            return factory.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            // this will never happen
            throw new RuntimeException(e);
        }
    }
}
