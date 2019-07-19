package lana.sockserver.user;


import lana.sockserver.user.model.UserEntity;
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
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserForm user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        if (userService.authorize(userEntity)) {
            return "ok";
        }
        return "login";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new UserForm());
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("user") UserForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userService.save(userEntity);
        return "redirect:/login";
    }
}
