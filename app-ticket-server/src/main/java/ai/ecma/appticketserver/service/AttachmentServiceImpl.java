package ai.ecma.appticketserver.service;


import ai.ecma.appticketserver.entity.Attachment;
import ai.ecma.appticketserver.entity.AttachmentContent;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.AttachmentResDto;
import ai.ecma.appticketserver.repository.AttachmentContentRepository;
import ai.ecma.appticketserver.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) {
        try {
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                Attachment attachment = new Attachment(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType());
                attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent(attachment, file.getBytes());
                attachmentContentRepository.save(attachmentContent);
                return ApiResult.successResponse(attachment.getId());
            }
        } catch (IOException e) {
            throw new RestException("Fileni getByte error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return null;
    }

    @Override
    public ApiResult<?> getPhoto(UUID id, HttpServletResponse response) {
        try {
            Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException("Photo not found", HttpStatus.NOT_FOUND));
            AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId()).orElseThrow(() -> new RestException("Photo not found", HttpStatus.NOT_FOUND));

            response.setHeader("Content-Disposition",
                    "Attachment; filename=\"" + attachment.getName() + "\"");
            response.setContentType(attachment.getContentType());

            FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());

        } catch (IOException e) {
            throw new RestException("Server error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return ApiResult.successResponse("Photo frontedga ketdi");
    }

    @Override
    public ApiResult<?> deleted(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException("Photo not found", HttpStatus.NOT_FOUND));
        boolean b = attachmentContentRepository.deleteByAttachmentId(attachment.getId());
        return ApiResult.successResponse(b);
    }

    @Override
    public ApiResult<?> get(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new RestException("Photo not found", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(attachmentResDtoMapper(attachment));
    }

    private AttachmentResDto attachmentResDtoMapper(Attachment attachment) {
        return new AttachmentResDto(attachment.getId(), attachment.getName(), attachment.getSize(), attachment.getContentType());
    }
}
