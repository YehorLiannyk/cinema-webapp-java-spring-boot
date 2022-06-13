package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return HtmlFileConstants.LOGIN_PAGE;
        } else {
            log.info("Authorized user tried to get login page");
            throw new AccessDeniedException("Authorized user tried to get login page");
        }
    }

    /*@PostMapping
    // add User validation
    public String login(@ModelAttribute UserLoginDTO userLoginDTO) {
        User user = userService.getByLoginAndPass(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return "redirect:/" + HtmlFileConstants.SIGN_UP_PAGE;
    }*/
}
