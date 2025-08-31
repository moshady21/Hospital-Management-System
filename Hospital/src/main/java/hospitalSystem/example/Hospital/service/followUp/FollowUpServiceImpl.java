package hospitalSystem.example.Hospital.service.followUp;

import hospitalSystem.example.Hospital.dto.request.FollowUpRequestDto;
import hospitalSystem.example.Hospital.dto.response.FollowUpResponseDto;
import hospitalSystem.example.Hospital.entity.Appointment;
import hospitalSystem.example.Hospital.entity.FollowUp;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.repository.AppointmentRepository;
import hospitalSystem.example.Hospital.repository.FollowUpRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
// import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FollowUpServiceImpl implements FollowUpService {

    private final FollowUpRepository followUpRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    // private final ModelMapper modelMapper;

    public FollowUpServiceImpl(FollowUpRepository followUpRepository,
                               UserRepository userRepository,
                               AppointmentRepository appointmentRepository
                            //    ,ModelMapper modelMapper
                               ) {
        this.followUpRepository = followUpRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        // this.modelMapper = modelMapper;
    }

    @Override
    public FollowUpResponseDto recordFollowUp(FollowUpRequestDto request, Long doctorId) {
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        User patient = userRepository.findById(request.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(request.getAppointmentId()).orElse(null);
        }
        FollowUp followUp = new FollowUp();
        followUp.setDoctor(doctor);
        followUp.setPatient(patient);
        followUp.setAppointment(appointment);
        followUp.setNotes(request.getNotes());
        followUp.setFollowUpDate(request.getFollowUpDate());
        FollowUp saved = followUpRepository.save(followUp);
        FollowUpResponseDto dto = new FollowUpResponseDto();
        dto.setId(saved.getId());
        dto.setDoctorId(saved.getDoctor() != null ? saved.getDoctor().getId() : null);
        dto.setPatientId(saved.getPatient() != null ? saved.getPatient().getId() : null);
        dto.setNotes(saved.getNotes());
        dto.setFollowUpDate(saved.getFollowUpDate());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowUpResponseDto> getPatientHistory(Long patientId) {
        List<FollowUp> list = followUpRepository.findByPatientIdOrderByFollowUpDateDesc(patientId);
        return list.stream().map(f -> {
            FollowUpResponseDto dto = new FollowUpResponseDto();
            dto.setId(f.getId());
            dto.setDoctorId(f.getDoctor() != null ? f.getDoctor().getId() : null);
            dto.setPatientId(f.getPatient() != null ? f.getPatient().getId() : null);
            dto.setNotes(f.getNotes());
            dto.setFollowUpDate(f.getFollowUpDate());
            return dto;
        }).collect(Collectors.toList());
    }
}
