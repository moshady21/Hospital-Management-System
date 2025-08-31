package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.request.FollowUpRequestDto;
import hospitalSystem.example.Hospital.dto.response.FollowUpResponseDto;
import hospitalSystem.example.Hospital.service.followUp.FollowUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/follow-up")
public class FollowUpController {

    private final FollowUpService followUpService;

    public FollowUpController(FollowUpService followUpService) {
        this.followUpService = followUpService;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<FollowUpResponseDto> recordFollowUp(@RequestBody FollowUpRequestDto request, @RequestParam Long doctorId) {
        FollowUpResponseDto response = followUpService.recordFollowUp(request, doctorId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/{patientId}")
    public ResponseEntity<List<FollowUpResponseDto>> getPatientHistory(@PathVariable Long patientId) {
        List<FollowUpResponseDto> history = followUpService.getPatientHistory(patientId);
        return ResponseEntity.ok(history);
    }
}
