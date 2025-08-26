package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.request.AppointmentRequestDto;
import hospitalSystem.example.Hospital.dto.response.AppointmentResponseDto;
import hospitalSystem.example.Hospital.entity.Appointment;
import hospitalSystem.example.Hospital.entity.AppointmentStatus;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.mapper.AppointmentMapper;
import hospitalSystem.example.Hospital.repository.AppointmentRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        LocalDateTime start = dto.getAppointmentTime();
        LocalDateTime end = dto.getAppointmentTime().plusMinutes(dto.getDurationMinutes());

        if (appointmentRepository.existsByDoctorIdAndAppointmentTimeBetween(dto.getDoctorId(), start, end)) {
            throw new IllegalStateException("Doctor is already booked at this time.");
        }

        if (appointmentRepository.existsByPatientIdAndAppointmentTimeBetween(dto.getPatientId(), start, end)) {
            throw new IllegalStateException("Patient already has an appointment at this time.");
        }

        Appointment appointment = AppointmentMapper.toEntity(dto, doctor, patient);
        Appointment saved = appointmentRepository.save(appointment);

        return AppointmentMapper.toDto(saved);
    }

    @Transactional
    public AppointmentResponseDto cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        Appointment saved = appointmentRepository.save(appointment);

        return AppointmentMapper.toDto(saved);
    }

    public List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(AppointmentMapper::toDto)
                .toList();
    }

    public List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(AppointmentMapper::toDto)
                .toList();
    }

}
