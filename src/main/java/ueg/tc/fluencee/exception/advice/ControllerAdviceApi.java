package ueg.tc.fluencee.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ueg.tc.fluencee.exception.BusinessException;

@ControllerAdvice
public class ControllerAdviceApi {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> resourceNotFound(BusinessException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
