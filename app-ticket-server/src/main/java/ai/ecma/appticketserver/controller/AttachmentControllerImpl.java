package ai.ecma.appticketserver.controller;


import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController {
    private final AttachmentService attachmentService;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) {
        return attachmentService.upload(request);
    }

    @Override
    public ApiResult<?> getPhoto(UUID id, HttpServletResponse response) {
        return attachmentService.getPhoto(id, response);
    }

    @PreAuthorize("hasAuthority('DELETE_ATTACHMENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return attachmentService.deleted(id);
    }

    @Override
    public ApiResult<?> get(UUID id) {
        return attachmentService.get(id);
    }


}
