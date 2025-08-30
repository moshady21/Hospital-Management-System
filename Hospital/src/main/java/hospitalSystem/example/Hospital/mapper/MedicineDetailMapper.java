package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.MedicineDetailDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.MedicineDetail;

public class MedicineDetailMapper {

    public static MedicineDetail toEntity(MedicineDetailDto dto, Medicine medicine) {
        MedicineDetail medicineDetail = new MedicineDetail();
        medicineDetail.setDosage(dto.getDosage());
        medicineDetail.setInstructions(dto.getInstructions());
        medicineDetail.setMedicine(medicine);

        return medicineDetail;
    }


    public static MedicineDetailDto toDto(MedicineDetail medicineDetail) {
        MedicineDetailDto dto = new MedicineDetailDto();
        dto.setDosage(medicineDetail.getDosage());
        dto.setInstructions(medicineDetail.getInstructions());
        dto.setMedicineId(medicineDetail.getMedicine().getId());

        return dto;
    }
}
