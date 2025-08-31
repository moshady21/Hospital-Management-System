package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.request.PrescriptionRequestDto;
import hospitalSystem.example.Hospital.dto.response.PrescriptionResponseDto;
import hospitalSystem.example.Hospital.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PrescriptionResponseDto> createPrescription(
            @RequestBody PrescriptionRequestDto requestDto,
            Principal principal) {
        PrescriptionResponseDto dto = prescriptionService.createPrescription(requestDto, principal.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<PrescriptionResponseDto> getPrescription(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(prescriptionService.getPrescription(id, principal.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PHARMACY', 'DOCTOR')")
    public ResponseEntity<List<PrescriptionResponseDto>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescription());
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient")
    public ResponseEntity<List<PrescriptionResponseDto>> getPatientPrescriptions(Principal principal) {
        return ResponseEntity.ok(prescriptionService.getAllPatientPrescription(principal.getName()));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionRequestDto requestDto,
            Principal principal) {
        System.out.println("Updating prescription");
        return ResponseEntity.ok(prescriptionService.updatePrescription(id, requestDto, principal.getName()));
    }
}
