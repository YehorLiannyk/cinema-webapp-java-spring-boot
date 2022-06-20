package yehor.epam.cinema_final_project_spring.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    @ExceptionHandler(Exception.class)
    protected String generalHandler(Exception e, Locale locale, Model model) {
        log.error("Unexpected Exception was occurred");
        return goToErrorPage(DEFAULT_MSG_CODE, null, locale, DEFAULT_STATUS, e, model);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    protected String handleUserAlreadyExistException(Locale locale, HttpServletRequest request, Model model, Throwable e) {
        final int errorStatus = getErrorCodeOrSetDefault(request);
        return goToErrorPage("error.userAlreadyExists", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected String handleAccessDeniedException() {
        log.debug("Handle AccessDeniedException, forward to: /error/access-denied");
        return "redirect:/error/access-denied";
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
        return "forward:/error";
    }
}