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
public class ForgotPasswordDto {
    @NotBlank(message = "Phone number required!")
    @Pattern(regexp = "^\\+998[0-9]{2}[0-9]{7}$")
    private String phoneNumber;

    @NotBlank(message = "Verification code required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Code wrong format")
    private String code;

    @NotBlank(message = "New password required")
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9]).{8,16}$", message = "Parol kamida 8 xonali, ikita harf, bitta belgi va ikkita raqamdan iborat bo'lishi kerak")
    private String newPassword;

    @NotBlank(message = "Pre password required")
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9]).{8,16}$", message = "Parol kamida 8 xonali, ikita harf, bitta belgi va ikkita raqamdan iborat bo'lishi kerak")
    private String prePassword;

    public ForgotPasswordDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
