package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

@Controller
@RequestMapping("/error")
public class ErrorsController {
    @RequestMapping("/access-denied")
    public String accessDenied() {
        return HtmlFileConstants.ACCESS_DENIED_PAGE;
    }
}
