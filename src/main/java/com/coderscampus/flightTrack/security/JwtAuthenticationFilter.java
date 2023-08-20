package com.coderscampus.flightTrack.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserService userService;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        super();
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Requests: 
        //  Headers -> key/value pairs (Authorization -> Bearer xxx.yyy.zzz)
        //  Body -> (if JSON) key/value pairs
        String authHeader = request.getHeader("Authorization");
        
        if (StringUtils.hasText(authHeader)) {
            // hey, we have a token (probably) in the request
            // let's see if this token is a valid JWS or not
            String token = authHeader.substring(7);
            String subject = jwtService.getSubject(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (StringUtils.hasText(subject) && authentication == null) {
                UserDetails userDetails = userService.loadUserByUsername(subject);
                
                if (jwtService.isValidToken(token, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, 
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities());
                    securityContext.setAuthentication(authToken);
                    SecurityContextHolder.setContext(securityContext);
                } 
            }
        }
        filterChain.doFilter(request, response);
    }

}
