package lana.sockserver.main;


import lana.sockserver.user.UserService;
import lana.sockserver.util.objectmapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Principal principal, Authentication auth, Model model) {
        return "home";
    }
}
