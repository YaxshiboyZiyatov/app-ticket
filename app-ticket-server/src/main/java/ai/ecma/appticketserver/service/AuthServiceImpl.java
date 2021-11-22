package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.entity.VerificationCode;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.RoleRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import ai.ecma.appticketserver.repository.VerificationCodeRepository;
import ai.ecma.appticketserver.security.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final RoleRepository roleRepository;
    private final TwilioService twilioService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.maxSendMessageCount}")
    private long maxSendMessageCount;

    @Value("${app.maxSendMessageDuration}")
    private long maxSendMessageDuration;

    public AuthServiceImpl(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JwtProvider jwtProvider, VerificationCodeRepository verificationCodeRepository, RoleRepository roleRepository, TwilioService twilioService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.verificationCodeRepository = verificationCodeRepository;
        this.roleRepository = roleRepository;
        this.twilioService = twilioService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ApiResult<TokenDto> login(LoginDto loginDto) {
        log.info("Login method entered: {}", loginDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            String accessToken = jwtProvider.generate(loginDto.getUsername(), true);
            String refreshToken = jwtProvider.generate(loginDto.getUsername(), false);
            log.info("Login method exited: {}", loginDto.getUsername());
            return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
        } catch (Exception e) {
            log.info("Login method exited: {}", " ");
            return ApiResult.failResponse("Login or password is wrong");
        }

    }

    @Override
    public ApiResult<?> checkPhone(String phone) {
        //validatsiyadan o'tmasa throw ga otadi
        validate(phone);
        log.info("CheckPhone method entered: {}", " ");
        Timestamp lastTime = new Timestamp(System.currentTimeMillis() - maxSendMessageDuration);
        long countMessages = verificationCodeRepository.countPhoneNumbers(lastTime, phone);
        if (countMessages > maxSendMessageCount)
            throw new RestException("TOO_MANY_REQUESTS", HttpStatus.TOO_MANY_REQUESTS);
        //yangi verification code yaratdim va saqladim
        VerificationCode verificationCode = verificationCodeRepository.save(
                new VerificationCode(
                        phone,
                        generateCode(),
                        false
                )
        );
        //sms yuborish xizmati
        twilioService.sendVerificationCode(phone, verificationCode.getCode());
        log.info("CheckPhone method exited: {}", " ");
        return ApiResult.successResponse("verification code send to phone number");
    }

    @Override
    public ApiResult<Registered> checkSms(CodeDto codeDto) {
        //validatsiyadan o'tkazdim
        log.info("CheckSMS method entered: {}", " ");
        validate(codeDto.getPhoneNumber(), codeDto.getCode());
        //select hah from vc where phoneNumber=998991234567 or 1=1
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumberAndCodeAndUsedIsFalseAndCreatedAtAfter(
                codeDto.getPhoneNumber(),
                codeDto.getCode(),
                new Timestamp(System.currentTimeMillis() - 180000)
        ).orElseThrow(() -> new RestException("Sms code is wrong", HttpStatus.BAD_REQUEST));
        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);
        Optional<User> optionalUser = userRepository.findByPhoneNumber(codeDto.getPhoneNumber());
        //avval ro'yxatdan o'tgan
        if (optionalUser.isPresent()) {
            String accessToken = jwtProvider.generate(codeDto.getPhoneNumber(), true);
            String refreshToken = jwtProvider.generate(codeDto.getPhoneNumber(), false);
            log.info("CheckSMS method entered: {}", " ");
            return ApiResult.successResponse(new Registered(true, accessToken, refreshToken));
        }
        //yangi user
        log.info("CheckSMS method exited: {}", " ");
        return ApiResult.successResponse(new Registered(false, null, null));
    }

    @Override
    public ApiResult<TokenDto> signUp(RegisterDto registerDto) {
        //validatsiya
        validate(registerDto.getPhoneNumber());
        //check
        log.info("SignUp method entered: {}", " ");
        if (!verificationCodeRepository.existsByPhoneNumberAndCodeAndUsedIsTrue(registerDto.getPhoneNumber(), registerDto.getCode()))
            throw new RestException("BAD_REQUEST", HttpStatus.BAD_REQUEST);

        if (userRepository.existsByPhoneNumber(registerDto.getPhoneNumber()))
            throw new RestException("User already exist", HttpStatus.CONFLICT);

        userRepository.save(
                new User(
                        registerDto.getFirstName(),
                        registerDto.getLastName(),
                        registerDto.getPhoneNumber(),
                        true,
                        roleRepository.findByRoleType(RoleTypeEnum.CLIENT).orElseThrow(() -> new RestException("Role not found", HttpStatus.NOT_FOUND))
                )
        );
        verificationCodeRepository.deleteByPhoneNumberAndCodeAndUsedTrue(registerDto.getPhoneNumber(), registerDto.getCode());

        String accessToken = jwtProvider.generate(registerDto.getPhoneNumber(), true);
        String refreshToken = jwtProvider.generate(registerDto.getPhoneNumber(), false);
        log.info("SignUp method entered: {}", " ");
        return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        try {
            jwtProvider.validateToken(tokenDto.getAccessToken());
            return ApiResult.successResponse(tokenDto);
        } catch (ExpiredJwtException e) {
            try {
                jwtProvider.validateToken(tokenDto.getRefreshToken());
                String username = jwtProvider.getUsername(tokenDto.getRefreshToken());
                String accessToken = jwtProvider.generate(username, true);
                String refreshToken = jwtProvider.generate(username, false);
                return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
            } catch (Exception ex) {
                throw new RestException("Sign ga boooor", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RestException("Sign ga boooor", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ApiResult<?> forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByPhoneNumber(forgotPasswordDto.getPhoneNumber()).orElseThrow(() -> new RestException("Not registered", HttpStatus.NOT_FOUND));
        Timestamp lastTime = new Timestamp(System.currentTimeMillis() - maxSendMessageDuration);
        long countMessages = verificationCodeRepository.countPhoneNumbers(lastTime, user.getPhoneNumber());
        if (countMessages > maxSendMessageCount)
            throw new RestException("TOO_MANY_REQUESTS", HttpStatus.TOO_MANY_REQUESTS);

        String code = generateCode();

        boolean send = twilioService.sendVerificationCode(user.getPhoneNumber(), code);
        //sms yuborish xizmati
        if (!send)
            throw new RestException("Server xatoligi, qayta urinib ko'ring", HttpStatus.INTERNAL_SERVER_ERROR);

        //yangi verification code yaratdim va saqladim
        VerificationCode verificationCode = verificationCodeRepository.save(
                new VerificationCode(
                        user.getPhoneNumber(),
                        code,
                        false
                )
        );

        return ApiResult.successResponse("Verification code send your phone number");
    }

    @Override
    public ApiResult<?> replacePassword(ForgotPasswordDto forgotPasswordDto) {
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumberAndCodeAndUsedIsFalseAndCreatedAtAfter(
                forgotPasswordDto.getPhoneNumber(),
                forgotPasswordDto.getCode(),
                new Timestamp(System.currentTimeMillis() - 180000)
        ).orElseThrow(() -> new RestException("Sms code is wrong", HttpStatus.BAD_REQUEST));

        if (!forgotPasswordDto.getNewPassword().equals(forgotPasswordDto.getPrePassword()))
            throw new RestException("Passwords none match", HttpStatus.CONFLICT);

        User user = userRepository.findByPhoneNumber(verificationCode.getPhoneNumber()).orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);

        String encode = passwordEncoder.encode(forgotPasswordDto.getNewPassword());
        user.setPassword(encode);
        userRepository.save(user);
        return ApiResult.successResponse("Password succesfully replaced");
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RestException("user not found", HttpStatus.NOT_FOUND));
    }

    private void validate(String phoneNumber) {
        log.info("PhoneNumber validate method entered: {}", "");
        if (phoneNumber.startsWith("+") || !phoneNumber.startsWith("998") || phoneNumber.length() != 12)
            throw new RestException("NOT_VALIDATE_PHONE_NUMBER", HttpStatus.BAD_REQUEST);
        for (int i = 0; i < phoneNumber.length(); i++) {
            boolean matches = String.valueOf(phoneNumber.charAt(i)).matches("[0-9]");
            if (!matches)
                throw new RestException("NOT_NUMBER", HttpStatus.BAD_REQUEST);
        }
        log.info("PhoneNumber validate method exited: {}", "");
    }

    private void validate(String phoneNumber, String code) {
        validate(phoneNumber);
        log.info("PhoneNumber and Code validate method entered: {}", "");
        if (code.length() != 6)
            throw new RestException("NOT_VALIDATE_CODE", HttpStatus.BAD_REQUEST);
        for (int i = 0; i < code.length(); i++) {
            boolean matches = String.valueOf(code.charAt(i)).matches("[0-9]");
            if (!matches)
                throw new RestException("NOT_NUMBER", HttpStatus.BAD_REQUEST);
        }
        log.info("PhoneNumber and Code validate method exited: {}", "");
    }

    private String generateCode() {
        String code = String.valueOf((int) (Math.random() * 10_000_000)).substring(0, 6);
        System.out.println(code);
        return code;
    }
}
