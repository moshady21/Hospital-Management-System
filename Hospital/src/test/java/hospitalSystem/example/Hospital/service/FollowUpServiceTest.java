// package hospitalSystem.example.Hospital.service;

// import hospitalSystem.example.Hospital.dto.FollowUpRequestDto;
// import hospitalSystem.example.Hospital.dto.FollowUpResponseDto;
// import hospitalSystem.example.Hospital.entity.FollowUp;
// import hospitalSystem.example.Hospital.entity.User;
// import hospitalSystem.example.Hospital.repository.AppointmentRepository;
// import hospitalSystem.example.Hospital.repository.FollowUpRepository;
// import hospitalSystem.example.Hospital.repository.UserRepository;
// import hospitalSystem.example.Hospital.service.impl.FollowUpServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.modelmapper.ModelMapper;
// import org.mockito.Mockito;
// import java.time.LocalDate;
// import java.util.Optional;
// import java.util.List;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// public class FollowUpServiceTest {

//     private FollowUpRepository followUpRepository;
//     private UserRepository userRepository;
//     private AppointmentRepository appointmentRepository;
//     private FollowUpServiceImpl followUpService;

//     @BeforeEach
//     public void setUp() {
//         followUpRepository = mock(FollowUpRepository.class);
//         userRepository = mock(UserRepository.class);
//         appointmentRepository = mock(AppointmentRepository.class);
//         followUpService = new FollowUpServiceImpl(followUpRepository, userRepository, appointmentRepository, new ModelMapper());
//     }

//     @Test
//     public void testRecordFollowUpSuccess() {
//         User doctor = new User();
//         doctor.setId(1L);
//         User patient = new User();
//         patient.setId(2L);
//         when(userRepository.findById(1L)).thenReturn(Optional.of(doctor));
//         when(userRepository.findById(2L)).thenReturn(Optional.of(patient));
//         FollowUp saved = new FollowUp();
//         saved.setId(10L);
//         saved.setDoctor(doctor);
//         saved.setPatient(patient);
//         saved.setNotes("note");
//         saved.setFollowUpDate(LocalDate.now());
//         when(followUpRepository.save(any(FollowUp.class))).thenReturn(saved);
//         FollowUpRequestDto req = new FollowUpRequestDto();
//         req.setPatientId(2L);
//         req.setNotes("note");
//         req.setFollowUpDate(LocalDate.now());
//         FollowUpResponseDto resp = followUpService.recordFollowUp(req, 1L);
//         assertNotNull(resp);
//         assertEquals(10L, resp.getId());
//         assertEquals(1L, resp.getDoctorId());
//         assertEquals(2L, resp.getPatientId());
//     }

//     @Test
//     public void testGetPatientHistoryEmpty() {
//         when(followUpRepository.findByPatientIdOrderByFollowUpDateDesc(2L)).thenReturn(List.of());
//         List<FollowUpResponseDto> history = followUpService.getPatientHistory(2L);
//         assertNotNull(history);
//         assertTrue(history.isEmpty());
//     }
// }
