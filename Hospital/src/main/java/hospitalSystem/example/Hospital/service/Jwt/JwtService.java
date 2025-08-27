package hospitalSystem.example.Hospital.service.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    // ======== EXTRACTING FROM TOKEN SECTION FUNCTIONS ============

    public Claims extractAllClaims(String Token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(Token)
                .getBody();
    }

    public <T> T extractClaim(String Token,  Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(Token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String Token) {
        return extractClaim(Token, Claims::getSubject);
    }

    public Date extractExpiration(String Token) {
        return extractClaim(Token, Claims::getExpiration);
    }

    // ======== Validation Section  (Contains Functions That Validate The Token) ============

    public Boolean isValidToken(String Token, UserDetails userDetails) {
        String username = extractUsername(Token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(Token);
    }

    public Boolean isTokenExpired(String Token) {
        Date expiration = extractExpiration(Token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    // ======== GENERATE TOKEN and SigningKey Section ============

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails);
    }

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
