package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.MedicineDetailDto;
import hospitalSystem.example.Hospital.dto.request.PrescriptionRequestDto;
import hospitalSystem.example.Hospital.dto.response.PrescriptionResponseDto;
import hospitalSystem.example.Hospital.entity.*;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.mapper.MedicineDetailMapper;
import hospitalSystem.example.Hospital.mapper.PrescriptionMapper;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.PrescriptionRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import hospitalSystem.example.Hospital.service.prescription.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private PrescriptionService prescriptionService;

    private User doctor;
    private User patient;
    private Medicine medicine;
    private PrescriptionRequestDto requestDto;
    private Prescription prescription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new User();
        doctor.setId(1L);
        doctor.setEmail("doctor@test.com");

        patient = new User();
        patient.setId(2L);
        patient.setEmail("patient@test.com");

        medicine = new Medicine();
        medicine.setId(100L);
        medicine.setName("Paracetamol");

        MedicineDetailDto mdDto = new MedicineDetailDto();
        mdDto.setMedicineId(100L);
        mdDto.setDosage(2);

        requestDto = new PrescriptionRequestDto();
        requestDto.setPatientId(2L);
        requestDto.setIssuedAt(LocalDateTime.now());
        requestDto.setMedicineDetails(List.of(mdDto));

        MedicineDetail mdEntity = MedicineDetailMapper.toEntity(mdDto, medicine);

        prescription = PrescriptionMapper.toEntity(requestDto, doctor, patient, List.of(mdEntity));
        prescription.setId(10L);
    }

    // ✅ Create Prescription Success
    @Test
    void testCreatePrescription_Success() {
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        PrescriptionResponseDto response = prescriptionService.createPrescription(requestDto, "doctor@test.com");

        assertNotNull(response);
        assertEquals("doctor@test.com", response.getDoctorEmail());
        assertEquals("patient@test.com", response.getPatientEmail());
        assertEquals(1, response.getMedicineDetails().size());
    }

    // ❌ Doctor Not Found
    @Test
    void testCreatePrescription_DoctorNotFound() {
        when(userRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.createPrescription(requestDto, "wrong@test.com"));
    }

    // ❌ Patient Not Found
    @Test
    void testCreatePrescription_PatientNotFound() {
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.createPrescription(requestDto, "doctor@test.com"));
    }

    // ❌ Medicine Not Found
    @Test
    void testCreatePrescription_MedicineNotFound() {
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(medicineRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.createPrescription(requestDto, "doctor@test.com"));
    }

    // ✅ Get Prescription Success
    @Test
    void testGetPrescription_Success() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));

        PrescriptionResponseDto dto = prescriptionService.getPrescription(10L, "patient@test.com");

        assertNotNull(dto);
        assertEquals("doctor@test.com", dto.getDoctorEmail());
    }

    // ❌ Get Prescription Not Found
    @Test
    void testGetPrescription_NotFound() {
        when(prescriptionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.getPrescription(99L, "patient@test.com"));
    }

    // ❌ Unauthorized Access
    @Test
    void testGetPrescription_Unauthorized() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));

        assertThrows(IllegalArgumentException.class,
                () -> prescriptionService.getPrescription(10L, "wrongpatient@test.com"));
    }

    // ✅ Get All Prescriptions
    @Test
    void testGetAllPrescription_Success() {
        when(prescriptionRepository.findAll()).thenReturn(List.of(prescription));

        List<PrescriptionResponseDto> dtos = prescriptionService.getAllPrescription();

        assertFalse(dtos.isEmpty());
        assertEquals(1, dtos.size());
    }

    // ❌ No Prescriptions Found
    @Test
    void testGetAllPrescription_NotFound() {
        when(prescriptionRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.getAllPrescription());
    }

    // ✅ Get All Patient Prescriptions
    @Test
    void testGetAllPatientPrescription_Success() {
        when(prescriptionRepository.findAllPrescriptionsByPatientEmail("patient@test.com"))
                .thenReturn(List.of(prescription));

        List<PrescriptionResponseDto> dtos = prescriptionService.getAllPatientPrescription("patient@test.com");

        assertEquals(1, dtos.size());
    }

    // ❌ No Patient Prescriptions
    @Test
    void testGetAllPatientPrescription_NotFound() {
        when(prescriptionRepository.findAllPrescriptionsByPatientEmail("patient@test.com"))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.getAllPatientPrescription("patient@test.com"));
    }

    // ✅ Update Prescription Success
    @Test
    void testUpdatePrescription_Success() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        PrescriptionResponseDto response = prescriptionService.updatePrescription(10L, requestDto, "doctor@test.com");

        assertNotNull(response);
        assertEquals("doctor@test.com", response.getDoctorEmail());
    }

    // ❌ Update Prescription Not Found
    @Test
    void testUpdatePrescription_NotFound() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.updatePrescription(10L, requestDto, "doctor@test.com"));
    }

    // ❌ Unauthorized Doctor
    @Test
    void testUpdatePrescription_UnauthorizedDoctor() {
        User anotherDoctor = new User();
        anotherDoctor.setId(99L);
        anotherDoctor.setEmail("another@test.com");

        prescription.setDoctor(anotherDoctor);

        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));

        assertThrows(IllegalArgumentException.class,
                () -> prescriptionService.updatePrescription(10L, requestDto, "doctor@test.com"));
    }

    // ❌ Update Patient Not Found
    @Test
    void testUpdatePrescription_PatientNotFound() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.updatePrescription(10L, requestDto, "doctor@test.com"));
    }

    // ❌ Update Medicine Not Found
    @Test
    void testUpdatePrescription_MedicineNotFound() {
        when(prescriptionRepository.findById(10L)).thenReturn(Optional.of(prescription));
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(doctor));
        when(userRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(medicineRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> prescriptionService.updatePrescription(10L, requestDto, "doctor@test.com"));
    }
}
