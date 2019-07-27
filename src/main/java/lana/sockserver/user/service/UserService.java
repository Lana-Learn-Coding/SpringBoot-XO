package lana.sockserver.user.service;


import lana.sockserver.user.User;

public interface UserService {
    // get the user by name or id, in case one of those property is missing.
    User get(User user) throws UserNotExistException;

    User get(String username) throws UserNotExistException;

    User get(Integer id) throws UserNotExistException;

    User create(User user) throws UserExistException;

    void save(User user);
}
