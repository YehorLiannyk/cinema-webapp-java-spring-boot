package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.services.UserService;

@Controller
@Slf4j
public class SignupController {
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("user", new UserSignUpDTO());
        return HtmlFileConstants.SIGN_UP_PAGE;
    }

    // todo: add User validation
    @PostMapping("/signup")
    public String signingUp(@ModelAttribute UserSignUpDTO userDto) {
        userService.save(userDto);
        return "redirect:/" + HtmlFileConstants.USER_PROFILE_PAGE;
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
