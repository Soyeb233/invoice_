package com.utiitsl.DMSAuthService.service.jwtService;

import com.utiitsl.DMSAuthService.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRE;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRE;



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


//    public boolean isValid(String token, UserDetails user) {
//        String username = extractUsername(token);
//
//        boolean validToken = tokenRepository
//                .findByAccessToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);
//
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
//    }
//
//    public boolean isValidRefreshToken(String token, User user) {
//        String username = extractUsername(token);
//
//        boolean validRefreshToken = tokenRepository
//                .findByRefreshToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);
//
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
//    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_EXPIRE);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_EXPIRE );
    }

    private String generateToken(User user, long expireTime) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("authorities",populateAuthorities(user.getAuthorities()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    public String generateTokenByEmail(String email){
        return generateTokenByEmail(email, ACCESS_TOKEN_EXPIRE);
    }
    private String generateTokenByEmail(String email, long expireTime) {
        String token = Jwts
                .builder()
                .subject(email)
//                .claim("authorities",populateAuthorities(user.getAuthorities()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities){
        Set<String> authoritiesSet=new HashSet<>();
        for(GrantedAuthority authority:authorities){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",",authoritiesSet);
    }

}
