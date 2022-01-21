package ru.abtank.fitnessab.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.abtank.fitnessab.dto.JwtRequest;
import ru.abtank.fitnessab.dto.JwtResponse;
import ru.abtank.fitnessab.security.JwtTokenUtil;
import ru.abtank.fitnessab.security.UserAuthService;

@RestController
public class AuthRestController {
    private UserAuthService userAuthService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthRestController(UserAuthService userAuthService,
                              JwtTokenUtil jwtTokenUtil,
                              AuthenticationManager authenticationManager) {
        this.userAuthService = userAuthService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        UserDetails userDetails = userAuthService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
