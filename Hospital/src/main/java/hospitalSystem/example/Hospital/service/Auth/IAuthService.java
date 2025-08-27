package hospitalSystem.example.Hospital.service.Auth;

import hospitalSystem.example.Hospital.dto.auth.AuthenticationRequest;
import hospitalSystem.example.Hospital.dto.auth.AuthenticationResponse;
import hospitalSystem.example.Hospital.dto.auth.RegisterRequest;

/**
 * Service interface for handling user authentication and registration operations.
 *
 * Provides methods to register new users, authenticate existing users,
 * and generate JWT tokens for both processes.
 */
public interface IAuthService {

    /**
     * Registers a new user in the system and returns a JWT token.
     *
     * Implementation typically includes:
     * 1. Creating a new user entity from the registration request.
     * 2. Encoding the user's password.
     * 3. Saving the user in the database.
     * 4. Generating a JWT token for immediate authentication.
     *
     * @param request the registration request containing user details (name, email, password, role)
     * @return AuthenticationResponse containing the JWT token for the new user
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Authenticates an existing user using email and password, and returns a JWT token.
     *
     * Implementation typically includes:
     * 1. Authenticating the user credentials using AuthenticationManager.
     * 2. Loading the full user entity from the database.
     * 3. Generating a JWT token for the authenticated user.
     *
     * @param request the authentication request containing email and password
     * @return AuthenticationResponse containing the JWT token for the authenticated user
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
