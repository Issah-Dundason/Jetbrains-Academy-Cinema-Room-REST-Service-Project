package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CinemaException.class)
    public ResponseEntity<?> handlePurchasedException(CinemaException exception) {
       return new ResponseEntity<>(Map.of("error", exception.getError()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPasswordException(WrongPasswordException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}

