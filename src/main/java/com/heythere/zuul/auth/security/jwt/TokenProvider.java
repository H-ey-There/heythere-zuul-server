package com.heythere.zuul.auth.security.jwt;

import com.heythere.zuul.auth.security.AuthUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String createToken(final Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(Long.toString(authUser.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtProperties.getAuth().getTokenExpirationMsec()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(final String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
