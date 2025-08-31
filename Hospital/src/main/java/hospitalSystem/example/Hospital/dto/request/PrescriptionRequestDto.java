package hospitalSystem.example.Hospital.dto.request;

import hospitalSystem.example.Hospital.dto.MedicineDetailDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionRequestDto {
    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotNull(message = "Medicine Details is required")
    private List<MedicineDetailDto> medicineDetails;
}

