package com.ktcraft.pvs.service.security;

import com.ktcraft.pvs.constants.AppConstants;
import com.ktcraft.pvs.service.exceptions.InvalidTokenException;
import com.ktcraft.pvs.service.security.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.RequiredTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenFilter(final JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException();
        }

        final String token = authHeader.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            throw new InvalidTokenException();
        }

        final Claims claims = jwtTokenUtil.decodeJWT(token);

        final AccountAuthentication accountAuthentication = new AccountAuthentication();
        try {
            accountAuthentication.setOrganizationId(claims.get(AppConstants.JWT_ORGANIZATION_ID, Long.class));
            accountAuthentication.setAccountId(claims.get(AppConstants.JWT_ACCOUNT_ID, Long.class));
            accountAuthentication.setProductId(claims.get(AppConstants.JWT_PRODUCT_ID, Integer.class));
        } catch (RequiredTypeException e) {
            throw new InvalidTokenException();
        }

        if (accountAuthentication.getOrganizationId() == null || accountAuthentication.getAccountId() == null
        || accountAuthentication.getProductId() == null) {
            throw new InvalidTokenException();
        }

        SecurityContextHolder.getContext().setAuthentication(accountAuthentication);
        filterChain.doFilter(request, response);
    }
}
