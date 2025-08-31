package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.request.OrderRequestDto;
import hospitalSystem.example.Hospital.dto.request.OrderRequestItemDto;
import hospitalSystem.example.Hospital.dto.response.OrderResponseDto;
import hospitalSystem.example.Hospital.entity.*;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.OrderRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(OrderService.class) // import service into test context
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;
    private Medicine paracetamol;

    @BeforeEach
    void setUp() {
        // create a test user (patient)
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        userRepository.save(testUser);

        // create a medicine
        paracetamol = new Medicine();
        paracetamol.setName("Paracetamol");
        paracetamol.setPrice(10.0);
        paracetamol.setStockQuantity(50);
        medicineRepository.save(paracetamol);
    }

    @Test
    void testCreateOrder_Success() {
        // prepare request
        OrderRequestItemDto itemDto = new OrderRequestItemDto(paracetamol.getId(), 5);
        OrderRequestDto request = new OrderRequestDto(List.of(itemDto));

        // call service
        OrderResponseDto response = orderService.createOrder(testUser.getId(), request);

        // assertions
        assertThat(response.getOrderId()).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(50.0);
        assertThat(response.getItems()).hasSize(1);
        assertThat(response.getItems().get(0).getMedicineName()).isEqualTo("Paracetamol");

        // stock should decrease
        Medicine updated = medicineRepository.findById(paracetamol.getId()).orElseThrow();
        assertThat(updated.getStockQuantity()).isEqualTo(45);
    }

    @Test
    void testCreateOrder_ThrowsWhenStockInsufficient() {
        OrderRequestItemDto itemDto = new OrderRequestItemDto(paracetamol.getId(), 100); // exceeds stock
        OrderRequestDto request = new OrderRequestDto(List.of(itemDto));

        assertThatThrownBy(() -> orderService.createOrder(testUser.getId(), request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough stock");
    }

    @Test
    void testGetOrderHistory() {
        // create an order first
        OrderRequestItemDto itemDto = new OrderRequestItemDto(paracetamol.getId(), 2);
        orderService.createOrder(testUser.getId(), new OrderRequestDto(List.of(itemDto)));

        List<OrderResponseDto> history = orderService.getOrderHistory(testUser.getId());

        assertThat(history).hasSize(1);
        assertThat(history.get(0).getItems()).hasSize(1);
        assertThat(history.get(0).getItems().get(0).getQuantity()).isEqualTo(2);
    }
}
