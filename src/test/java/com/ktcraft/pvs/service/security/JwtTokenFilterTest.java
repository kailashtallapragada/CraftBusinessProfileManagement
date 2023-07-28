package com.ktcraft.pvs.service.security;

import com.ktcraft.pvs.service.exceptions.InvalidTokenException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = { "jwt.secret=authserversecret" })
class JwtTokenFilterTest {

    @Autowired
    public JwtTokenFilter jwtTokenFilter;

    @Test
    void doFilterInternalWithoutHeader() {
        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenFilter.doFilterInternal(req, res, chain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithInvalidHeader() {
        final String invalidToken = "abcdefg";

        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenFilter.doFilterInternal(req, res, chain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithExpiredTokenHeader() {
        String expiredToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjozLCJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MTE2MjM5MDIyfQ.xT1wpPk2pW-zOKACHqxyGSFqcovBF_H2uKWY9pftBD8";

        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(expiredToken);

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenFilter.doFilterInternal(req, res, chain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithInvalidType() {
        String expiredToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjoiYWJjIiwiYWNjb3VudF9pZCI6MzEwMDAwMDA0LCJvcmdhbml6YXRpb25faWQiOjEwLCJleHAiOjI4MTYyMzkwMjJ9.EQcmxxFGGGhjCsPKKGGMY6VeEreZbTgzOBMnUQQWB9Q";

        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(expiredToken);

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenFilter.doFilterInternal(req, res, chain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithoutProductId() {
        String tokenWithoutProductId = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MjgxNjIzOTAyMn0.My5cLR7BaeHkd9uu8s2CQA5ZnNZd1a1Awe7f6961Q5w";;

        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(tokenWithoutProductId);

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenFilter.doFilterInternal(req, res, chain));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithValidToken() throws ServletException, IOException {
        String tokenWithoutProductId = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2lkIjozLCJhY2NvdW50X2lkIjozMTAwMDAwMDQsIm9yZ2FuaXphdGlvbl9pZCI6MTAsImV4cCI6MjgxNjIzOTAyMn0.Wr6EiZoj8xk7hHr_EnzrM2ItklOhdG758ZF5SO-hJ_Y";;

        final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(tokenWithoutProductId);

        SecurityContextHolder.getContext().setAuthentication(null);

        jwtTokenFilter.doFilterInternal(req, res, chain);

        assertEquals(310000004l,
                ((AccountAuthentication) SecurityContextHolder.getContext().getAuthentication()).getAccountId());
        assertEquals(3,
                ((AccountAuthentication) SecurityContextHolder.getContext().getAuthentication()).getProductId());
        assertEquals(10l,
                ((AccountAuthentication) SecurityContextHolder.getContext().getAuthentication()).getOrganizationId());

        Mockito.verify(chain, Mockito.times(1)).doFilter(req, res);
    }
}