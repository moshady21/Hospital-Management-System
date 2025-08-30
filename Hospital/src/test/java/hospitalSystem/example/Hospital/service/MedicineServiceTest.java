package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.request.MedicineRequestDto;
import hospitalSystem.example.Hospital.dto.response.MedicineResponseDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import hospitalSystem.example.Hospital.service.medicine.MedicineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MedicineService medicineService;

    private User user;
    private Medicine medicine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("doctor@test.com");

        medicine = new Medicine();
        medicine.setId(100L);
        medicine.setName("Paracetamol");
        medicine.setPrice(10.0);
        medicine.setStockQuantity(50);
        medicine.setDescription("Pain reliever");
        medicine.setUser(user);
    }

    @Test
    void testCreateMedicine_Success() {
        MedicineRequestDto request = new MedicineRequestDto();
        request.setName("Paracetamol");
        request.setPrice(10.0);
        request.setDescription("Pain reliever");
        request.setStockQuantity(50);

        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(user));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);

        MedicineResponseDto response = medicineService.createMedicine(request, "doctor@test.com");

        assertThat(response.getName()).isEqualTo("Paracetamol");
        assertThat(response.getPrice()).isEqualTo(10.0);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }

    @Test
    void testCreateMedicine_UserNotFound() {
        MedicineRequestDto request = new MedicineRequestDto();
        request.setName("Paracetamol");

        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.createMedicine(request, "notfound@test.com"));
    }

    @Test
    void testGetMedicineById_Success() {
        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));

        MedicineResponseDto response = medicineService.getMedicineById(100L);

        assertThat(response.getName()).isEqualTo("Paracetamol");
        assertThat(response.getDescription()).isEqualTo("Pain reliever");
    }

    @Test
    void testGetMedicineById_NotFound() {
        when(medicineRepository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.getMedicineById(200L));
    }

    @Test
    void testGetAllMedicines_Success() {
        when(medicineRepository.findAll()).thenReturn(Arrays.asList(medicine));

        List<MedicineResponseDto> medicines = medicineService.getAllMedicines();

        assertThat(medicines).hasSize(1);
        assertThat(medicines.get(0).getName()).isEqualTo("Paracetamol");
    }

    @Test
    void testGetAllMedicines_Empty() {
        when(medicineRepository.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.getAllMedicines());
    }

    @Test
    void testUpdateMedicine_Success() {
        MedicineRequestDto newRequest = new MedicineRequestDto();
        newRequest.setName("Ibuprofen");
        newRequest.setPrice(15.0);
        newRequest.setDescription("Anti-inflammatory");
        newRequest.setStockQuantity(30);

        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));
        when(userRepository.findByEmail("doctor@test.com")).thenReturn(Optional.of(user));

        MedicineResponseDto updated = medicineService.updateMedicine(100L, "doctor@test.com", newRequest);

        assertThat(updated.getName()).isEqualTo("Ibuprofen");
        assertThat(updated.getPrice()).isEqualTo(15.0);
        assertThat(updated.getStockQuantity()).isEqualTo(30);
    }

    @Test
    void testUpdateMedicine_NotFound() {
        MedicineRequestDto request = new MedicineRequestDto();
        request.setName("Ibuprofen");

        when(medicineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.updateMedicine(999L, "doctor@test.com", request));
    }

    @Test
    void testUpdateMedicine_UserNotFound() {
        MedicineRequestDto request = new MedicineRequestDto();
        request.setName("Ibuprofen");

        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));
        when(userRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.updateMedicine(100L, "wrong@test.com", request));
    }

    @Test
    void testDeleteMedicine_Success() {
        when(medicineRepository.findById(100L)).thenReturn(Optional.of(medicine));

        medicineService.deleteMedicine(100L);

        verify(medicineRepository, times(1)).deleteById(100L);
    }

    @Test
    void testDeleteMedicine_NotFound() {
        when(medicineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.deleteMedicine(999L));
    }

    @Test
    void testGetAvailableMedicines_Success() {
        when(medicineRepository.findAllWithAvailableQuantity()).thenReturn(Arrays.asList(medicine));

        List<MedicineResponseDto> available = medicineService.getAvailableMedicines();

        assertThat(available).hasSize(1);
        assertThat(available.get(0).getStockQuantity()).isEqualTo(50);
    }

    @Test
    void testGetAvailableMedicines_Empty() {
        when(medicineRepository.findAllWithAvailableQuantity()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.getAvailableMedicines());
    }
}
