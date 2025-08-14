package com.alex.chat.controller;

import com.alex.chat.dto.LoginRequest;
import com.alex.chat.dto.LoginResponse;
import com.alex.chat.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Controlador para gestionar la autenticación de usuarios.
 * Proporciona endpoints para inicio de sesión y generación de tokens JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint para inicio de sesión.
     * Autentica al usuario y devuelve un token JWT si las credenciales son válidas.
     *
     * @param loginRequest Credenciales de inicio de sesión
     * @return Token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Intento de inicio de sesión para usuario: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginRequest.getUsername());

        logger.info("Usuario {} autenticado correctamente", loginRequest.getUsername());

        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
