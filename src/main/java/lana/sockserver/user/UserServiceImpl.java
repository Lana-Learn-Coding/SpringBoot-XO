package lana.sockserver.user;

import lana.sockserver.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (!userExist(user)) {
            user.setSalt(this.generateSalt());
            user.setPassword(this.generateHash(user.getPassword(), user.getSalt()));
        }
        // now is put but we need use patch here in case
        // the user is already exist
        return userRepo.save(user);
    }

    @Override
    public boolean authorize(UserEntity user) {
        if (userExist(user)) {
            // already check user exist above so this should never null
            // in case something wrong happened return false.
            UserEntity userInfo = userRepo.findById(user.getId()).orElse(null);
            if (userInfo != null) {
                String salt = userInfo.getSalt();
                String validHash = userInfo.getPassword();
                String hash = generateHash(user.getPassword(), salt);
                if (hash.equals(validHash)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean userExist(UserEntity user) {
        Integer userId = user.getId();
        return (userId != null) && (userRepo.existsById(userId));
    }


    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String generateHash(String password, String salt) {
        byte[] saltByte = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltByte, 64000, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            // this will never happen
            throw new RuntimeException(e);
        }
    }
}
