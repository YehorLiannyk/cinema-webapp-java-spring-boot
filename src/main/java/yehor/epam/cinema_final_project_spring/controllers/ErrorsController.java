package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

@Controller
@RequestMapping("/error")
public class ErrorsController implements ErrorController {
    @RequestMapping("/access-denied")
    public String accessDenied() {
        return HtmlFileConstants.ACCESS_DENIED_PAGE;
    }

    @RequestMapping
    public String handleGeneralError(Model model) {
        return HtmlFileConstants.ERROR_PAGE;
    }
}
