package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.request.AppointmentRequestDto;
import hospitalSystem.example.Hospital.dto.response.AppointmentResponseDto;
import hospitalSystem.example.Hospital.entity.Appointment;
import hospitalSystem.example.Hospital.entity.AppointmentStatus;
import hospitalSystem.example.Hospital.entity.User;

public class AppointmentMapper {

    /**
     * Convert Appointment entity → Response DTO
     */
    public static AppointmentResponseDto toDto(Appointment a) {
        if (a == null) return null;

        return AppointmentResponseDto.builder()
                .id(a.getId())
                .appointmentTime(a.getAppointmentTime())
                .durationMinutes(a.getDurationMinutes())
                .appointmentStatus(a.getAppointmentStatus())
                .notes(a.getNotes())
                .doctorId(a.getDoctor() != null ? a.getDoctor().getId() : null)
                .doctorName(a.getDoctor() != null ? a.getDoctor().getName() : null)
                .patientId(a.getPatient() != null ? a.getPatient().getId() : null)
                .patientName(a.getPatient() != null ? a.getPatient().getName() : null)
                .build();
    }

    /**
     * Convert AppointmentRequestDto → Appointment entity
     * (doctor & patient must already be loaded from DB).
     */
    public static Appointment toEntity(AppointmentRequestDto dto, User doctor, User patient) {
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setDurationMinutes(dto.getDurationMinutes());
        appointment.setNotes(dto.getNotes());
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        return appointment;
    }
}
