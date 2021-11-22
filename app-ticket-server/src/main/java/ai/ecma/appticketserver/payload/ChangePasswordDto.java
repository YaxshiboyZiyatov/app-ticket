package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Muhammad Mo'minov
 * 11.10.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDto {
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9]).{8,16}$", message = "Parol kamida 8 xonali, ikita harf, bitta belgi va ikkita raqamdan iborat bo'lishi kerak")
    @NotBlank(message = "Old password required")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9]).{8,16}$", message = "Parol kamida 8 xonali, ikita harf, bitta belgi va ikkita raqamdan iborat bo'lishi kerak")
    @NotBlank(message = "New password required")
    private String newPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9]).{8,16}$", message = "Parol kamida 8 xonali, ikita harf, bitta belgi va ikkita raqamdan iborat bo'lishi kerak")
    @NotBlank(message = "Pre required")
    private String prePassword;
}