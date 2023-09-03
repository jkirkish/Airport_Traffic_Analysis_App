package com.coderscampus.flightTrack.util;

import jakarta.servlet.http.Cookie;


public class CookieUtils {

    public static final String REFRESH_TOKEN_NAME = "refreshToken";
    public static final String ACCESS_TOKEN_NAME = "accessToken";

    public static Cookie createAccessTokenCookie (String value) {
        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_NAME, value);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(-1);
        return accessTokenCookie;
    }
    
    public static Cookie createRefreshTokenCookie (String value) {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_NAME, value);
        
        return refreshTokenCookie;
    }
}