package ru.petrov.weatherrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.petrov.weatherrest.dto.PersonDTO;
import ru.petrov.weatherrest.security.JWTUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody PersonDTO personDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(personDTO.getUsername(),
                        personDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }
        String token = jwtUtil.generateToken(personDTO.getUsername());
        return Map.of("jwt-token", token);
    }
}
