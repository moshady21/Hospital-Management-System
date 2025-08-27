package hospitalSystem.example.Hospital.service.Jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Contract for JWT (JSON Web Token) operations such as token generation,
 * extraction of claims, and validation.
 */
public interface IJwtService {

    // ========================= Extraction =========================

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return all claims contained in the token
     */
    Claims extractAllClaims(String token);

    /**
     * Extracts a single claim from the JWT token using the provided resolver.
     *
     * @param token          the JWT token
     * @param claimsResolver function to resolve a specific claim
     * @param <T>            the type of the claim
     * @return the resolved claim
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Extracts the username (email) from the JWT token.
     *
     * @param token the JWT token
     * @return the username (email)
     */
    String extractUsername(String token);

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    Date extractExpiration(String token);

    // ========================= Validation =========================

    /**
     * Validates a JWT token by checking its expiration status
     * and whether it belongs to the given user.
     *
     * @param token       the JWT token
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    Boolean isValidToken(String token, UserDetails userDetails);

    /**
     * Checks whether the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    Boolean isTokenExpired(String token);

    // ========================= Generation =========================

    /**
     * Generates a new JWT token for the given user with additional claims.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details to include
     * @return the generated JWT token
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);


    /**
     * Generates a JWT token for the given user.
     *
     * The token contains claims derived from the provided
     * {@link UserDetails}, including (but not limited to) the
     * username/identifier. The token is typically signed using
     * the application's configured secret key.
     *
     * @param userDetails the authenticated user's details
     * @return a signed JWT string representing the user session
     */
    public String generateToken(UserDetails userDetails);
}
