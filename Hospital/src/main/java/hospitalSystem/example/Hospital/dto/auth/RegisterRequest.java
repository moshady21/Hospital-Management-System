package hospitalSystem.example.Hospital.dto.auth;

import hospitalSystem.example.Hospital.entity.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20)
    private String name;

    @Email
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private UserRole userRole;
}
