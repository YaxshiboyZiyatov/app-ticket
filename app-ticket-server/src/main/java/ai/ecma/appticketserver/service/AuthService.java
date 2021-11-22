package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.*;


public interface AuthService {
    ApiResult<TokenDto> login(LoginDto loginDto);

    ApiResult<?> checkPhone(String phone);

    ApiResult<Registered> checkSms(CodeDto codeDto);

    ApiResult<TokenDto> signUp(RegisterDto registerDto);

    ApiResult<TokenDto> refreshToken(TokenDto tokenDto);

    ApiResult<?> forgotPassword(ForgotPasswordDto forgotPasswordDto);

    ApiResult<?> replacePassword(ForgotPasswordDto forgotPasswordDto);
}
