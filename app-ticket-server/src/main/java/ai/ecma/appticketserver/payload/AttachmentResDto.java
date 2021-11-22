package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResDto {

    private UUID id;

    private String name;

    private Long size;

    private String contentType;

}
