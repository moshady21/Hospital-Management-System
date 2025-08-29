package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.request.MedicineRequestDto;
import hospitalSystem.example.Hospital.dto.response.MedicineResponseDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.service.medicine.MedicineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PreAuthorize("hasRole('PHARMACY')")
    @PostMapping
    public ResponseEntity<MedicineResponseDto> createMedicine(
            @RequestBody MedicineRequestDto medicineDto, Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());

        String email = principal.getName();
        return ResponseEntity.ok(medicineService.createMedicine(medicineDto, email));
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @GetMapping
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @PutMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> updateMedicine(
            @PathVariable Long id,
            @RequestBody MedicineRequestDto medicineDto,
            Principal principal) {

        String email = principal.getName();
        return ResponseEntity.ok(medicineService.updateMedicine(id, email, medicineDto));
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
