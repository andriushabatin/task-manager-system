package com.andriusha.task.management.system.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication methods")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(
            summary = "Регистрирует нового пользователя",
            description = "Получает на вход объект RegisterRequest, создает на его основе объект User, сохраняет его " +
                    "в базу, генерирует jwt токен и возвращает его в объекте AuthenticationResponse."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(
            summary = "Авторизирует зарегистрированного пользователя",
            description = "Получает на вход объект AuthenticationRequest, генерирует jwt токен и возвращает его в объекте AuthenticationResponse."
    )
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
