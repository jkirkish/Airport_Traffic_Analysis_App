package com.coderscampus.flightTrack.response;

public record AuthenticationResponse(
        String username,
        String accessToken,
        String refreshToken) {

}
