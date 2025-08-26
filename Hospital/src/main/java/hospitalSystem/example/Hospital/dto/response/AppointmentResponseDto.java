package hospitalSystem.example.Hospital.dto.response;

import hospitalSystem.example.Hospital.entity.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponseDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private Integer durationMinutes;
    private AppointmentStatus appointmentStatus;
    private String notes;

    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;
}
