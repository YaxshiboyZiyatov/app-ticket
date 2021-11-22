package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Artist;
import ai.ecma.appticketserver.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventArtistReqDto {

    @NotNull
    private UUID eventId;

    @NotNull
    private UUID artistId;

    @NotNull
    private boolean mainArtist; //Bosh artistmi?
}

