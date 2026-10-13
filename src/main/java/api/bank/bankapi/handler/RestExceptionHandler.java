package api.bank.bankapi.handler;

import api.bank.bankapi.exception.IllegalArgumentExceptionDetails;
import api.bank.bankapi.exception.ValidationExceptionDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentExceptionDetails> handleIllegalArgumentException(IllegalArgumentException iae) {
        return new ResponseEntity<>(IllegalArgumentExceptionDetails
                .builder()
                .title("Illegal Argument Exception")
                .status(HttpStatus.BAD_REQUEST.value())
                .details(iae.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((e -> errors.put(e.getField(), e.getDefaultMessage())));
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<IllegalArgumentExceptionDetails>  handleDataIntegrityViolationException(DataIntegrityViolationException dive) {
        return new ResponseEntity<>(IllegalArgumentExceptionDetails.builder().title("Invalid values")
                .status(HttpStatus.CONFLICT.value()).details("Any information is invalid").timestamp(LocalDateTime.now()).build(), HttpStatus.CONFLICT);
    }

}
