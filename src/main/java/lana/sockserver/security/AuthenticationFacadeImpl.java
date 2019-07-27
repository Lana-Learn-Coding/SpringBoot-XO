package lana.sockserver.security;

import lana.sockserver.user.User;
import lana.sockserver.user.UserRepo;
import lana.sockserver.user.service.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private UserRepo userRepo;
    private static AuthenticationFacadeImpl ourInstance = new AuthenticationFacadeImpl();

    private AuthenticationFacadeImpl() {
    }

    public static AuthenticationFacadeImpl getInstance() {
        return ourInstance;
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getAuthenticatedUser() throws UserNotExistException {
        String username = getAuthentication().getName();
        return userRepo
                .findByName(username)
                .orElseThrow(UserNotExistException::new);
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
