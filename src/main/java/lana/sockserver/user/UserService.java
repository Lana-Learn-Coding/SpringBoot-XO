package lana.sockserver.user;


import lana.sockserver.user.model.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);

    boolean authorize(UserEntity user);
}
