package com.utiitsl.DMSAuthService.service.refreshTokenService;

import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.RefreshTokenRequest;
import com.utiitsl.DMSAuthService.entity.RefreshToken;

public interface RefreshTokenService {

    public AuthenticationResponseDTO generateRefreshToken(RefreshTokenRequest refreshTokenRequest);
    public RefreshToken createRefreshToken(String username);
    public RefreshToken verifyRefreshToken(String refreshTOken);
}
