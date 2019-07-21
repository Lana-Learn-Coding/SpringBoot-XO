package lana.sockserver.user;

import lana.sockserver.user.exception.UserExistException;
import lana.sockserver.user.exception.UserNotExistException;
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
    public UserEntity get(UserEntity user) throws UserNotExistException {
        String username = user.getName();
        Integer userId = user.getId();
        // if both username and id exist then check if those match with the info in database
        // else try to get the user based on username or id.
        if (userId != null && username != null) {
            UserEntity userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
            if (!userEntity.getName().equals(username)) throw new UserNotExistException();
            return userEntity;
        } else if (userId != null) {
            return userRepo.findById(userId).orElseThrow(UserNotExistException::new);
        } else if (username != null) {
            return userRepo.findByName(username).orElseThrow(UserNotExistException::new);
        }
        // missing both username and id info, basically can't find the user.
        throw new UserNotExistException();
    }

    @Override
    public UserEntity create(UserEntity user) throws UserExistException {
        if (userExist(user) || userRepo.existsByName(user.getName())) {
            throw new UserExistException();
        }
        user.setSalt(this.generateSalt());
        user.setPassword(this.generateHash(user.getPassword(), user.getSalt()));
        return userRepo.save(user);
    }

    @Override
    public void save(UserEntity user) {
        // only check the id as this method is
        // designed to update the user info.
        if (userExist(user)) {
            userRepo.save(user);
        }
    }

    @Override
    public boolean authorize(UserEntity user) {
        String username = user.getName();
        if (username != null) {
            try {
                // the get() method only find user that match name or id
                // cause the username already exist so this method also complete the username check step
                UserEntity userInfo = this.get(user);
                String salt = userInfo.getSalt();
                String validHash = userInfo.getPassword();
                String hash = generateHash(user.getPassword(), salt);
                return hash.equals(validHash);
            } catch (UserNotExistException e) {
                // continue and simply return false as username not found.
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
            // this should never happen
            throw new RuntimeException(e);
        }
    }
}
