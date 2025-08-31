package hospitalSystem.example.Hospital.dto.response;

import hospitalSystem.example.Hospital.dto.MedicineDetailDto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionResponseDto {
    private LocalDateTime issuedAt;

    private String doctorEmail;
    private String patientEmail;

    private List<MedicineDetailDto> medicineDetails;
}
