package lana.sockserver.user;


import org.apache.catalina.User;

public interface UserService {
    User save(User user);
}
