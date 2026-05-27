package com.utiitsl.DMSAuthService.config.auth;

import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // Proceed with the filter chain if no token is found
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // You can use both statuses depending on the use case
                return;
        }

        // Extract the token from the header
        token = authHeader.substring(7);

        // Try to extract the username from the JWT token
        try {
            String username = jwtService.extractUsername(token); // Extract username from token

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // If token is valid and no authentication is set, proceed to load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create the authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context in the security holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (ExpiredJwtException e) {
            // If the token is expired, handle the expiration and return an appropriate response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status code for Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Token has expired. Please log in again.\"}");

            // Optionally log the error
            System.err.println("Token has expired. User must re-authenticate.");

            return;  // Stop further processing
        } catch (JwtException e) {
            // If the token is invalid for any other reason, handle the invalid token case
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Invalid token. Please log in again.\"}");

            // Optionally log the error
            System.err.println("Invalid token. User must re-authenticate.");

            return;  // Stop further processing
        }

        // Proceed with the filter chain if token is valid
        filterChain.doFilter(request, response);
    }


//    @Override
//    protected boolean shouldNotFilter(@NonNull HttpServletRequest  request) throws ServletException{
//        return request.getServletPath().contains("/v1/auth");
//    }
}
