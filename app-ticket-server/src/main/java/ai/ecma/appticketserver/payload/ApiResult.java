package ai.ecma.appticketserver.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private boolean success;
    private String message;
    private T data;
    private List<ErrorData> errorData;

    public ApiResult(boolean success) {
        this.success = success;
    }

    public ApiResult(boolean success, String message, T object) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResult(String message, T data, boolean success) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResult(boolean success, List<ErrorData> errorData) {
        this.success = success;
        this.errorData = errorData;
    }

    public static <T> ApiResult<T> successResponse(String message, T data) {
        return new ApiResult<>(message, data, true);
    }

    public static <T> ApiResult<T> successResponse(String message) {
        return new ApiResult<>(message, null, true);
    }

    public static <T> ApiResult<T> successResponse(T data) {
        return new ApiResult<>(true, data);
    }

    public static ApiResult successResponse() {
        return new ApiResult(true);
    }

    public static <T> ApiResult<T> failResponse(String message) {
        return new ApiResult<T>(
                message,
                null,
                false
        );
    }

    public static <T> ApiResult<T> failResponse(String message, T data) {
        return new ApiResult<T>(
                message,
                data,
                false
        );
    }

    public static <T> ApiResult<T> failResponse(List<ErrorData> errorData) {
        return new ApiResult<T>(false, errorData);
    }
}
