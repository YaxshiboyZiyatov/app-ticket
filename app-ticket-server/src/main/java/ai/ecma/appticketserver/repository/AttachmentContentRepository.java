package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

    Optional<AttachmentContent> findByAttachmentId(UUID attachment_id);

    boolean deleteByAttachmentId(UUID attachment_id);

}
