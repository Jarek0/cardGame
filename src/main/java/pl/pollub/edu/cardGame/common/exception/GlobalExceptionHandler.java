package pl.pollub.edu.cardGame.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import error.view.ErrorsView;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;
import pl.pollub.edu.cardGame.common.exception.impl.ObjectNotFoundException;

import static error.codes.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    )
    {
        BindingResult bindingResult = ex.getBindingResult();
        ErrorsView errors = ErrorsView.of(bindingResult);

        return new ResponseEntity<>(errors, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {BusinessValidationException.class})
    @ResponseStatus(value = NOT_FOUND)
    protected ResponseEntity<String> handleBusinessValidationException(BusinessValidationException ex) {
        return processException(ex, ex.code(), BAD_REQUEST);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    @ResponseStatus(value = NOT_FOUND)
    protected ResponseEntity<String> handleNotFoundException(ObjectNotFoundException ex) {
        return processException(ex, ex.code(), NOT_FOUND);
    }

    @ExceptionHandler(value = {AuthenticationException.class, AccessDeniedException.class})
    @ResponseStatus(value = UNAUTHORIZED)
    protected ResponseEntity<String> handleUnauthorized(Exception ex) {
        return processException(ex, UNAUTHORIZED_REQ, UNAUTHORIZED);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> handleInternalServerError(Exception ex) {
        return processException(ex, FATAL_ERROR, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> processException(Exception ex, String code, HttpStatus status) {
        log.error("exceptions: "+ex.getClass().getSimpleName()+" message: " + ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(code, status);
    }
}
