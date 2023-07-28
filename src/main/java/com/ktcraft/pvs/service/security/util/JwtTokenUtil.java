package com.ktcraft.pvs.service.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Claims decodeJWT(String jwt) {
        final Claims claims = Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(secret))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public Boolean validate(String jwt) {
        try {
            decodeJWT(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
