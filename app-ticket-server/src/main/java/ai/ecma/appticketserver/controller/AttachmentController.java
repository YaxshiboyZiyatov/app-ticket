package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequestMapping(AppConstant.ATTACHMENT_CONTROLLER)
@Tag(name = "Attachment controller", description = "Bu attachment controller")
public interface AttachmentController {

    @PostMapping
    ApiResult<?> upload(MultipartHttpServletRequest request);

    @GetMapping("/download/{id}")
    ApiResult<?> getPhoto(@PathVariable UUID id, HttpServletResponse response);

    @DeleteMapping("/deleted/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @GetMapping("/get/{id}")
    ApiResult<?> get(@PathVariable UUID id);

}
