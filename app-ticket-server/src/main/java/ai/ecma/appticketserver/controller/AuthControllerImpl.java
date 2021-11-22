package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ApiResult<TokenDto> login(LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @Override
    public ApiResult<?> checkPhone(String phone) {
        return authService.checkPhone(phone);
    }

    @Override
    public ApiResult<Registered> checkSms(CodeDto codeDto) {
        return authService.checkSms(codeDto);
    }

    @Override
    public ApiResult<TokenDto> signUp(RegisterDto registerDto) {
        return authService.signUp(registerDto);
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        log.info("Kimdir tokenni refresh qilyapti. Token: {}", tokenDto);
        ApiResult<TokenDto> tokenDtoApiResult = authService.refreshToken(tokenDto);
        log.info("Tokenni refresh qildi: {}", tokenDtoApiResult.getData());
        return tokenDtoApiResult;
    }

    @Override
    public ApiResult<?> forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        return authService.forgotPassword(forgotPasswordDto);
    }

    @Override
    public ApiResult<?> forgotPasswordStepTwo(ForgotPasswordDto forgotPasswordDto) {
        return authService.replacePassword(forgotPasswordDto);
    }
}
