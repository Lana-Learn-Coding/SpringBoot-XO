package lana.sockserver.user;


import lana.sockserver.user.exception.UserExistException;
import lana.sockserver.user.exception.UserNotExistException;
import lana.sockserver.user.model.UserEntity;

public interface UserService {
    // get the user by name or id, in case one of those property is missing.
    UserEntity get(UserEntity user) throws UserNotExistException;

    UserEntity create(UserEntity user) throws UserExistException;

    void save(UserEntity user);

    boolean authorize(UserEntity user);
}
