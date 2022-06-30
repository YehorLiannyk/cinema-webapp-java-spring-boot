package yehor.epam.cinema_final_project_spring.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yehor.epam.cinema_final_project_spring.exceptions.email.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.exceptions.email.EmailException;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmNotFoundException;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreNotFoundException;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.PdfException;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.WritePdfToResponseException;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatIsAlreadyReservedException;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatWasNotPickedException;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionNotFoundException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketNotFoundException;
import yehor.epam.cinema_final_project_spring.exceptions.user.NoRoleException;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserNotFoundException;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String generalHandler(Exception e, Locale locale, Model model) {
        log.error("Unexpected Exception was occurred");
        return goToErrorPage(DEFAULT_MSG_CODE, null, locale, DEFAULT_STATUS, e, model);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, InternalAuthenticationServiceException.class})
    protected String handleUserNotFoundException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.userNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    protected String handleUserAlreadyExistException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.userAlreadyExists", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {PdfException.class})
    protected String handlePDFException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.SERVICE_UNAVAILABLE.value();
        return goToErrorPage("error.pdfException", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {WritePdfToResponseException.class})
    protected String handleWritePdfToResponseException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return goToErrorPage("error.writePdfToResponse", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {SeatWasNotPickedException.class})
    protected String handleSeatWasNotPickedException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.seatWasNotPicked", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {SeatIsAlreadyReservedException.class})
    protected String handleSeatIsAlreadyReservedException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.seatListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {SeatListIsEmptyException.class})
    protected String handleSeatListIsEmptyException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.filmListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected String handleAccessDeniedException() {
        log.debug("Handle AccessDeniedException, redirect to: /error/access-denied");
        return "redirect:/error/access-denied";
    }

    @ExceptionHandler(value = {EmailException.class, CantSendTicketEmailException.class})
    protected String handleEmailException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return goToErrorPage("error.cantSendEmail", null, locale, errorStatus, e, model);
    }


    @ExceptionHandler(value = {FilmListIsEmptyException.class})
    protected String handleFilmListIsEmptyException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.filmListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {FilmNotFoundException.class})
    protected String handleFilmNotFoundException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.filmNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {GenreListIsEmptyException.class})
    protected String handleGenreListIsEmptyException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.genreListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {GenreNotFoundException.class})
    protected String handleGenreNotFoundException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.genreNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {SessionListIsEmptyException.class})
    protected String handleSessionListIsEmptyException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.sessionListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {SessionNotFoundException.class})
    protected String handleSessionNotFoundException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.sessionNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {TicketListIsEmptyException.class})
    protected String handleTicketListIsEmptyException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.BAD_REQUEST.value();
        return goToErrorPage("error.ticketListIsEmpty", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {TicketNotFoundException.class})
    protected String handleTicketNotFoundException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.ticketNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {NoRoleException.class})
    protected String handleNoRoleException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return goToErrorPage("error.roleNotFound", null, locale, errorStatus, e, model);
    }

    @ExceptionHandler(value = {PaginationException.class})
    protected String handlePaginationException(Locale locale, Model model, Throwable e) {
        final int errorStatus = HttpStatus.NOT_FOUND.value();
        return goToErrorPage("error.pagination", null, locale, errorStatus, e, model);
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