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

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpPage(Model model) {
        model.addAttribute("user", new User());
        return HtmlFileConstants.SIGN_UP_PAGE;
    }

    @PostMapping
    // todo: read about DTO obj and create the one for User
    // todo: add User validation
    public String signingUp(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/" + HtmlFileConstants.USER_PROFILE_PAGE;
    }
}
