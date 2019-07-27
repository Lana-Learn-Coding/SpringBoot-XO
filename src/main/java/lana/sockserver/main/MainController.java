package lana.sockserver.main;


import lana.sockserver.security.AuthenticationFacade;
import lana.sockserver.user.controller.UserDTO;
import lana.sockserver.user.service.UserNotExistException;
import lana.sockserver.util.objectmapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {
    private final AuthenticationFacade authenticationFacade;
    private final ObjectMapper objectMapper;

    @Autowired
    public MainController(ObjectMapper objectMapper, AuthenticationFacade authenticationFacade) {
        this.objectMapper = objectMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model) {
        UserDTO userDTO = new UserDTO();
        try {
            objectMapper.map(authenticationFacade.getAuthenticatedUser(), userDTO);
            model.addAttribute("user", userDTO);
            return "home";
        } catch (UserNotExistException e) {
            return "home";
        }
    }
}
