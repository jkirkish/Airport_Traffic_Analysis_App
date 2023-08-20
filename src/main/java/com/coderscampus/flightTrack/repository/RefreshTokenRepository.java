package com.coderscampus.flightTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderscampus.flightTrack.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

}
