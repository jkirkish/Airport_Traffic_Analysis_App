package com.coderscampus.flightTrack.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


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
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
        response.addCookie(cookie);
    }
}