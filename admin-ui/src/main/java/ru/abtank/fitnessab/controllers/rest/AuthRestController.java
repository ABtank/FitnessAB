package ru.abtank.fitnessab.controllers.rest;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.abtank.fitnessab.dto.JwtRequest;
import ru.abtank.fitnessab.dto.JwtResponse;
import ru.abtank.fitnessab.dto.UserDto;
import ru.abtank.fitnessab.exception.NotFoundException;
import ru.abtank.fitnessab.security.JwtTokenUtil;
import ru.abtank.fitnessab.security.UserAuthService;
import ru.abtank.fitnessab.servises.Mapper;
import ru.abtank.fitnessab.servises.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class AuthRestController {
    private final UserAuthService userAuthService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;
    private final UserService userService;

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

    @ApiOperation(value = "Кто я.", notes = "Информация авторизовавшегося пользователя", response = UserDto.class)
    @GetMapping("/whoami")
    public ResponseEntity<UserDto> login(@ApiIgnore Principal principal) {
        System.out.println("whoami");
        System.out.println(principal == null);
        System.out.println(principal);
        return (principal != null) ?
                new ResponseEntity<>
                        (userService
                                .findByLogin(principal.getName())
                                .orElseThrow(NotFoundException::new), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
