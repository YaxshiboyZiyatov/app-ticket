package ai.ecma.appticketserver.security;

import ai.ecma.appticketserver.exception.RestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken.expirationInMls}")
    private long expireAccessToken;
    @Value("${jwt.refreshToken.expirationInMls}")
    private long expireRefreshToken;

    public String getUsername(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
    }

    public String generate(String phoneNumber, boolean forAccess) {
        Date expire = new Date(System.currentTimeMillis() + (forAccess ? expireAccessToken : expireRefreshToken));
        String token = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setIssuedAt(expire)
                .setSubject(phoneNumber)
                .compact();
        return "Bearer " + token;
    }

    public void validateToken(String token) {
        Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
