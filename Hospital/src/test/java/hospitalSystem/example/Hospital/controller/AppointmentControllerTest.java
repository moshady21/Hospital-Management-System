package hospitalSystem.example.Hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hospitalSystem.example.Hospital.dto.request.AppointmentRequestDto;
import hospitalSystem.example.Hospital.entity.AppointmentStatus;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User doctor;
    private User patient;

    private LocalDateTime normalizedNowPlusDays(int days) {
        return LocalDateTime.now().plusDays(days).withSecond(0).withNano(0);
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        doctor = new User();
        doctor.setName("Dr. Strange");
        doctor.setEmail("doctor@example.com");
        doctor.setPassword("strongPass123");
        doctor.setUserRole(hospitalSystem.example.Hospital.entity.UserRole.DOCTOR);
        doctor = userRepository.save(doctor);

        patient = new User();
        patient.setName("John Doe");
        patient.setEmail("patient@example.com");
        patient.setPassword("securePass456");  // also at least 8 chars
        patient.setUserRole(hospitalSystem.example.Hospital.entity.UserRole.PATIENT);
        patient = userRepository.save(patient);
    }

    @Test
    void shouldCreateAndFetchAppointment() throws Exception {
        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setDoctorId(doctor.getId());
        request.setPatientId(patient.getId());
        request.setAppointmentTime(normalizedNowPlusDays(1));
        request.setDurationMinutes(30);
        request.setNotes("First consultation");

        // Create appointment
        String response = mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.appointmentStatus").value(AppointmentStatus.CONFIRMED.name()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Parse ID from response
        Long appointmentId = objectMapper.readTree(response).get("id").asLong();

        // Fetch by doctor
        mockMvc.perform(get("/appointments/doctor/" + doctor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointmentId))
                .andExpect(jsonPath("$[0].doctorName").value("Dr. Strange"));

        // Fetch by patient
        mockMvc.perform(get("/appointments/patient/" + patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointmentId))
                .andExpect(jsonPath("$[0].patientName").value("John Doe"));
    }

    @Test
    void shouldCancelAppointment() throws Exception {
        // First create appointment
        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setDoctorId(doctor.getId());
        request.setPatientId(patient.getId());
        request.setAppointmentTime(normalizedNowPlusDays(2));
        request.setDurationMinutes(60);

        String response = mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long appointmentId = objectMapper.readTree(response).get("id").asLong();

        // Cancel appointment
        mockMvc.perform(delete("/appointments/" + appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentStatus").value(AppointmentStatus.CANCELLED.name()));
    }

    @Test
    void shouldNotAllowDoubleBookingForDoctor() throws Exception {
        // First appointment
        AppointmentRequestDto first = new AppointmentRequestDto();
        first.setDoctorId(doctor.getId());
        first.setPatientId(patient.getId());
        first.setAppointmentTime(LocalDateTime.now().plusDays(1));
        first.setDurationMinutes(30);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(first)))
                .andExpect(status().isCreated());

        // Second appointment with same doctor & time
        AppointmentRequestDto second = new AppointmentRequestDto();
        second.setDoctorId(doctor.getId());
        second.setPatientId(patient.getId());
        second.setAppointmentTime(first.getAppointmentTime()); // same time
        second.setDurationMinutes(30);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(second)))
                .andExpect(status().isConflict())  // 409 from controller
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Doctor is already booked")));
    }

    @Test
    void shouldNotAllowDoubleBookingForPatient() throws Exception {
        // First appointment
        AppointmentRequestDto first = new AppointmentRequestDto();
        first.setDoctorId(doctor.getId());
        first.setPatientId(patient.getId());
        first.setAppointmentTime(LocalDateTime.now().plusDays(1).withHour(12));
        first.setDurationMinutes(30);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(first)))
                .andExpect(status().isCreated());

        // Another doctor, but same patient & time
        User anotherDoctor = new User();
        anotherDoctor.setName("Dr. House");
        anotherDoctor.setEmail("house@example.com");
        anotherDoctor.setPassword("doctorPass123");
        anotherDoctor.setUserRole(hospitalSystem.example.Hospital.entity.UserRole.DOCTOR);
        userRepository.save(anotherDoctor);

        AppointmentRequestDto second = new AppointmentRequestDto();
        second.setDoctorId(anotherDoctor.getId());
        second.setPatientId(patient.getId()); // same patient
        second.setAppointmentTime(first.getAppointmentTime()); // same time
        second.setDurationMinutes(30);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(second)))
                .andExpect(status().isConflict())  // 409
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Patient already has an appointment")));
    }

    @Test
    void shouldFailValidationForInvalidDuration() throws Exception {
        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setDoctorId(doctor.getId());
        request.setPatientId(patient.getId());
        request.setAppointmentTime(normalizedNowPlusDays(1));
        request.setDurationMinutes(2); // too short

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
