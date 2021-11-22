package ai.ecma.appticketserver.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeDto {
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String code;
}
