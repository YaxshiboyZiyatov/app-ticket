package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(AppConstant.AUTH_CONTROLLER)
@Tag(name = "Auth controller", description = "Bu auth controller")
public interface AuthController {

    @PostMapping("/login")
    ApiResult<TokenDto> login(@RequestBody LoginDto loginDto);

    @GetMapping("/check-phone/{phone}")
    ApiResult<?> checkPhone(@PathVariable String phone);

    @PostMapping("/check-sms")
    ApiResult<Registered> checkSms(@RequestBody @Valid CodeDto codeDto);

    @PostMapping("/sign-up")
    ApiResult<TokenDto> signUp(@RequestBody @Valid RegisterDto registerDto);

    @PostMapping("/refresh-token")
    ApiResult<TokenDto> refreshToken(@RequestBody @Valid TokenDto tokenDto);

    @PutMapping("/forgot-password")
    ApiResult<?> forgotPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto);

    @PutMapping("/complete-forgot-password")
    ApiResult<?> forgotPasswordStepTwo(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto);
}
