package lana.sockserver.main;

import lana.sockserver.user.User;
import lana.sockserver.user.UserNotExistException;
import lana.sockserver.user.UserService;
import lana.sockserver.util.objectmapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public UserDTO user(Principal principal) {
        try {
            User user = userService.get(principal.getName());
            return objectMapper.map(user, UserDTO.class);
        } catch (UserNotExistException e) {
            // user must exist so it is in the principal :<
            // but if this happen, return empty user
            return new UserDTO();
        }
    }

}
