package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.EventArtist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventArtistRepository extends JpaRepository<EventArtist, UUID> {
}
