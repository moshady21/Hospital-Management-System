package hospitalSystem.example.Hospital.controller;

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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDto> checkout(@AuthenticationPrincipal UserDetails user,
                                                     @Valid @RequestBody OrderRequestDto request) {
        Long patientId = Long.valueOf(user.getUsername()); // ⚠️ adjust if username != patientId
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(patientId, request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@AuthenticationPrincipal UserDetails user) {
        Long patientId = Long.valueOf(user.getUsername());
        return ResponseEntity.ok(orderService.getOrderHistory(patientId));
    }
}
