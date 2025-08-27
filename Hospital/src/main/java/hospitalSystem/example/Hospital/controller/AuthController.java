package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.auth.AuthenticationRequest;
import hospitalSystem.example.Hospital.dto.auth.AuthenticationResponse;
import hospitalSystem.example.Hospital.dto.auth.RegisterRequest;
import hospitalSystem.example.Hospital.service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}
