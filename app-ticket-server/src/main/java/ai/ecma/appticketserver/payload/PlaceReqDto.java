package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceReqDto {
    @NotBlank
    private String address;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;

}
