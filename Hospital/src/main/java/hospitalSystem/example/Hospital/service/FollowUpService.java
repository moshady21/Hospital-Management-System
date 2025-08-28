package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.request.FollowUpRequestDto;
import hospitalSystem.example.Hospital.dto.response.FollowUpResponseDto;
import java.util.List;

public interface FollowUpService {
    FollowUpResponseDto recordFollowUp(FollowUpRequestDto request, Long doctorId);
    List<FollowUpResponseDto> getPatientHistory(Long patientId);
}
