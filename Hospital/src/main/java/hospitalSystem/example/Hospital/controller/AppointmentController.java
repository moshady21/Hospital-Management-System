package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.request.AppointmentRequestDto;
import hospitalSystem.example.Hospital.dto.response.AppointmentResponseDto;
import hospitalSystem.example.Hospital.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Book a new appointment
     */
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        try {
            AppointmentResponseDto response = appointmentService.createAppointment(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            // conflict in booking (doctor/patient already has appointment at that time)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // fallback for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }


    /**
     * Cancel an appointment
     */
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(@PathVariable Long id) {
        AppointmentResponseDto response = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all appointments for a doctor
     */
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }

    /**
     * Get all appointments for a patient
     */
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }
}
