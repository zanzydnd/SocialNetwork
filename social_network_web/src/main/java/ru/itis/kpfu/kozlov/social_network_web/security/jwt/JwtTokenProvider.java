package ru.itis.kpfu.kozlov.social_network_web.security.jwt;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Autowired
    @Qualifier("jwtUserService")
    private UserDetailsService userDetailsService;


    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, UserEntity.Role role){
        System.out.println("token created");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", String.valueOf(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        System.out.println("authentication");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        System.out.println("getUsername");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req){
        System.out.println("resolve");
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer_")){
            System.out.println("alright");
            return bearerToken.substring(7,bearerToken.length());
        }
        System.out.println("not alright");
        return null;
    }

    public boolean validateToken(String token){
        System.out.println("validateToken");
        try{
            Jws<Claims>  claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())){
                System.out.println("expired");
                return false;
            }
            System.out.println("ok");
            return true;
        }catch (JwtException |  IllegalArgumentException e){
            System.out.println("invalid");
            return false;
            //throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
    }
}
