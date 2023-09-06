package com.coderscampus.flightTrack.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.RefreshTokenRepository;
import com.coderscampus.flightTrack.request.RefreshTokenRequest;

@Service
public class RefreshTokenService {
    
    private UserService userService;
    private RefreshTokenRepository refreshTokenRepository;
    private JwtService jwtService;
    
    @Value("${jwt.refreshTokenExpirationTimeInMillis}")
    private Long refreshTokenExpirationTimeInMillis;
    
    public RefreshTokenService(UserService userService, RefreshTokenRepository refreshTokenRepository,
            JwtService jwtService) {
        super();
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken generateRefreshToken (Integer userId) {
        
        Optional<User> userOpt = Optional.ofNullable(userService.findById(userId));
        if (userOpt.isPresent()) {
            Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findById(userId);
            
            RefreshToken refreshToken = null;
            if (refreshTokenOpt.isPresent()) {
                refreshToken = refreshTokenOpt.get();
                refreshToken.setExpirationDate(getRefreshTokenExpirationDate());
                refreshToken.setRefreshToken(generateRandomTokenValue());
            } else {
                refreshToken = new RefreshToken(userOpt.get(), generateRandomTokenValue(), getRefreshTokenExpirationDate());
            }
            
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }
        return null;
    }

    private String generateRandomTokenValue() {
        return UUID.randomUUID().toString();
    }

    private Date getRefreshTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + refreshTokenExpirationTimeInMillis);
    }
    public String createNewAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken());
        // TODO: write code to check that the RefreshToken hasn't expired
        String accessToken = refreshTokenOpt.map(RefreshTokenService::isNonExpired)
                .map(refreshToken -> jwtService.generateToken(new HashMap<>(), refreshToken.getUser()))
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        
        return accessToken;
        
    }
    
    private static RefreshToken isNonExpired (RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().after(new Date())) {
            return refreshToken;
        } else {
            throw new IllegalArgumentException("Refresh Token has expired");
        }
    }
    @Transactional
	public void deleteByUserId(Integer userId) {
		refreshTokenRepository.deleteByUserId(userId);
		
	}
    
}