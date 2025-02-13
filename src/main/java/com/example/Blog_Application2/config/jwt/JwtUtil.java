package com.example.Blog_Application2.config.jwt;

import com.example.Blog_Application2.config.secuirty.AuthenticatedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    private static final String SECRET_KEY = "9142504BD0FEDF749BFDBA7885F92010E95A57E63E976DFAB65EAA4677C7577C487C9B0AB002110A7891E9816B91BC8CE0FD398E3CEA5375E19C9E0FE94DD1E4";
    private final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(60);

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof AuthenticatedUser) {
            AuthenticatedUser customUser = (AuthenticatedUser) userDetails;
            claims.put("role", customUser.getAuthorities().stream()      //gets list of data from getAuthorities which contains roles also which shown in user details
                    .map(GrantedAuthority::getAuthority)// converts granted authority into string
                    .collect(Collectors.toList()));  // Convert roles to a list
        }
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {


        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }


}


