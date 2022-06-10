package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;
import yehor.epam.cinema_final_project_spring.dto.UserLoginDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.AuthException;
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
    public String loginPage(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return HtmlFileConstants.LOGIN_PAGE;
    }

    @PostMapping
    // todo: add User validation
    public String login(@ModelAttribute UserLoginDTO userLoginDTO) {
        final Optional<User> optional = userService.getByLoginAndPass(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        User user = optional.orElseThrow(AuthException::new);

        return "redirect:/" + HtmlFileConstants.SIGN_UP_PAGE;
    }
}
