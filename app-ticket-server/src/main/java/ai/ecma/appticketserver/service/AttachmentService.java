package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {

    ApiResult<?> upload(MultipartHttpServletRequest request);

    ApiResult<?> getPhoto(UUID id, HttpServletResponse response);

    ApiResult<?> deleted(UUID id);

    ApiResult<?> get(UUID id);
}
