package lana.sockserver.user;

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
            User userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
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

    @Override
    public boolean authorize(User user) {
        String username = user.getName();
        if (username != null) {
            try {
                // the get() method only find user that match name or id
                // cause the username already exist so this method also complete the username check step
                User userInfo = this.get(user);
                return passwordEncoder.matches(user.getPassword(), userInfo.getPassword());
            } catch (UserNotExistException e) {
                // continue and simply return false as username not found.
            }
        }
        return false;
    }


    private boolean userExist(User user) {
        Integer userId = user.getId();
        return (userId != null) && (userRepo.existsById(userId));
    }
}
