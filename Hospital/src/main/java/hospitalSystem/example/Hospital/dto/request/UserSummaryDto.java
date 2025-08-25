package hospitalSystem.example.Hospital.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSummaryDto {
    private String name, email, password;
}
