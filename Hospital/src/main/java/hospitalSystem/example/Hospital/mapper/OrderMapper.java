package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.OrderItemDto;
import hospitalSystem.example.Hospital.dto.response.OrderResponseDto;
import hospitalSystem.example.Hospital.entity.Order;
import hospitalSystem.example.Hospital.entity.OrderItem;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDto toDto(Order order) {
        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(OrderMapper::mapItem)
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getOrderDate(),
                itemDtos
        );
    }

    private static OrderItemDto mapItem(OrderItem item) {
        return new OrderItemDto(
                item.getMedicine().getId(),
                item.getMedicine().getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}
