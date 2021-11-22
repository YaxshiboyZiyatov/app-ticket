package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistReqDto {
    private String name;

    private String description;

    private UUID photoId;
}
