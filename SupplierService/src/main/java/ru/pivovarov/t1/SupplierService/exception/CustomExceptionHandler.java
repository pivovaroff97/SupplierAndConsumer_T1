package ru.pivovarov.t1.SupplierService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleEntityNotFound(
            NoSuchElementException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({NoResourceFoundException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleNoHandlerFound(Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception exception) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getLocalizedMessage(),
                List.of(exception.getClass().getName(), "error occurred"));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
