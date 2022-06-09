package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.constants.HtmlFileConstants;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.services.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage() {
        return HtmlFileConstants.LOGIN_PAGE;
    }

    @PostMapping
    // todo: read about DTO obj and create the one for User
    // todo: add User validation
    public String login(@ModelAttribute User user) {
        final Optional<User> optional = userService.getByLoginAndPass(user.getEmail(), user.getPassword());
        //todo: optional User
        return "redirect:/" + HtmlFileConstants.USER_PROFILE_PAGE;
    }
}
