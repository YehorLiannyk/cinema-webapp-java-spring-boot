package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.dto.UserLoginDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

@Slf4j
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

    /*@PostMapping
    // todo: add User validation
    public String login(@ModelAttribute UserLoginDTO userLoginDTO) {
        User user = userService.getByLoginAndPass(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return "redirect:/" + HtmlFileConstants.SIGN_UP_PAGE;
    }*/
}
