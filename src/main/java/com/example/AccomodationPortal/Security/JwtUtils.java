package com.example.AccomodationPortal.Security;

import com.example.AccomodationPortal.Authorization.UserRole;
import com.example.AccomodationPortal.Student.CustomFetchUserDetails;
import com.example.AccomodationPortal.Student.Student;
import com.example.AccomodationPortal.Student.StudentRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class JwtUtils {

    private CustomFetchUserDetails customFetchUserDetails;
    private StudentRepository studentRepository;
    private final String jwtSignKey
            = "secretsecretsecretsecretsecretsecretsecretsecretsecret" +
            "secretsecretsecretsecretsecretsecretsecretsecretsecretsecret" +
            "secretsecretsecretsecretsecretsecretsecretsecretsecretsecret" +
            "secretsecretsecretsecretsecretsecretsecretsecretsecretsecret" +
            "secretsecretsecretsecretsecret";

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public java.util.Date extractExpiration(String token){
        return (java.util.Date) extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaims(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName)!= null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        Claims body = Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
        return body;
    }
    public String createToken(Map<String, Object> claims, String subject){
        UserDetails user = customFetchUserDetails.loadUserByUsername(subject);
        String username = user.getUsername();
        String userId = null;
        Optional<Student> student =
                Optional.ofNullable(studentRepository.findByUsername(username));

//        Optional<Doctor> doctor =
//                Optional.ofNullable(doctorRepository.findUserByUsername(username));

        UserRole role = null;

        if (student.isPresent()){
            role = student.get().getRole();
            userId = student.get().getRegistrationNumber();
        }
//        else if (doctor.isPresent()){
//            role = doctor.get().getRole();
//            userId = doctor.get().getDoctorId();
//        }

        String token = Jwts.builder()
                .setSubject(subject)
                .claim("Authorization", claims)
                .claim("authorities",user.getAuthorities())
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String generateToken(String userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Key getSignInKey(){
        final byte[] keyBytes = Decoders.BASE64.decode(jwtSignKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
