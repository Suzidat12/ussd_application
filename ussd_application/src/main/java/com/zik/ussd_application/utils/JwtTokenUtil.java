package com.zik.ussd_application.utils;

import com.zik.ussd_application.IssueType.JwtIssuerType;
import com.zik.ussd_application.dto.JwtTokenGenerateRequest;
import com.zik.ussd_application.dto.JwtTokenParseRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION_ACCESS = 60 * 60 * 1000; // 1 hour
    private static final long EXPIRE_DURATION_REFRESH = 24 * 60 * 60 * 1000; // 24 hour
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret_access}")
    private String secret_access;

    @Value("${app.jwt.secret_refresh}")
    private String secret_refresh;

    public String generateToken(JwtTokenGenerateRequest jwtTokenRequest) {
        long EXPIRE_DURATION =
                jwtTokenRequest.getJwtIssuer() == JwtIssuerType.HelloAI_Access
                        ? EXPIRE_DURATION_ACCESS
                        : EXPIRE_DURATION_REFRESH;
        return Jwts.builder()
                .setSubject(jwtTokenRequest.getSubject())
                .setIssuer(jwtTokenRequest.getJwtIssuer().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(
                        SignatureAlgorithm.HS512, getSecretKeyByIssuerType(jwtTokenRequest.getJwtIssuer()))
                .compact();
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token without "Bearer "
        }

        return null; // If no token is found in the Authorization header
    }
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret_access) // Assuming you always use the access secret
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty, or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }
        return false;
    }


    private String getSecretKeyByIssuerType(JwtIssuerType jwtIssuerType) {
        String secret_key =
                jwtIssuerType == JwtIssuerType.HelloAI_Access ? secret_access : secret_refresh;
        return secret_key;
    }

    public String parseSubjectFromToken(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(secret_access) // Assuming you always use the access secret
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims.getSubject();
    }


    public int ParseExpirationFromToken(JwtTokenParseRequest jwtTokenParseRequest) {
        Date expiration =
                Jwts.parser()
                        .setSigningKey(getSecretKeyByIssuerType(jwtTokenParseRequest.getJwtIssuerType()))
                        .parseClaimsJws(jwtTokenParseRequest.getJwt_token())
                        .getBody()
                        .getExpiration();
        int seconds = DataUtil.diffTime(expiration);
        return seconds;
    }
}