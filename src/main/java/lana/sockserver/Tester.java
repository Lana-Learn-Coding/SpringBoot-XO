package lana.sockserver;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import java.util.Base64;

import lana.sockserver.user.UserServiceImpl;

public class Tester {
    public static void main(String[] args) {
        UserServiceImpl hashit = new UserServiceImpl();
        String pw = "123456";
        byte[] salt = hashit.generateSalt();
        byte[] hash = hashit.generateHash(pw, salt);
        System.out.println(Base64.getEncoder().encodeToString(hash));
    }
}
