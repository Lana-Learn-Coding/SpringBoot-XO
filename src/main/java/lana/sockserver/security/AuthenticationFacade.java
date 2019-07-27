package lana.sockserver.security;

import lana.sockserver.user.User;
import lana.sockserver.user.service.UserNotExistException;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    User getAuthenticatedUser() throws UserNotExistException;
}
