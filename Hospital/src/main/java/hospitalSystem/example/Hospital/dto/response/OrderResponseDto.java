package hospitalSystem.example.Hospital.dto.response;

import hospitalSystem.example.Hospital.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private double totalPrice;
    private String status;
    private LocalDate orderDate;
    private List<OrderItemDto> items;
}
