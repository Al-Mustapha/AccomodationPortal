package com.example.AccomodationPortal.Authorization;

import com.example.AccomodationPortal.Security.JwtUtils;
import com.example.AccomodationPortal.Student.CustomFetchUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("user/v1/")
public class AuthenticateUser {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomFetchUserDetails customFetchUserDetails;

    @PostMapping("authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginCredentials user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        ));

        UserDetails userDetails = customFetchUserDetails.loadUserByUsername(user.getUsername());
        String username = userDetails.getUsername();
        String token = jwtUtils.generateToken(username);

        if (!token.equals("")){
            return ResponseEntity.ok(token);
        }
        else {
         return ResponseEntity.status(400)
                 .body("User with the login credentials does not exist");
        }
    }
}
