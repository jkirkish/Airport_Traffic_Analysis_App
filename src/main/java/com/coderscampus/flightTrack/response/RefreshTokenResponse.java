package com.coderscampus.flightTrack.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken) {

}
