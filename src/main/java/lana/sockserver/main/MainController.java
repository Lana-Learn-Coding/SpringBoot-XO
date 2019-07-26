package lana.sockserver.main;

import lana.sockserver.user.User;
import lana.sockserver.user.UserNotExistException;
import lana.sockserver.user.UserService;
import lana.sockserver.util.objectmapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class MainController {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public MainController(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @ModelAttribute
    public UserDTO user(Principal principal, Authentication authentication) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            User user = userService.get(principal.getName());
            return objectMapper.map(user, UserDTO.class);
        } catch (UserNotExistException e) {
            // user must exist so it is in the principal :<
            // but if this happen, return null
            return null;
        }
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home() {
        return "home";
    }
}
