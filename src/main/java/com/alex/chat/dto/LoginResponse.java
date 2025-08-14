package com.alex.chat.dto;

/**
 * DTO para la respuesta de inicio de sesión.
 * Contiene el token JWT generado tras una autenticación exitosa.
 */
public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
