package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.request.MedicineRequestDto;
import hospitalSystem.example.Hospital.dto.response.MedicineResponseDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.User;

public class MedicineMapper {
    public static Medicine toEntity(MedicineRequestDto dto, User user) {
        Medicine medicine = new Medicine();
        medicine.setName(dto.getName());
        medicine.setPrice(dto.getPrice());
        medicine.setDescription(dto.getDescription());
        medicine.setStockQuantity(dto.getStockQuantity());
        //medicine.setMedicineDetails(dto.);
        medicine.setUser(user);
        return medicine;
    }

    public static MedicineResponseDto toDto(Medicine medicine) {
        MedicineResponseDto dto = new MedicineResponseDto();
        dto.setName(medicine.getName());
        dto.setPrice(medicine.getPrice());
        dto.setDescription(medicine.getDescription());
        dto.setStockQuantity(medicine.getStockQuantity());
        dto.setPharmacy_id(medicine.getUser().getId());
        return dto;
    }
}
