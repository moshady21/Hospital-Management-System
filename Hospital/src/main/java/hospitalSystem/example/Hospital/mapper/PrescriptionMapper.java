package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.request.PrescriptionRequestDto;
import hospitalSystem.example.Hospital.dto.response.PrescriptionResponseDto;
import hospitalSystem.example.Hospital.dto.MedicineDetailDto;
import hospitalSystem.example.Hospital.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionMapper {

    public static Prescription toEntity(PrescriptionRequestDto dto, User doctor, User patient, List<MedicineDetail> medicineDetails) {
        Prescription prescription = new Prescription();
        prescription.setIssuedAt(dto.getIssuedAt());
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setMedicineDetails(medicineDetails);

        medicineDetails.forEach(md -> md.setPrescription(prescription));

        return prescription;
    }

    public static PrescriptionResponseDto toDto(Prescription prescription) {
        PrescriptionResponseDto dto = new PrescriptionResponseDto();
        dto.setIssuedAt(prescription.getIssuedAt());
        dto.setDoctorEmail(prescription.getDoctor().getEmail());
        dto.setPatientEmail(prescription.getPatient().getEmail());
        dto.setMedicineDetails(
                prescription.getMedicineDetails()
                        .stream()
                        .map(MedicineDetailMapper::toDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }


}
