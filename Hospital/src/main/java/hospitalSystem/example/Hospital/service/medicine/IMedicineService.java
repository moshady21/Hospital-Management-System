package hospitalSystem.example.Hospital.service.medicine;

import hospitalSystem.example.Hospital.dto.request.MedicineRequestDto;
import hospitalSystem.example.Hospital.dto.response.MedicineResponseDto;

import java.util.List;

public interface IMedicineService {
    MedicineResponseDto createMedicine(MedicineRequestDto dto, String email);

    MedicineResponseDto getMedicineById(Long id);

    List<MedicineResponseDto> getAllMedicines();

    MedicineResponseDto updateMedicine(Long id,String email, MedicineRequestDto newMedicineDto);

    void deleteMedicine(Long id);

    List<MedicineResponseDto> getAvailableMedicines();
}
