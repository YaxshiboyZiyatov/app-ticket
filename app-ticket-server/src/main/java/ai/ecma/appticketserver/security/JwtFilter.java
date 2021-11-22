package ai.ecma.appticketserver.security;

import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.service.AuthServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthServiceImpl authServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public JwtFilter(JwtProvider jwtProvider, AuthServiceImpl authServiceImpl,@Lazy PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.authServiceImpl = authServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")){
            authorization = authorization.substring("Bearer".length() + 1);
            String phoneNumber = jwtProvider.getUsername(authorization);
            UserDetails userDetails = authServiceImpl.loadUserByUsername(phoneNumber);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }else if (authorization != null && authorization.startsWith("Basic")){
            authorization = authorization.substring("Basic".length() + 1);
            String encodedString = new String(Base64.decodeBase64(authorization.getBytes()));
            String[] split = encodedString.split(":");
            UserDetails userDetails = authServiceImpl.loadUserByUsername(split[0]);
            if (userDetails == null || !passwordEncoder.matches(split[1], userDetails.getPassword()))
                throw new RestException("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
