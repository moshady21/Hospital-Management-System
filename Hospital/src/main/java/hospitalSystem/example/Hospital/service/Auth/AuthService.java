package hospitalSystem.example.Hospital.service.Auth;

import hospitalSystem.example.Hospital.dto.auth.AuthenticationRequest;
import hospitalSystem.example.Hospital.dto.auth.AuthenticationResponse;
import hospitalSystem.example.Hospital.dto.auth.RegisterRequest;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.repository.UserRepository;
import hospitalSystem.example.Hospital.service.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        // CREATE USER AND SAVE HIM
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getUserRole());

        userRepository.save(user);

        // CREATE TOKEN AND SAVE IT into RESPONSE
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);

        // RETURN TOKEN
        return authenticationResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // AUTHENTICATE THE USER
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // CREATE TOKEN AND SAVE IT into RESPONSE
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);

        // RETURN TOKEN
        return authenticationResponse;
    }
}
