package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Attachment;
import ai.ecma.appticketserver.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 17.09.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventResDto {
    private UUID id;
    private String name;

    private String banner;

    private String schema;

    private PlaceResDto place;

    private String description;
}
