package lana.sockserver.user;


import lana.sockserver.user.model.User;
import lana.sockserver.user.model.UserForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("user", new UserForm());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserForm user, Model model) {
        User userEntity = modelMapper.map(user, User.class);
        if (userService.authorize(userEntity)) {
            // TODO: add UserDTO and redirect to /home
            return "home";
        }
        model.addAttribute("error", "Wrong username or password");
        return "user/login";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new UserForm());
        return "user/signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("user") UserForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signUp";
        }
        User userEntity = modelMapper.map(user, User.class);
        try {
            userService.create(userEntity);
        } catch (Exception e) {
            return "user/signUp";
        }
        return "redirect:/login";
    }
}
