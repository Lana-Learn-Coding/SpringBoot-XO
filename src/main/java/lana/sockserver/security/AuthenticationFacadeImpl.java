package lana.sockserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private static AuthenticationFacadeImpl ourInstance = new AuthenticationFacadeImpl();

    private AuthenticationFacadeImpl() {
    }

    public static AuthenticationFacadeImpl getInstance() {
        return ourInstance;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
