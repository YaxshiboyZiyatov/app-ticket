package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResDto {
    private UUID id;
    private String name;

    private String description;

    private String url;
}
