package yehor.epam.cinema_final_project_spring.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String DEFAULT_MSG_CODE = "error.smthWentWrong";
    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {Throwable.class})
    protected String generalHandler(Throwable e, Locale locale, Model model) {
        log.error("Unexpected exception was occurred");
        return goToErrorPage(DEFAULT_MSG_CODE, null, locale, DEFAULT_STATUS, e, model);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    protected String handleUserAlreadyExistException(Locale locale, HttpServletRequest request, Model model, Throwable e) {
        final int errorStatus = getErrorCodeOrSetDefault(request);
        return goToErrorPage("error.userAlreadyExists", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected String handleAccessDeniedException(Locale locale, HttpServletRequest request, Model model, Throwable e) {
        final int errorStatus = HttpStatus.FORBIDDEN.value();
        return goToErrorPage("error.haveNoEnoughPermits", null, locale, errorStatus, e, model);
    }

    private int getErrorCodeOrSetDefault(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return status != null ? (int) status : DEFAULT_STATUS;
    }

    private String goToErrorPage(String messageCode, Object[] args, Locale locale, int errorStatus, Throwable e, Model model) {
        final String errorMessage = messageSource.getMessage(messageCode, args, locale);
        model.addAttribute("errorCode", errorStatus);
        model.addAttribute("errorMessage", errorMessage);
        log.error("Handled exception: ", e);
        return HtmlFileConstants.ERROR_PAGE;
    }
}