package com.utiitsl.DMSAuthService.service.refreshTokenService;

import com.utiitsl.DMSAuthService.common.exceptionHandler.UserDefinedException;
import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.RefreshTokenRequest;
import com.utiitsl.DMSAuthService.entity.RefreshToken;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.repository.RefreshTokenRepository;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImple implements RefreshTokenService{

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRE;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO generateRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken=verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user=refreshToken.getUser();
        String token=jwtService.generateAccessToken(user);
        System.err.println("TOKEN CREATED .. :"+token);

        return AuthenticationResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken.getRefreshToken())
                .status(HttpStatus.OK)
                .role(user.getRole().toString())
                .username(user.getUsername())
                .build();

    }

    @Override
    public RefreshToken createRefreshToken(String username) {

        Optional<User> userOptional=userRepository.findByUsername(username);
        RefreshToken refreshToken=userOptional.get().getRefreshToken();;

        if(refreshToken==null) {
            System.err.println("PRINTING THE 55  refresh token empty");
             refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRE))
                    .user(userOptional.get())
                    .build();
        }
        else{
            refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRE));
        }
        userOptional.get().setRefreshToken(refreshToken);;

        System.out.println(refreshToken);
        // SAVE THE TOKEN
        RefreshToken refreshToken1=refreshTokenRepository.save(refreshToken);
        System.out.println("AFTER SAVING THE REOCRDS :"+refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
        System.err.println("REFRESH TOKEN FROM 74 :"+refreshToken);

        RefreshToken refreshTokenObj=refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()-> new UserDefinedException("PROVIDED TOKEN NOT EXISTS", HttpStatus.NOT_FOUND));

        if(refreshTokenObj.getExpiry().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshTokenObj);
            throw new UserDefinedException(("Refresh Token Expired"),HttpStatus.FORBIDDEN);
        }
        return refreshTokenObj;
    }
}
