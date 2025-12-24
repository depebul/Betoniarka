package com.betoniarka.biblioteka.auth;

import com.betoniarka.biblioteka.appuser.AppUserService;
import com.betoniarka.biblioteka.appuser.dto.AppUserRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody AppUserRegisterDto requestDto) {
        appUserService.register(requestDto);
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        return "Logged in as " + authentication.getName();
    }

}

