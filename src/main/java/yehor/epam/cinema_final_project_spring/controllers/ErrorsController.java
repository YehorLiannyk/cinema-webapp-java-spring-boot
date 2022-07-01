package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.RequestDispatcher;

import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.*;

@Controller
@RequestMapping("/error")
public class ErrorsController implements ErrorController {
    @RequestMapping("/access-denied")
    public String accessDenied() {
        return HtmlFileConstants.ERROR_ACCESS_DENIED_PAGE;
    }

    @RequestMapping
    public String handleGeneralError(@RequestAttribute(name = RequestDispatcher.ERROR_STATUS_CODE, required = false) Integer status) {
        if (status != null) {
            if (status == HttpStatus.NOT_FOUND.value()) {
                return ERROR_NOT_FOUND_PAGE;
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_INTERNAL_PAGE;
            } else if (status == HttpStatus.FORBIDDEN.value()) {
                return ERROR_ACCESS_DENIED_PAGE;
            } else if (status == HttpStatus.BAD_REQUEST.value()) {
                return ERROR_BAD_REQUEST_PAGE;
            }
        }
        return HtmlFileConstants.ERROR_PAGE;
    }
}
