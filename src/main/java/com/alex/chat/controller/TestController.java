package com.alex.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "API Chat Tiempo Real is running!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "API Chat Tiempo Real");
        response.put("version", "1.0.0");
        response.put("author", "Alejandro Arango Calderon");
        response.put("github", "https://github.com/Biershoot/API_Chat_Tiempo_Real");
        return response;
    }
}
