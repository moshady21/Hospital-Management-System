package hospitalSystem.example.Hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Appointment time is required")
    private LocalDateTime appointmentTime;

    @NotNull(message = "Duration is required")
    @Min(5)  // minimum 5 minutes
    @Max(480) // max 8 hours
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private AppointmentStatus appointmentStatus;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
}
