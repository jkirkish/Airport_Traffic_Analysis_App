package com.coderscampus.flightTrack;



import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.service.JwtService;


@TestInstance(Lifecycle.PER_CLASS)
class JwtServiceTest {

    private JwtService sut;
    
    @BeforeAll
    void init () {
        sut = new JwtService();
    }
    
    @Test
    void testGenerateToken() {
        /** 
         * 1. Arrange
         * 2. Act
         * 3. Assert
         */
                
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        User user = new User("trevor@coderscampus.com", "abc123");
        
        // Act
        String jwt = sut.generateToken(extraClaims, user);
        
        // Assert
        System.out.println(jwt);
    }

}