package dev.chanchhaya.course.exception;

import dev.chanchhaya.course.base.BasedError;
import dev.chanchhaya.course.base.BasedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusEx(ResponseStatusException ex) {
        log.info("handleResponseStatusEx: {}", ex.getReason());
        Map<String, Object> error = new HashMap<>();
        error.put("code", ex.getStatusCode());
        error.put("description", ex.getReason());
        return new ResponseEntity<>(new ErrorResponse<>(error), ex.getStatusCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateCategoryException.class)
    public BasedResponse<?> handleDuplicateCategoryException(DuplicateCategoryException e) {
        // Customize the response for the DuplicateCategoryException

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("name", "category");
        errorDetails.put("message", e.getMessage());

        return BasedResponse.builder()
                .errors(errorDetails)
                .build();
    }

}
