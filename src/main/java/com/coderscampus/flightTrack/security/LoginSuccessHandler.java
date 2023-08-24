package com.coderscampus.flightTrack.security;

import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.util.CookieUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


	@Component
	public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	    private JwtService jwtService;
	    private RefreshTokenService refreshTokenService;

	    public LoginSuccessHandler(JwtService jwtService, RefreshTokenService refreshTokenService) {
	        super();
	        this.jwtService = jwtService;
	        this.refreshTokenService = refreshTokenService;
	    }

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException, java.io.IOException {

	        User user = (User) authentication.getPrincipal();

	        String accessToken = jwtService.generateToken(new HashMap<>(), user);
	        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user.getId());

	        Cookie accessTokenCookie = CookieUtils.createAccessTokenCookie(accessToken);
	        Cookie refreshTokenCookie = CookieUtils.createRefreshTokenCookie(refreshToken.getRefreshToken());

	        response.addCookie(accessTokenCookie);
	        response.addCookie(refreshTokenCookie);
	        response.sendRedirect("/api/v1/users/index");

	    }

	}

