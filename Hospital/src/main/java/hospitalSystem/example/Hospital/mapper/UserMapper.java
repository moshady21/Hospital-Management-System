package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.request.UserSummaryDto;
import hospitalSystem.example.Hospital.dto.response.UserResponseDto;
import hospitalSystem.example.Hospital.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Map Entity -> Response DTO
    public static UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserRole()
        );
    }

    // Map Request DTO -> Entity
    public static User toEntity(UserSummaryDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
