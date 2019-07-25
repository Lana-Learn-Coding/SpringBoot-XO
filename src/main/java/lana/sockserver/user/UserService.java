package lana.sockserver.user;


public interface UserService {
    // get the user by name or id, in case one of those property is missing.
    User get(User user) throws UserNotExistException;

    User create(User user) throws UserExistException;

    void save(User user);
}
