package ai.ecma.appticketserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class RestException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private Object object;

    public RestException(String message, HttpStatus status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }

    public RestException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }


}
