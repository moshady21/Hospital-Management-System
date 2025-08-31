package hospitalSystem.example.Hospital.service.medicine;

import hospitalSystem.example.Hospital.dto.request.MedicineRequestDto;
import hospitalSystem.example.Hospital.dto.response.MedicineResponseDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.mapper.MedicineMapper;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MedicineService implements IMedicineService {
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    @Override
    public MedicineResponseDto createMedicine(MedicineRequestDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Medicine medicine = MedicineMapper.toEntity(dto, user);
        medicineRepository.save(medicine);
        return MedicineMapper.toDto(medicine);
    }

    @Override
    public MedicineResponseDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        return MedicineMapper.toDto(medicine);
    }

    @Override
    public List<MedicineResponseDto> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        if (medicines.isEmpty()) {
            throw new ResourceNotFoundException("No Available Medicines found");
        }
        return medicines.stream().map(MedicineMapper::toDto).toList();
    }

    @Override
    public MedicineResponseDto updateMedicine(Long id, String email, MedicineRequestDto newMedicineDto) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Medicine newMedicine = MedicineMapper.toEntity(newMedicineDto, user);

        medicine.setName(newMedicine.getName());
        medicine.setPrice(newMedicine.getPrice());
        medicine.setDescription(newMedicine.getDescription());
        medicine.setStockQuantity(newMedicine.getStockQuantity());
        return MedicineMapper.toDto(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        medicineRepository.deleteById(id);
    }

    @Override
    public List<MedicineResponseDto> getAvailableMedicines() {
        List<Medicine> medicines = medicineRepository.findAllWithAvailableQuantity();
        if (medicines.isEmpty()) {
            throw new ResourceNotFoundException("No Available Medicines found");
        }
        return medicines.stream().map(MedicineMapper::toDto).toList();
    }


}
