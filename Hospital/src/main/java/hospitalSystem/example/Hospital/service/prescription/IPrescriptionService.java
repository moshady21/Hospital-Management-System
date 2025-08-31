package hospitalSystem.example.Hospital.service.prescription;

import hospitalSystem.example.Hospital.dto.request.PrescriptionRequestDto;
import hospitalSystem.example.Hospital.dto.response.PrescriptionResponseDto;

import java.util.List;

public interface IPrescriptionService {
    PrescriptionResponseDto createPrescription(PrescriptionRequestDto prescriptionRequestDto, String doctorEmail);

    PrescriptionResponseDto getPrescription(Long id, String patientEmail);

    List<PrescriptionResponseDto> getAllPrescription();

    List<PrescriptionResponseDto> getAllPatientPrescription(String patientEmail);

    PrescriptionResponseDto updatePrescription(Long id, PrescriptionRequestDto newPrescriptionRequestDto, String doctorEmail);


}
