package com.ktcraft.pvs.service.security.util;

import com.ktcraft.pvs.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = { "jwt.secret=authserversecret" })
class JwtTokenUtilTest {

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Test
    void decodeJWTwithExpiredToken() {
        String expiredToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjozLCJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MTE2MjM5MDIyfQ.xT1wpPk2pW-zOKACHqxyGSFqcovBF_H2uKWY9pftBD8";
        assertThrows(ExpiredJwtException.class, () -> jwtTokenUtil.decodeJWT(expiredToken));
    }

    @Test
    void decodeJWTwithInvalidToken() {
        String invalidToken = "abcdefg";
        assertThrows(MalformedJwtException.class, () -> jwtTokenUtil.decodeJWT(invalidToken));
    }

    @Test
    void decodeJWTwithValid() {
        String validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjozLCJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MjgxNjIzOTAyMn0.Wr6EiZoj8xk7hHr_EnzrM2ItklOhdG758ZF5SO-hJ_Y";
        final Claims claims = jwtTokenUtil.decodeJWT(validToken);
        assertEquals(10l, claims.get(AppConstants.JWT_ORGANIZATION_ID, Long.class));
        assertEquals(310000004l, claims.get(AppConstants.JWT_ACCOUNT_ID, Long.class));
        assertEquals(3, claims.get(AppConstants.JWT_PRODUCT_ID, Integer.class));
    }

    @Test
    void validate_withInvalidToken() {
        String invalidToken = "abcdefg";
        assertFalse(jwtTokenUtil.validate(invalidToken));
    }

    @Test
    void validate_withValidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjozLCJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MjgxNjIzOTAyMn0.Wr6EiZoj8xk7hHr_EnzrM2ItklOhdG758ZF5SO-hJ_Y";
        assertTrue(jwtTokenUtil.validate(invalidToken));
    }
}