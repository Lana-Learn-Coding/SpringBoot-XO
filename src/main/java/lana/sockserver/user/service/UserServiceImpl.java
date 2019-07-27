package lana.sockserver.user.service;

import lana.sockserver.user.User;
import lana.sockserver.user.UserRepo;
import lana.sockserver.user.role.Role;
import lana.sockserver.user.role.RoleEntity;
import lana.sockserver.user.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    @Override
    public User get(User user) throws UserNotExistException {
        String username = user.getName();
        Integer userId = user.getId();
        // if both username and id exist then check if those match with the info in database
        // else try to get the user based on username or id.
        if (userId != null && username != null) {
            User userEntity = get(userId);
            if (!userEntity.getName().equals(username)) throw new UserNotExistException();
            return userEntity;
        } else if (userId != null) {
            return get(userId);
        } else if (username != null) {
            return get(username);
        }
        // missing both username and id info, basically can't find the user.
        throw new UserNotExistException();
    }

    @Override
    public User get(String username) throws UserNotExistException {
        return userRepo.findByName(username).orElseThrow(UserNotExistException::new);
    }

    @Override
    public User get(Integer id) throws UserNotExistException {
        return userRepo.findById(id).orElseThrow(UserNotExistException::new);
    }

    @Override
    public User create(User user) throws UserExistException {
        if (userExist(user) || userRepo.existsByName(user.getName())) throw new UserExistException();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.get(Role.USER));
        user.setRoles(roles);
        return userRepo.save(user);
    }

    @Override
    public void save(User user) {
        // only check the id as this method is
        // designed to update the user info.
        if (userExist(user)) {
            userRepo.save(user);
        }
    }

    private boolean userExist(User user) {
        Integer userId = user.getId();
        return (userId != null) && (userRepo.existsById(userId));
    }
}
