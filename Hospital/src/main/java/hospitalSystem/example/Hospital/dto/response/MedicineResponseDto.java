package hospitalSystem.example.Hospital.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicineResponseDto {
    private String name;

    private double price;

    private int stockQuantity;

    private String description;

    private Long pharmacy_id;
}
