package hospitalSystem.example.Hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicineDetailDto {
    @NotNull(message = "Dosage is required")
    private int dosage;

    @NotBlank(message = "Instructions is required")
    private String instructions;

    @NotNull(message = "Medicine Id is required")
    private Long medicineId;


}
