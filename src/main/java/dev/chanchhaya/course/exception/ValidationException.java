package dev.chanchhaya.course.exception;

import dev.chanchhaya.course.base.BasedError;
import dev.chanchhaya.course.base.BasedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BasedResponse<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<Map<String, Object>> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("reason", fieldError.getDefaultMessage());
                    errors.add(error);
                });

        BasedError<?> basedError = BasedError.builder()
                .code(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .description(errors)
                .build();

        return BasedResponse.builder()
                .errors(basedError)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BasedResponse<?> handleHttpMessageNotReadableError(HttpMessageNotReadableException ex) {
        BasedError<?> basedError = BasedError.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .description("The data types of submitted values don't match what the API expects")
                .build();
        return BasedResponse.builder()
                .errors(basedError)
                .build();
    }

}
