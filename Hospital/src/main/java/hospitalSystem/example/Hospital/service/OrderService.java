package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.*;
import hospitalSystem.example.Hospital.dto.request.OrderRequestDto;
import hospitalSystem.example.Hospital.dto.request.OrderRequestItemDto;
import hospitalSystem.example.Hospital.dto.response.OrderResponseDto;
import hospitalSystem.example.Hospital.entity.*;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.mapper.OrderMapper;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.OrderRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponseDto createOrder(Long patientId, OrderRequestDto request) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Order order = new Order();
        order.setUser(patient);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRequestItemDto itemReq : request.getItems()) {
            Medicine medicine = medicineRepository.findById(itemReq.getMedicineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));

            if (medicine.getStockQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for " + medicine.getName());
            }

            medicine.setStockQuantity(medicine.getStockQuantity() - itemReq.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setMedicine(medicine);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(medicine.getPrice() * itemReq.getQuantity());

            total += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);
        log.info("✅ Order {} created for patient {}", savedOrder.getId(), patientId);

        return OrderMapper.toDto(savedOrder);
    }

    public List<OrderResponseDto> getOrderHistory(Long patientId) {
        return orderRepository.findByUserId(patientId)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }
}
