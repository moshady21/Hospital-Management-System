package hospitalSystem.example.Hospital.dto.response;

import hospitalSystem.example.Hospital.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}
