package ueg.tc.fluencee.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private final ErrorMessageCode errorMessageCode;

    public BusinessException(ErrorMessageCode errorMessageCode){
        super();
        this.errorMessageCode = errorMessageCode;
    }

    public String getMessage() {
        return errorMessageCode.getMessage();
    }

    public HttpStatus getStatus(){
        return errorMessageCode.getStatus();
    }
}
