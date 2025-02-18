package com.example.Blog_Application2.config.secuirty;


import com.example.Blog_Application2.config.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import com.example.Blog_Application2.config.jwt.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)

@RequiredArgsConstructor
@EnableWebMvc
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final  UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint;


//    public SecurityConfig(UserDetailsService userDetailsService, com.example.Blog_Application2.config.jwt.JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//        this.userDetailsService = userDetailsService;
//        JwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html","/login", "/signup","/signupWithImage", "/post/image/**", "/user/image/**", "/category", "/forgot-password/**", "/posts", "category/{categoryId}/posts", "post/{postId}", "like/{postId}/likeCount", "like/{postId}/disLikeCount", "comment/comments-post/{postId}" , "comment/{postId}/commentCount")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(JwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();   //It's a class provided by Spring Security that authenticates user credentials against a database or any data source.
        provider.setUserDetailsService(userDetailsService);   //Links the DaoAuthenticationProvider to your custom implementation of UserDetailsService
        provider.setPasswordEncoder(passwordEncoder());       //Ensures that the passwords are compared securely using a PasswordEncoder (e.g., BCryptPasswordEncoder).
        return new ProviderManager(provider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


}
