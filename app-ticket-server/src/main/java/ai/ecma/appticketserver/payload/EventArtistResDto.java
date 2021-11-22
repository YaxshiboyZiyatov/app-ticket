package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Artist;
import ai.ecma.appticketserver.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventArtistResDto {

    private UUID id;

    private Event event;

    private Artist artist;

    private boolean mainArtist; //Bosh artistmi?
}
