package com.utiitsl.DMSAuthService.config.auth;

import com.utiitsl.DMSAuthService.constants.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.utiitsl.DMSAuthService.constants.Permission.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private static final String [] SWAGGER_WHITELIST={
      "/swagger-ui/**","/actuator/**",
      "/","/error","/csrf",
       "/swagger-ui.html","/swagger-ui/**","/v3/api-docs","/v3/api-docs/**",
            "/authService/v3/api-docs",
            "/authService/v3/api-docs/**"
    };

    /*

     .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/actuator/**","/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()

     */

//    @Bean
//    public SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity)throws Exception{
//
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req->
//                        req.requestMatchers("/api/v1/auth/**","/loginWithParichay")
//                                .permitAll()
//                                .requestMatchers(SWAGGER_WHITELIST).permitAll()
//                                .requestMatchers("/v1/supreme/**").hasAnyRole(Role.APPLICATION_ADMIN.name(),Role.SUPREMEADMIN.name())
//                                .requestMatchers("/v1/superAdmin/**").hasAnyRole(Role.APPLICATION_ADMIN.name(),Role.SUPREMEADMIN.name(),Role.SUPERADMIN.name())
//                                .requestMatchers("/v1/admin/**").hasAnyRole(Role.APPLICATION_ADMIN.name(),Role.SUPREMEADMIN.name(),Role.SUPERADMIN.name(),Role.ADMIN.name())
//                                .requestMatchers("/api/v1/user/**").hasAnyRole(Role.APPLICATION_ADMIN.name(),Role.SUPREMEADMIN.name(),Role.SUPERADMIN.toString(),Role.ADMIN.name(), Role.USER.name())
//
//                                // TESTING FOR PERMISSION FOR BOTH ADMIN AS WELL AS USER
//
//                                .requestMatchers("/v1/dash/**").hasAnyRole(Role.APPLICATION_ADMIN.name(),Role.SUPREMEADMIN.name(),Role.SUPERADMIN.toString(),Role.ADMIN.name(),Role.USER.name())
//                                .requestMatchers(HttpMethod.GET,"/v1/dash/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
//                                .requestMatchers(HttpMethod.POST,"/v1/dash/**").hasAuthority(ADMIN_CREATE.name())
//                                .requestMatchers(HttpMethod.PUT,"/v1/dash/**").hasAnyAuthority(ADMIN_UPDATE.name())
//                                .requestMatchers(HttpMethod.DELETE,"/v1/dash/**").hasAuthority(ADMIN_DELETE.name())
//
//
//                                .anyRequest()
//                                .authenticated())
////                .userDetailsService(userDetailsService)
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//
//
//
    //    }

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/api/v1/user/**"
                        ).permitAll()

                        .requestMatchers(SWAGGER_WHITELIST).permitAll()

                        .requestMatchers("/v1/supreme/**")
                        .hasAnyRole(Role.APPLICATION_ADMIN.name(), Role.SUPREMEADMIN.name())

                        .requestMatchers("/v1/admin/**")
                        .hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name(), Role.SUPREMEADMIN.name())

                        .requestMatchers("/api/v1/user/**")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPERADMIN.name())

                        .anyRequest().authenticated()
                )

//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                .authenticationProvider(authenticationProvider)
//
//                // JWT filter (your existing one)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//
//                // ⭐ ADD THIS FOR OAUTH2
//                .oauth2Login(oauth -> oauth
//                        .successHandler(oAuth2SuccessHandler)
//                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");

                            response.getWriter().write("""
                            {
                                "timestamp": "%s",
                                "status": 401,
                                "error": "Unauthorized",
                                "message": "Authentication token is missing or invalid",
                                "path": "%s"
                            }
                            """.formatted(
                                    java.time.LocalDateTime.now(),
                                    request.getRequestURI()
                            ));
                        })
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2SuccessHandler)
                )

                .build();


    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        configuration.addAllowedOrigin("http://localhost:9696");
        // ADDED THIS URL TO ACCESS USING SWAGGER
        configuration.addAllowedOrigin("http://localhost:8686");
        configuration.addAllowedOrigin("http://103.208.56.61:8282/");

        // Allow specific origins (localhost and UAT URLs)
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://10.11.50.190:3000");
        configuration.addAllowedOrigin("http://10.11.50.191:3000");

        configuration.addAllowedOrigin("http://103.208.56.61:8181"); // Temporary/UAT URL
        configuration.addAllowedOrigin("http://103.208.57.61:8181");
        configuration.addAllowedOrigin("http://10.10.108.26:8181"); // Production/UAT URL

        configuration.addAllowedOrigin("http://127.0.1.1:8181");

        // NIC PARICHAY URL ACCESS
        configuration.addAllowedOrigin("https://parichay.staging.nic.in");

        configuration.addAllowedOrigin("http://103.208.56.61:8282"); // Temporary/UAT URL
        configuration.addAllowedOrigin("http://103.208.57.61:8282");

        configuration.addAllowedOrigin("http://10.10.108.26:8282");// Temporary/UAT URL
        configuration.addAllowedOrigin("http://10.10.108.26:8181"); // Production/UAT URL
        configuration.addAllowedOrigin("http://10.10.108.26:8282");
        configuration.addAllowedOrigin("http://10.10.108.26:8383");

        // Avoid wildcard (*), only allow trusted domains
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");

        // Expose headers for file download or other purposes
        configuration.setExposedHeaders(Arrays.asList("Content-Disposition", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
