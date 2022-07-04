package yehor.epam.cinema_final_project_spring.exceptions.pdf;

import lombok.experimental.StandardException;

/**
 * Exception is occurred when cannot write {@link java.io.ByteArrayOutputStream} to {@link javax.servlet.http.HttpServletResponse}
 */
@StandardException
public class WritePdfToResponseException extends RuntimeException {
}
