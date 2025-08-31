package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import hospitalSystem.example.Hospital.dto.request.OrderRequestDto;
import hospitalSystem.example.Hospital.dto.response.OrderResponseDto;
import hospitalSystem.example.Hospital.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<OrderResponseDto> checkout(@AuthenticationPrincipal UserDetails user,
                                                     @Valid @RequestBody OrderRequestDto request) {
        // Get email from authenticated user
        String email = user.getUsername();

        // Fetch patient entity
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Get patient ID
        Long patientId = patient.getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(patientId, request));
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@AuthenticationPrincipal UserDetails user) {
        // Get email from authenticated user
        String email = user.getUsername();

        // Fetch patient entity
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Get patient ID
        Long patientId = patient.getId();
        return ResponseEntity.ok(orderService.getOrderHistory(patientId));
    }
}
