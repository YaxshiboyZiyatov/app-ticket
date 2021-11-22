package ai.ecma.appticketserver.exception;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.ErrorData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public HttpEntity<ApiResult<?>> handleException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<ErrorData> errorData = new ArrayList<>(errors.size());
        for (ObjectError error : errors) {
            errorData.add(new ErrorData(error.getDefaultMessage(), 400));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failResponse(errorData));
    }

    @ExceptionHandler(value = {RestException.class})
    public HttpEntity<ApiResult<?>> handleException(RestException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                new ApiResult<>(
                        false,
                        ex.getMessage(),
                        ex.getObject()
                )
        );
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public HttpEntity<ApiResult<?>> handleException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResult.failResponse("Kirish mumkin emas")
        );
    }



    @ExceptionHandler(value = {Exception.class})
    public HttpEntity<ApiResult<?>> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResult<>(
                        false,
                        "Server error"
                )
        );
    }
}
