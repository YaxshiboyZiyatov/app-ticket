package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

}
