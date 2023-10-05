package com.zik.ussd_application.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the JWT token from the request
        String token = jwtTokenUtil.extractTokenFromRequest(request);

        if (token != null && jwtTokenUtil.validateToken(token)) {
            try {
                // Parse the JWT token to get the user information
                String username = jwtTokenUtil.parseSubjectFromToken(token);

                // You can fetch user roles from the token if needed
                // List<String> roles = jwtTokenUtil.getRolesFromToken(token);

                // Create an authentication token based on the user's information
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null);

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException e) {
                // Handle JWT parsing or validation errors here
            }
        }

        // Continue processing the request
        filterChain.doFilter(request, response);
    }
}
